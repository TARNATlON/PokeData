package me.hugmanrique.pokedata.utils;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
public class ROMUtils {
    public static boolean isEmerald(ROM rom) {
        String name = rom.getGameCode();

        return name.startsWith("BPE");
    }
}
