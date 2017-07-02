package me.hugmanrique.pokedata.items;

import lombok.Getter;
import me.hugmanrique.pokedata.roms.Game;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
@Getter
public enum Pocket {
    ITEMS(1, 1),
    KEY_ITEMS(2, 0),
    POKE_BALLS(3, 2),
    BERRIES(4, 4),
    TMS_AND_HMS(5, 3);

    /**
     * ID for FireRed and LeafGreen games
     */
    private byte element;

    /**
     * ID for Emerald, Ruby and Sapphire games
     */
    private byte gem;

    Pocket(int element, int gem) {
        this.element = (byte) element;
        this.gem = (byte) gem;
    }

    public static Pocket byId(Game game, byte id) {
        if (game.isGem()) {
            for (Pocket pocket : values()) {
                if (pocket.getGem() == id) {
                    return pocket;
                }
            }
        } else {
            // TODO Fix Custom game support for gem games
            for (Pocket pocket : values()) {
                if (pocket.getElement() == id) {
                    return pocket;
                }
            }
        }

        return null;
    }



}
