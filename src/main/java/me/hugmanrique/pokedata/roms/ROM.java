package me.hugmanrique.pokedata.roms;

import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.utils.HeaderNames;
import me.hugmanrique.pokedata.utils.PokeText;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a readable (and optionally writable) GBA ROM
 * The ROM will be written using {@link #writeByte(byte)}, but you can leave it empty
 * @author Hugmanrique
 * @since 30/04/2017
 */
public interface ROM {
    /**
     * Retrieves this ROM's bytes
     */
    byte[] getData();

    /**
     * Initializes this ROM's bytes. Only called once
     */
    void setData(byte[] bytes);

    int getInternalOffset();
    void setInternalOffset(int offset);

    /**
     * You can default it to {@code (byte) 0xFF}
     */
    byte getFreeSpaceByte();

    default void addInternalOffset(int add) {
        setInternalOffset(getInternalOffset() + add);
    }

    /**
     * Read bytes from the ROM from given offset into an array of a given size
     * @param offset Offset in ROM as hex string
     * @param size Amount of bytes to grab
     */
    default byte[] readBytes(String offset, int size) {
        int offs = convertOffsetToInt(offset);
        return readBytes(offs, size);
    }

    /**
     * Read bytes from the ROM from given offset into an array of a given size
     * Use {@link BitConverter#getBytes(byte[], int, int)}
     * @param offset Offset in ROM
     * @param size Amount of bytes to grab
     */
    default byte[] readBytes(int offset, int size) {
        return BitConverter.getBytes(getData(), offset, size);
    }

    default byte[] readBytes(int size) {
        int offset = getInternalOffset();

        byte[] result = BitConverter.getBytes(getData(), offset, size);

        addInternalOffset(size);

        return result;
    }

    /**
     * Reads a byte from an offset
     * @param offset Offset to read from
     */
    default byte readByte(int offset) {
        return readBytes(offset,1)[0];
    }

    default byte readByte() {
        byte t = getData()[getInternalOffset()];
        addInternalOffset(1);

        return t;
    }

    /**
     * Reads a byte from an offset
     * @param offset Offset to read from
     */
    default int readByteAsInt(int offset) {
        return BitConverter.toInts(readBytes(offset,1))[0];
    }

    /**
     * Reads a byte from an internal offset
     */
    default int readByteAsInt() {
        return readByteAsInt(getInternalOffset());
    }

    default long readLong(int offset) {
        byte[] t = readBytes(offset, 4);

        return BitConverter.toInt32(t);
    }

    default long readLong() {
        return readLong(getInternalOffset());
    }

    /**
     * Reads a 16 bit word from an offset
     * @param offset Offset to read from
     */
    default int readWord(int offset) {
        int[] words = BitConverter.toInts(readBytes(offset,2));

        return ((words[1] << 8) + (words[0]));
    }

    /**
     * Reads a 16 bit word from the internalOffset
     */
    default int readWord() {
        int word = readWord(getInternalOffset());

        addInternalOffset(2);

        return word;
    }

    default void writeWord(int offset, int toWrite) {
        int[] bytes = new int[] {toWrite & 0xFF, (toWrite & 0xFF00) >> 8};
        byte[] nBytes = BitConverter.toBytes(bytes);

        writeBytes(offset, nBytes);
    }

    default void writeWord(int toWrite) {
        writeWord(getInternalOffset(), toWrite);
        addInternalOffset(2);
    }

    void writeByte(byte value, int offset);

    /**
     * Write an array of bytes to the ROM at a given offset
     * @param offset Offset to write the bytes at
     * @param bytesToWrite Bytes to write to the ROM
     */
    default void writeBytes(int offset, byte[] bytesToWrite) {
        for (int count = 0; count < bytesToWrite.length; count++) {
            try {
                writeByte(bytesToWrite[count], offset);
                offset++;
            } catch (Exception e) {
                String message = String.format("Tried to write out of bounds (%s)", offset);
                System.out.println(message);
            }
        }
    }

    default void writeByte(byte b) {
        writeByte(b, getInternalOffset());
        addInternalOffset(1);
    }

    /**
     * Write an array of bytes to the ROM at a given offset
     * @param bytesToWrite Bytes to write to the ROM
     */
    default void writeBytes(byte[] bytesToWrite) {
        writeBytes(getInternalOffset(), bytesToWrite);
        addInternalOffset(bytesToWrite.length);
    }

    /**
     * Convert a string offset i.e 0x943BBD into a decimal
     * Used for directly accessing the ROM byte array
     * @param offset Offset to convert to an integer
     * @return The offset as an int
     */
    default int convertOffsetToInt(String offset) {
        return Integer.parseInt(offset, 16);
    }

    /**
     * Validate the file loaded based on a given byte and offset
     * @param offset Offset to check in the ROM
     * @param validByte Byte to check it with
     * @return
     */
    default boolean validateROM(int offset, byte validByte) {
        return getData()[offset] == validByte;
    }

    void setCurrentHeader(byte[] romHeader);
    byte[] getCurrentHeader();

    /**
     * Retrieve the header of the ROM, based on offset and size
     * Identical to readBytesFromROM just with a different name
     */
    @Deprecated
    default byte[] getROMHeader(String headerOffset, int headerSize) {
        byte[] header = readBytes(headerOffset, headerSize);
        setCurrentHeader(header);

        return header;
    }

    /**
     * Return a string of the friendly ROM header based on the current ROM
     */
    default String getFriendlyHeader() {
        return HeaderNames.get(new String(getCurrentHeader()));
    }

    /**
     * Read a structure of data from the ROM at a given offset, a set number of times, with a set structure size
     * For example returning the names of Pokemon into an ArrayList of bytes
     * @param offset Offset to read the structure from
     * @param amount Amount to read
     * @param maxStructSize Maximum structure size
     */
    default List<byte[]> loadArrayOfStructuredData(int offset, int amount, int maxStructSize) {
        List<byte[]> data = new ArrayList<>();
        int offs = offset & 0x1FFFFFF;

        for (int count = 0; count < amount; count++) {
            byte[] tempByte = new byte[maxStructSize];

            for (int c2 = 0; c2 < tempByte.length; c2++) {
                tempByte[c2] = getData()[offs];
                offs++;
            }

            data.add(tempByte);
        }

        return data;
    }

    /**
     * Reads ASCII text from the ROM
     * @param offset The offset to read from
     * @param length The amount of text to read
     * @return The text as a String object
     */
    default String readText(int offset, int length) {
        return new String(BitConverter.getBytes(getData(), offset, length));
    }

    default String readPokeText(int length) {
        String text = readPokeText(getInternalOffset(), length);
        addInternalOffset(length);

        return text;
    }

    default String readPokeText(int offset, int length) {
        // If length set, return converted
        if (length > -1) {
            return PokeText.toAscii(BitConverter.getBytes(getData(), offset, length));
        }

        // Grab till end of text
        byte b = 0x0;
        int i = 0;

        while (b != -1) {
            b = getData()[offset + i];
            i++;
        }

        return PokeText.toAscii(BitConverter.getBytes(getData(), offset, i));
    }

    /**
     * Gets a pointer at an offset
     * @param offset Offset to get the pointer from
     * @param fullPointer Whether we should fetch the full 32 bit pointer or the 24 bit byte[] friendly version.
     * @return Pointer as a long
     */
    default long getPointer(int offset, boolean fullPointer) {
        byte[] data = BitConverter.getBytes(getData(), offset, 4);

        if(!fullPointer) {
            data[3] = 0;
        }

        return BitConverter.toInt32(data);
    }

    /**
     * Gets a 24 bit pointer in the ROM as an integer.
     * @param offset Offset to get the pointer from
     * @return Pointer as a Long
     */
    default long getPointer(int offset) {
        return getPointer(offset,false) & 0x1FFFFFF;
    }

    /**
     * Gets a pointer in the ROM as an integer.
     * Does not support 32 bit pointers due to Java's integer size ({@link Integer#MAX_VALUE}) not being long enough.
     * @param offset Offset to get the pointer from
     * @return Pointer as an Integer
     */
    default int getPointerAsInt(int offset) {
        return (int) getPointer(offset,false);
    }

    default long getSignedLong(boolean fullPointer) {
        byte[] data = BitConverter.getBytes(getData(), getInternalOffset(), 4);

        if(!fullPointer) {
            data[3] = 0;
        }

        addInternalOffset(4);

        long pointer = BitConverter.toInt32(data);
        return data[3] > 0x7F ? ~pointer : pointer;
    }

    /**
     * Reverses and writes a pointer to the ROM
     * @param pointer Pointer to write
     * @param offset Offset to write it at
     */
    default void writePointer(long pointer, int offset) {
        byte[] bytes = BitConverter.getBytes(pointer);
        writeBytes(offset, bytes);
    }

    /**
     * Reverses and writes a pointer to the ROM. Assumes pointer is ROM memory and appends 08 to it.
     * @param pointer Pointer to write (appends 08 automatically)
     * @param offset Offset to write it at
     */
    default void writePointer(int pointer, int offset) {
        byte[] bytes = BitConverter.getBytes(pointer);

        bytes = BitConverter.reverseBytes(bytes);
        bytes[3] = 0x08;

        writeBytes(offset, bytes);
    }

    /**
     * Gets the game code from the ROM, ie BPRE for US Pkmn Fire Red
     * @see #getGame()
     */
    String getGameCode();

    /**
     * Gets the Game from the ROM, ie {@link Game#FIRE_RED} for US Pokémon Fire Red
     */
    Game getGame();

    /**
     * Gets the game text from the ROM, ie POKEMON FIRE for US Pkmn Fire Red
     */
    String getGameText();

    /**
     * Gets the game creator ID as a String, ie '01' is GameFreak's Company ID
     */
    String getGameCreatorId();

    /**
     * Sets the game code, the game creator and the game creator ID
     */
    void setGameVars(String code, String name, String maker);

    /**
     * Update some ROM flags depending on the Pokémon game
     */
    default void updateFlags() {
        String code = getGameCode();

        if (code.equals("BPRE")) {
            // Is this a function pointer?
            if (readByte(0x082903) == 0x8) {
                setDNPokePatchAdded(true);
            }

            // Is interdepth's RTC in there?
            if (readByte(0x427) == 0x8) {
                setRTCAdded(true);
            }
        } else if (code.equals("BPEE")) {
            setRTCAdded(true);

            if (readByte(0x0B4C7F) == 0x8) {
                setDNPokePatchAdded(true);
            }
        }
    }

    // Flags getters and setters
    boolean isDNPokePatchAdded();

    boolean isRTCAdded();

    void setDNPokePatchAdded(boolean added);

    void setRTCAdded(boolean added);

    /**
     * Gets a pointer at an offset
     * @param fullPointer Whether we should fetch the full 32 bit pointer or the 24 bit byte[] friendly version.
     * @return Pointer as a Long
     */
    default long getPointer(boolean fullPointer) {
        byte[] data = BitConverter.getBytes(getData(), getInternalOffset(), 4);

        if (!fullPointer && data[3] >= 0x8) {
            data[3] -= 0x8;
        }

        addInternalOffset(4);
        return BitConverter.toInt32(data);
    }

    /**
     * Gets a 24 bit pointer in the ROM as an integer.
     * @return Pointer as a Long
     */
    default long getPointer() {
        return getPointer(false);
    }

    /**
     * Gets a pointer in the ROM as an integer.
     * Does not support 32 bit pointers due to Java's integer size not being long enough.
     * @return Pointer as an Integer
     */
    default int getPointerAsInt() {
        return (int) getPointer(false);
    }

    /**
     * Reverses and writes a pointer to the ROM
     * @param pointer Pointer to write
     */
    default void writePointer(long pointer) {
        byte[] bytes = BitConverter.reverseBytes(BitConverter.getBytes(pointer));

        writeBytes(getInternalOffset(), bytes);
        addInternalOffset(4);
    }

    default void writeSignedPointer(long pointer) {
        byte[] bytes = BitConverter.reverseBytes(BitConverter.getBytes(pointer));

        writeBytes(getInternalOffset(), bytes);
        addInternalOffset(4);
    }

    /**
     * Reverses and writes a pointer to the ROM. Assumes pointer is ROM memory and appends 08 to it.
     * @param pointer Pointer to write (appends 08 automatically)
     */
    default void writePointer(int pointer) {
        byte[] bytes = BitConverter.reverseBytes(BitConverter.getBytes(pointer));
        bytes[3] += 0x8;

        writeBytes(getInternalOffset(), bytes);
        addInternalOffset(4);
    }

    default void seek(int offset) {
        if(offset > 0x08000000) {
            offset &= 0x1FFFFFF;
        }

        setInternalOffset(offset);
    }

    // Hacky way to make ROM, offset constructors work
    default ROM seekAndGet(int offset) {
        seek(offset);
        return this;
    }

    default int findFreespace(long freeSpaceSize, int startLoc, boolean asmSafe) {
        byte free = getFreeSpaceByte();
        byte[] searching = new byte[(int) freeSpaceSize];

        for(int i = 0; i < freeSpaceSize; i++) {
            searching[i] = free;
        }

        int numMatches = 0;
        int freespace = -1;

        byte[] bytes = getData();

        for(int i = startLoc; i < bytes.length; i++) {
            byte b = bytes[i];
            byte c = searching[numMatches];

            if(b == c) {
                numMatches++;

                if(numMatches == searching.length - 1) {
                    freespace = i - searching.length + 2;
                    break;
                }
            } else {
                numMatches = 0;
            }
        }

        return freespace;
    }

    default int findFreespace(int length, boolean asmSafe) {
        return findFreespace(length, 0, asmSafe);
    }

    default int findFreespace(long freeSpaceStart, int startLoc) {
        return findFreespace(freeSpaceStart, startLoc, false);
    }

    default int findFreespace(int length) {
        return findFreespace(length, 0, false);
    }

    default void floodBytes(int offset, byte b, int length) {
        if(offset > 0x1FFFFFF) {
            return;
        }

        for(int i = offset; i < offset + length; i++) {
            writeByte(b, i);
        }
    }

    default void repoint(int original, int newPointer, int lookForNum) {
        original |= 0x8000000;

        byte[] searching = BitConverter.reverseBytes(BitConverter.getBytes(original));

        int numMatches = 0;
        int totalMatches = 0;
        int offset;

        byte[] bytes = getData();

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            byte c = searching[numMatches];

            if (b == c) {
                numMatches++;

                if (numMatches == searching.length - 1) {
                    offset = i - searching.length + 2;

                    this.seek(offset);
                    this.writePointer(newPointer);

                    // TODO Should we log the offsets?
                    //System.out.println(BitConverter.toHexString(offset));

                    totalMatches++;

                    if (totalMatches == lookForNum) {
                        break;
                    }

                    numMatches = 0;
                }
            } else {
                numMatches = 0;
            }
        }

        System.out.println("Found " + totalMatches + " occurrences of the pointer specified.");
    }

    default void repoint(int original, int newPointer) {
        repoint(original, newPointer, -1);
    }
}
