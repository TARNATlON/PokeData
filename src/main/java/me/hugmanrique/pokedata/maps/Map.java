package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * Bulbapedia article missing
 * As the map data class contains a lot of values, each "section" is
 * separated into child classes
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
public class Map extends Data {
    private MapHeader header;

    public Map(ROM rom) {
        header = new MapHeader(rom);
    }
}
