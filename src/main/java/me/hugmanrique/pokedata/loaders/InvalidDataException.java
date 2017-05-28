package me.hugmanrique.pokedata.loaders;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
public class InvalidDataException extends IllegalArgumentException {
    public InvalidDataException(String s) {
        super(s);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataException(Throwable cause) {
        super(cause);
    }
}
