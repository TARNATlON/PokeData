package me.hugmanrique.pokedata.pokedex.ev;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hugmanrique.pokedata.loaders.InvalidDataException;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
@AllArgsConstructor
@Getter
public enum EvolutionParam {
    NONE("none"),
    EVOLVES_NO_PARAMS("evolvesbutnoparms"),
    LEVEL("level"),
    ITEM("item"),
    EVOLVES_BASEDONVALUE("evolvesbasedonvalue");

    private String config;

    public static EvolutionParam byConfig(String value) {
        for (EvolutionParam type : values()) {
            if (type.getConfig().equals(value)) {
                return type;
            }
        }

        throw new InvalidDataException("Config type '" + value + "' isn't a valid EvolutionType value");
    }
}
