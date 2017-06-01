package me.hugmanrique.pokedata.compression;

import me.hugmanrique.pokedata.compression.huff.HuffTreeNode;
import me.hugmanrique.pokedata.compression.huff.LinkedList;
import me.hugmanrique.pokedata.utils.Pair;

import java.io.EOFException;
import java.io.IOException;

/**
 * DSDecmp
 * https://github.com/barubary/dsdecmp
 * code.google.com/p/dsdecmp
 *
 * Adapted to be used in PokeData
 * @author Barubary
 * @since 29/05/2017
 */
public class CompressUtils {
    public static int[] decompress(HexInputStream stream) throws Exception {
        switch (stream.readU8()) {
            case 0x10:
                return decompress10Lz(stream);
            case 0x11:
                return decompress11Lz(stream);
            case 0x24:
            case 0x28:
                return decompressHuff(stream);
            case 0x30:
                return decompressRle(stream);
            default:
                return null;
        }
    }

    private static int getLength(HexInputStream stream) throws IOException {
        int length = 0;

        for (int i = 0; i < 3; i++) {
            length = length | (stream.readU8() << (i * 8));
        }

        // 0 length isn't possible, read 4 next bytes
        if (length == 0) {
            length = stream.readLS32();
        }

        return length;
    }

    private static int[] decompress10Lz(HexInputStream stream) throws Exception {
        int[] out = new int[getLength(stream)];

        int size = 0;
        int flags;
        boolean flag;
        int disp, n, b, cdest;

        while (size < out.length) {
            try {
                flags = stream.readU8();
            } catch (EOFException e) {
                break;
            }

            for (int i = 0; i < 8; i++) {
                flag = (flags & (0x80 >> i)) > 0;

                if (flag) {
                    disp = 0;

                    try {
                        b = stream.readU8();
                    } catch (EOFException e) {
                        throw new IncompleteDataException(e);
                    }

                    n = b >> 4;
                    disp = (b & 0x0F) << 8;

                    try {
                        disp |= stream.readU8();
                    } catch (EOFException e) {
                        throw new IncompleteDataException(e);
                    }

                    n += 3;
                    cdest = size;

                    if (disp > size) {
                        throw new BackMoreThanWrittenException();
                    }

                    for (int j = 0; j < n; j++) {
                        out[size++] = out[cdest - disp - 1 + j];
                    }

                    if (size > out.length) {
                        break;
                    }
                } else {
                    try {
                        b = stream.readU8();
                    } catch (EOFException e) {
                        break;
                    }

                    try {
                        out[size++] = b;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        if (b == 0) {
                            break;
                        }
                    }

                    if (size > out.length) {
                        break;
                    }
                }
            }
        }

        return out;
    }

