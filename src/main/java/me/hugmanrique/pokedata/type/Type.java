package me.hugmanrique.pokedata.type;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
@ToString
public enum Type {
    NORMAL(0),
    FIGHTING(1),
    FLYING(2),
    POISON(3),
    GROUND(4),
    ROCK(5),
    BUG(6),
    GHOST(7),
    STEEL(8),
    UNKNOWN(9),
    FIRE(10),
    WATER(11),
    GRASS(12),
    ELECTRIC(13),
    PSYCHIC(14),
    ICE(15),
    DRAGON(16),
    DARK(17);

    private byte id;

    Type(int id) {
        this.id = (byte) id;
    }

    public static Type byId(byte id) {
        for (Type type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        return NORMAL;
    }
}
