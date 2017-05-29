package me.hugmanrique.pokedata.pokedex.ev;

import lombok.Getter;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
public enum EvolutionType {
    FRIENDSHIP_NOTIME(0x1),
    FRIENDSHIP_DAY(0x2),
    FRIENDSHIP_NIGHT(0x3),
    LEVEL_UP(0x4),
    TRADE_NO_ITEMS(0x5),
    TRADE_WITH_ITEM(0x6),
    USE_ITEM(0x7),
    LEVEL_UP_ATTACK_GREATER_DEFENSE(0x8),
    LEVEL_UP_ATTACK_EQUAL_DEFENSE(0x9),
    LEVEL_UP_ATTACK_LOWER_DEFENSE(0xA),
    LEVEL_UP_PERSONALITY_PERMITS(0xB),
    LEVEL_UP_PERSONALITY_PERMITS_2(0xC),
    LEVEL_UP_OTHER_SPAWN(0xD),
    LEVEL_UP_IF_CONDITIONS(0xE),
    BEAUTY(0xF);

    private byte id;

    EvolutionType(int id) {
        this.id = (byte) id;
    }

    public static EvolutionType byId(int value) {
        for (EvolutionType type : values()) {
            if (type.getId() == value) {
                return type;
            }
        }

        return null;
    }
}
