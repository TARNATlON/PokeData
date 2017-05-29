package me.hugmanrique.pokedata.compression;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class IncompleteDataException extends Exception {
    public IncompleteDataException(Throwable cause) {
        super("Incomplete data found", cause);
    }

    public IncompleteDataException() {
        super("Incomplete data found");
    }
}
