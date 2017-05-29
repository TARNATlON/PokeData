package me.hugmanrique.pokedata.utils;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class BitConverter {
    protected BitConverter() {}

    public static long toInt32(byte[] bytes) {
        // AB CD EF 08 -> 08EFCDAB
        return (long) ((bytes[0] & 0xFF) + ((bytes[1] & 0xFF) << 8) + ((bytes[2] & 0xFF) << 16) + ((bytes[3] & 0xFF) << 24));
    }

    public static int shortenPointer(long pointer) {
        return (int)(pointer & 0x1FFFFFF);
    }

    public static byte[] getBytes(long i) {
        return new byte[] {
            (byte)((i & 0xFF000000) >> 24),
            (byte)((i & 0x00FF0000) >> 16),
            (byte)((i & 0x0000FF00) >> 8),
            (byte)((i & 0x000000FF))
        };
    }

    public static int[] getInts(long i) {
        return new int[] {
            (int)((i & 0xFF000000) >> 24),
            (int)((i & 0x00FF0000) >> 16),
            (int)((i & 0x0000FF00) >> 8),
            (int)((i & 0x000000FF))
        };
    }

    public static byte[] reverseBytes(byte[] bytes) {
        byte[] result = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[bytes.length - 1 - i];
        }

        return result;
    }

    public static byte[] getBytes(byte[] array, int offset, int length) {
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            try {
                result[i] = array[offset + i];
            } catch (ArrayIndexOutOfBoundsException e) {
                String message = String.format("Tried to read outside of bounds (%s, %s)", offset, i);

                System.out.println(message);
            }
        }

        return result;
    }

    public static int[] grabBytesAsInts(byte[] array, int offset, int length) {
        if (length > array.length) {
            length = array.length;
        }

        int[] result = new int[length];

        for (int i = 0; i < length; i++) {
            result[i] = (array[offset + i] & 0xFF);
        }

        return result;
    }

    public static byte[] putBytes(byte[] array, byte[] toPut, int offset) {
        for (int i = 0; i < toPut.length; i++) {
            array[offset + i] = toPut[i];
        }

        return array;
    }

    public static int[] toInts(byte[] array) {
        return grabBytesAsInts(array, 0, array.length);
    }

    public static String toHexString(int loc) {
        return toHexString(loc, false);
    }

    // Use absolute value to prevent negative bytes

    public static String toHexString(int loc, boolean spacing) {
        if (spacing) {
            return String.format("%02X", Math.abs(loc));
        } else {
            return String.format("%X", Math.abs(loc));
        }
    }

    public static String toDwordString(int b, boolean spacing) {
        if (spacing) {
            return String.format("%06X", Math.abs(b));
        } else {
            return String.format("%X", Math.abs(b));
        }
    }

    public static String byteToStringNoZero(int b) {
        if(b != 0) {
            return String.format("%X", Math.abs(b));
        } else {
            return "";
        }
    }


    public static byte[] toBytes(int[] data) {
        byte[] result = new byte[data.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte)(data[i]);
        }

        return result;
    }

    public static boolean zeroedOut(int... words) {
        for (int i : words) {
            if (i != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isBitSet(byte value, int index) {
        return (value & (1 << index)) != 0;
    }

    /**
     * Left-to-right bit range of 2 bytes
     * @param from The bits from 0 (left)
     * @param to The bits from 0 (left)
     */
    public static int getBitRange(int value, int from, int to) {
        int fromA = 16 - to;
        to = 16 - from;

        from = fromA;

        int bits = to - from;
        int rightShifted = value >>> from;
        int mask = (1 << bits) - 1;

        return rightShifted & mask;
    }
}
