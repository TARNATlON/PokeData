package me.hugmanrique.pokedata.compression;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class HexInputStream {
    private volatile InputStream stream;
    private Stack<Long> positionStack;

    @Getter
    private volatile long pos;

    public HexInputStream(InputStream baseStream) {
        this.stream = baseStream;
        this.pos = 0;
        this.positionStack = new Stack<>();
    }

    public void skip(long n) throws IOException {
        pos += stream.skip(n);
    }

    public void setPosition(long newPos) throws IOException {
        skip(newPos - pos);
    }

    /**
     * Returns an estimate of the number of bytes left to read until the EOF
     * @see InputStream#available()
     */
    public int available() throws IOException {
        return stream.available();
    }

    /**
     * Reads a byte
     */
    public int readU8() throws IOException {
        int b = stream.read();
        pos++;

        return b;
    }

    /**
     * Reads a BigEndian s16
     */
    public short readS16() throws IOException {
        short word = 0;

        for (int i = 0; i < 2; i++) {
            word = (short) (word | (readU8() << (8 * i)));
        }

        return word;
    }

    /**
     * Reads a LittleEndian s16
     */
    public short readLS16() throws IOException {
        short word = 0;

        for (int i = 0; i < 2; i++) {
            word = (short) ((word << 8) | readU8());
        }

        return word;
    }

    /**
     * Reads a BigEndian u16
     */
    public int readU16() throws IOException {
        int word = 0;

        for (int i = 0; i < 2; i++) {
            word = word | (readU8() << (8 * i));
        }

        return word;
    }

    /**
     * Reads a LittleEndian u16
     */
    public int readLU16() throws IOException {
        int word = 0;

        for (int i = 0; i < 2; i++) {
            word = (word << 8) | readU8();
        }

        return word;
    }

    /**
     * Reads a BigEndian s32 (signed int)
     */
    public int readS32() throws IOException {
        int dword = 0;

        for (int i = 0; i < 4; i++) {
            dword = dword | (readU8() << (8 * i));
        }

        return dword;
    }

    /**
     * Reads a LittleEndian s32 (unsigned int)
     */
    public int readLS32() throws IOException {
        int dword = 0;

        for (int i = 0; i < 4; i++) {
            dword = (dword << 8) | readU8();
        }

        return dword;
    }

    /**
     * Reads a BigEndian u32 (unsigned int)
     */
    public long readU32() throws IOException {
        long dword = 0;

        for (int i = 0; i < 4; i++) {
            dword = dword | (readU8() << (8 * i));
        }

        return dword;
    }

    /**
     * Reads a LittleEndian u32 (unsigned int)
     */
    public long readLU32() throws IOException {
        long dword = 0;

        for (int i = 0; i < 4; i++) {
            dword = (dword << 8) | readU8();
        }

        return dword;
    }

    /**
     * Reads a BigEndian s64 (signed int)
     */
    public long readS64() throws IOException {
        long qword = 0;

        for (int i = 0; i < 8; i++) {
            qword = qword | (readU8() << (8 * i));
        }

        return qword;
    }

    /**
     * Reads a LittleEndian s64 (signed int)
     */
    public long readLS64() throws IOException {
        long qword = 0;

        for (int i = 0; i < 8; i++) {
            qword = (qword << 8) | readU8();
        }

        return qword;
    }
}
