package me.hugmanrique.pokedata.compression;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public class BackMoreThanWrittenException extends Exception {
    public BackMoreThanWrittenException() {
        super("Tried to go back more than already written");
    }
}
