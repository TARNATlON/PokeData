package me.hugmanrique.pokedata.loaders;

import lombok.AllArgsConstructor;
import me.hugmanrique.pokedata.loaders.InvalidDataException;
import org.ini4j.Wini;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
@AllArgsConstructor
public class DataLoader {
    private Wini store;
    private String header;

    public String getString(String key) {
        String value = store.get(header, key);

        return parseString(key, value);
    }

    public int getPointer(String key) {
        String value = getString(key);

        return parsePointer(key, value);
    }

    public int getInt(String key) {
        String value = getString(key);

        return parseInt(key, value);
    }

    private String removeComment(String value) {
        int index = value.indexOf(";");

        if (index != -1) {
            value = value.substring(0, index);
        }

        return value;
    }

    /**
     * Removes comments and checks if null
     */
    private String parseString(String key, String value) {
        if (value == null) {
            throw new InvalidDataException("The " + key + " config value isn't defined");
        }

        return removeComment(value);
    }

    private int parseInt(String key, String value) {
        value = parseString(key, value);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throwConfigException("Value should be an Integer", value);
        }

        return 0;
    }

    private int parsePointer(String key, String value) {
        value = parseString(key, value);

        try {
            if (value.startsWith("0x")) {
                // Remove hex prefix
                value = value.substring(2);
            }

            return Integer.parseInt(value, 16);
        } catch (NumberFormatException e) {
            throwConfigException("Value should be a hex integer", value);
        }

        return 0;
    }

    // TODO Also throw the config key that has the exception
    private void throwConfigException(String message, String value) {
        throw new InvalidDataException(message + ". Got " + value + " instead");
    }
}
