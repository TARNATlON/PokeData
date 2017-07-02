package me.hugmanrique.pokedata.maps;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.maps.tiles.MapTile;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public class MapTileData extends Data {
    private int pointer;
    private int size;

    private MapTile[][] tiles;

    public MapTileData(ROM rom, MapData data) {
        int width = (int) data.getWidth();
        int height = (int) data.getHeight();

        tiles = new MapTile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = (y * width) + x;

                int shortened = BitConverter.shortenPointer(data.getMapTilesPtr() + index * 2);
                int raw = rom.readWord(shortened);

                MapTile tile = new MapTile((raw & 0x3FF), (raw & 0xFC00) >> 10);
                tiles[x][y] = tile;
            }
        }





    }
}
