package me.hugmanrique.pokedata.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class PokeText {
    private static Map<String, String> hexTable = new HashMap<>();

    /**
     * Load a HEX table file for character mapping i.e. Pokétext
     * @param tablePath File path to the character table
     * @throws IOException
     */
    public static void loadFromFile(String tablePath) throws IOException {
        File file = new File(tablePath);
        List<String> lines = Files.readAllLines(file.toPath());

        for (String line : lines) {
            String[] separated = line.split("=");

            String key = separated[0];
            String value = separated.length > 1 ? separated[1] : " ";

            hexTable.put(key, value);
        }
    }

    /**
     * Convert Poketext to ascii, takes an array of bytes of poketext
     * @param pokeText Poketext as a byte array
     * @return The results from the given HEX Table <- must {@link #loadFromFile(String)} first
     */
    public static String toAscii(byte[] pokeText) {
        StringBuilder converted = new StringBuilder();

        for (int i = 0; i < pokeText.length; i++) {
            String value = hexTable.get(String.format("%02X", pokeText[i]));

            converted.append(value);
        }

        return converted.toString().trim();
    }
}
