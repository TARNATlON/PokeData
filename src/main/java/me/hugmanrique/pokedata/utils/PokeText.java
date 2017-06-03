package me.hugmanrique.pokedata.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class PokeText {
    private static Map<String, String> hexTable = new HashMap<>();

    /**
     * Loads the default PokeTable from this JAR's resources
     * @throws IOException If the PokeTable isn't stored in this JAR
     */
    public static void loadFromJar() throws IOException {
        InputStream in = PokeText.class.getClassLoader().getResourceAsStream("poketable.ini");

        if (in == null) {
            throw new IOException("Cannot find internal PokeTable in PokeData JAR");
        }

        loadFromStream(in);
    }

    /**
     * Load a HEX table file for character mapping i.e. Pokétext
     * @param tableFile File that contains the character table
     */
    public static void loadFromFile(File tableFile) throws IOException {
        loadFromStream(new FileInputStream(tableFile));
    }

    private static void loadFromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] separated = line.split("=");

            String key = separated[0];
            String value = separated.length > 1 ? separated[1] : " ";

            hexTable.put(key, value);
        }

        reader.close();
    }

    /**
     * Convert Poketext to ascii, takes an array of bytes of poketext
     * @param pokeText Poketext as a byte array
     * @return The results from the given HEX Table <- must {@link #loadFromFile(File)} first
     */
    public static String toAscii(byte[] pokeText) {
        StringBuilder converted = new StringBuilder();

        for (int i = 0; i < pokeText.length; i++) {
            String value = hexTable.get(String.format("%02X", pokeText[i]));

            converted.append(value);
        }

        String text = converted.toString().trim();

        return replaceLineBreaks(text);
    }

    public static String replaceLineBreaks(String text) {
        return text.replace("|br|", "\n");
    }
}
