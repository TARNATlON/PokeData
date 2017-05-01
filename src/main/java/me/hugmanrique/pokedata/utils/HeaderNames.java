package me.hugmanrique.pokedata.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class HeaderNames {
    private static Map<String, String> names = new HashMap<>();

    static {
        names.put("POKEMON FIREBPRE01", "Pokémon: FireRed");
        names.put("POKEMON LEAFBPGE01", "Pokémon: LeafGreen");
        names.put("POKEMON EMERBPEE01", "Pokémon: Emerald");
    }

    public static String get(String nonFriendly) {
        return names.get(nonFriendly);
    }

    /**
     * Updates the list of friendly ROM headers
     */
    public static void updateNames() {
        // TODO: Load header list from file or .ini
    }
}