    private static int[] decompress11Lz(HexInputStream stream) throws Exception {
        int[] out = new int[getLength(stream)];

        int size = 0;
        int flags;
        boolean flag;
        int b1, bt, b2, b3, len, disp, cDest;

        while (size < out.length) {
            try {
                flags = stream.readU8();
            } catch (EOFException e) {
                break;
            }

            for (int i = 0; i < 8 && size < out.length; i++) {
                flag = (flags & (0x80 >> i)) > 0;

                if (flag) {
                    try {
                        b1 = stream.readU8();
                    } catch (EOFException e) {
                        throw new IncompleteDataException(e);
                    }

                    switch (b1 >> 4) {
                        case 0:
                            /*
                            ab cd ef
                            =>
                            len = abc + 0x11 = bc + 0x11
                             */

                            len = b1 << 4;

                            try {
                                bt = stream.readU8();
                            } catch (EOFException e) {
                                throw new IncompleteDataException(e);
                            }

                            len |= bt >> 4;
                            len += 0x11;

                            disp = (bt & 0x0F) << 8;

                            try {
                                b2 = stream.readU8();
                            } catch (EOFException e) {
                                throw new IncompleteDataException(e);
                            }

                            disp |= b2;
                            break;
                        case 1:
                            /*
                            ab cd ef gh
                            =>
                            len = bcde + 0x111
                            disp = fgh
                            10 04 92 3F => disp = 0x23F, len = 0x149 + 0x11 =
                            0x15A
                             */

                            try {
                                bt = stream.readU8();
                                b2 = stream.readU8();
                                b3 = stream.readU8();
                            } catch (EOFException e) {
                                throw new IncompleteDataException(e);
                            }

                            len = (b1 & 0xF) << 12;
                            len |= bt << 4;
                            len |= (b2 >> 4);
                            len += 0x111;
                            disp = (b2 & 0x0F) << 8;
                            disp |= b3;

                            break;
                        default:
                            /*
                            ab cd
                            =>
                            len = a + threshold = a + 1
                            disp = bcd
                             */

                            len = (b1 >> 4) + 1;
                            disp = (b1 & 0x0F) << 8;

                            try {
                                b2 = stream.readU8();
                            } catch (EOFException e) {
                                throw new IncompleteDataException(e);
                            }

                            disp |= b2;
                            break;
                    }

                    if (disp > size) {
                        throw new BackMoreThanWrittenException();
                    }

                    cDest = size;

                    for (int j = 0; j < len && size < out.length; j++) {
                        out[size++] = out[cDest - disp - 1 + j];
                    }

                    if (size > out.length) {
                        break;
                    }
                } else {
                    try {
                        out[size++] = stream.readU8();
                    } catch (EOFException e) {
                        break;
                    }

                    if (size > out.length) {
                        break;
                    }
                }
            }
        }

        return out;
    }

    private static int[] decompressHuff(HexInputStream stream) throws Exception {
        stream.skip(-1);

        int firstByte = stream.readU8();
        int size = firstByte & 0x0F;

        if (size != 8 && size != 4) {
            throw new Exception("Unhandled data size " + Integer.toHexString(size));
        }

        int decompSize = getLength(stream);
        int treeSize = stream.readU8();
        HuffTreeNode.setMaxInPos((treeSize + 1) * 2 + 4);

        HuffTreeNode root = new HuffTreeNode();
        root.parseData(stream);

        // Go back to the start of encoded bit stream
        stream.setPosition((treeSize + 1) * 2 + 4);

        // Read all the data
        int[] in = new int[(int) (stream.available() - stream.getPos()) / 4];

        for (int i = 0; i < in.length; i++) {
            in[i] = stream.readS32();
        }

        int currentSize = 0;
        decompSize *= (size == 8 ? 1 : 2);

        int[] out = new int[decompSize];

        int idx = -1;
        String codeStr = "";

        LinkedList<Integer> code = new LinkedList<>();

        while (currentSize < decompSize) {
            try {
                codeStr += Integer.toBinaryString(in[++idx]);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new Exception("Not enough data", e);
            }

            while (codeStr.length() > 0) {
                code.addFirst(Integer.parseInt(codeStr.charAt(0) + ""));
                codeStr = codeStr.substring(1);

                Pair<Boolean, Integer> attempt = root.getValue(code.getTail());

                if (attempt.getFirst()) {
                    try {
                        out[currentSize++] = attempt.getSecond();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        if (code.getHead().getValue() != 0) {
                            throw e;
                        }
                    }

                    code.clear();
                }
            }
        }

        if (!codeStr.isEmpty() || idx < in.length - 1) {
            while (idx < in.length - 1) {
                codeStr += Integer.toBinaryString(in[++idx]);
            }

            codeStr = codeStr.replace("0", "");

            if (codeStr.length() > 0) {
                throw new Exception("Too much data, str=" + codeStr + ", idx=" + idx + "/" + in.length);
            }
        }

        int[] realOut;

        if (size == 4) {
            realOut = new int[decompSize / 2];

            for (int i = 0; i < decompSize / 2; i++) {
                if ((out[i * 2] & 0xF0) > 0 || (out[i * 2 + 1] & 0xF0) > 0) {
                    throw new Exception("First 4 bytes of data should be 0 if data size is 4");
                }

                realOut[i] = (byte) ((out[i * 2] << 4) | out[i * 2 + 1]);
            }
        } else {
            realOut = out;
        }

        return realOut;
    }
}
