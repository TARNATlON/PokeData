package me.hugmanrique.pokedata.maps.blocks;

import me.hugmanrique.pokedata.roms.Game;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public enum TripleType {
    NONE,
    LEGACY,
    LEGACY2,
    REFERENCE;

    public static TripleType getType(long behaviour, Game game) {
        if ((behaviour & 0x30) == 0x30) {
            return LEGACY;
        }

        if ((behaviour & 0x40) == 0x40) {
            return LEGACY2;
        } else if ((behaviour & 0x60) == 0x60 && game.isElements()) {
            return REFERENCE;
        }

        return NONE;
    }
}
