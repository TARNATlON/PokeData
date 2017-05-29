package me.hugmanrique.pokedata.attacks;

import lombok.Getter;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
@Getter
public enum Affects {
    SELECTED_TARGET(0x0),
    DEPENDS_ATTACK(0x01),
    UNUSED(0x02),
    RANDOM(0x04),
    BOTH_FOES(0x08),
    USER(0x10),
    BOTH_FOES_AND_PARTNER(0x20),
    OPPONENT_FIELD(0x40);

    private byte id;

    Affects(int id) {
        this.id = (byte) id;
    }

    // TODO Combinated attacks, convert to Affects[]
    public static Affects byId(byte id) {
        for (Affects affects : values()) {
            if (affects.getId() == id) {
                return affects;
            }
        }

        return null;
    }
}
