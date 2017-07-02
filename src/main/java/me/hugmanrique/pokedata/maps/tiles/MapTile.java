package me.hugmanrique.pokedata.maps.tiles;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
@AllArgsConstructor
@Getter
public class MapTile {
    private int id;
    private int meta;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new MapTile(id, meta);
    }
}
