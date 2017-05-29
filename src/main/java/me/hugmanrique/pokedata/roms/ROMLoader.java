package me.hugmanrique.pokedata.roms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
public class ROMLoader {
    public static void load(ROM rom, File file) throws IOException {
        // IO loading
        loadRomBytes(rom, file);

        rom.setGameVars(
            // Game code
            rom.readText(0xAC, 4),
            // Game name
            rom.readText(0xA0, 12).trim(),
            // Maker name
            rom.readText(0xB0, 2)
        );

        rom.updateFlags();
    }

    private static void loadRomBytes(ROM rom, File file) throws IOException {
        try (InputStream in = new FileInputStream(file)) {
            long length = file.length();

            byte[] bytes = new byte[(int) length];

            int offset = 0;
            int n;

            while (offset < bytes.length &&
                    (n = in.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += n;
            }

            rom.setData(bytes);
        }
    }
}
