package me.hugmanrique.pokedata.roms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum of currently supported games
 * @author Hugmanrique
 * @since 28/05/2017
 */
@AllArgsConstructor
@Getter
public enum Game {
    RUBY("AXV"),
    SAPPHIRE("AXP"),
    FIRE_RED("BPR"),
    LEAF_GREEN("BPG"),
    EMERALD("BPE"),
    CUSTOM("___");

    private String id;

    public static Game byId(String id) {
        for (Game game : values()) {
            // IDs are in xxxx format
            // Language is the last char, ignore it
            if (id.startsWith(game.getId())) {
                return game;
            }
        }

        return CUSTOM;
    }

    public boolean isElements() {
        return this == FIRE_RED || this == LEAF_GREEN;
    }

    public boolean isGem() {
        return this == RUBY || this == SAPPHIRE || this == EMERALD;
    }
}
