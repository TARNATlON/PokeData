package me.hugmanrique.pokedata.compression;

import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class Lz77 {
    public static int getDataLength(ROM rom, int offset) {
        byte[] data = rom.readBytes(offset, 0x10);

        return (int) BitConverter.toInt32(
            new byte[] {
                data[1],
                data[2],
                data[3],
                0x0
            }
        );
    }

    public static int[] decompress(ROM rom, int offset) {
        InputStream stream = new ByteArrayInputStream(rom.getData());
        HexInputStream hexStream = new HexInputStream(stream);

        try {
            stream.skip(offset);

            return CompressUtils.decompress(hexStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
