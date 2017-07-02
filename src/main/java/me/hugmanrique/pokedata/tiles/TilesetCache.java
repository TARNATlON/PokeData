package me.hugmanrique.pokedata.tiles;

import me.hugmanrique.pokedata.maps.MapData;
import me.hugmanrique.pokedata.roms.ROM;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public class TilesetCache {
    private static Map<Integer, Tileset> cache = new HashMap<>();

    public static Tileset get(ROM rom, int offset) {
        if (cache.containsKey(offset)) {
            return cache.get(offset);
        }

        Tileset tileset = Tileset.load(rom, offset);
        cache.put(offset, tileset);

        return tileset;
    }

    public static void switchTileset(ROM rom, MapData mapData) {
        Tileset global = get(rom, mapData.getGlobalTilesetPtr());
        Tileset local = get(rom, mapData.getLocalTilesetPtr());

        // TODO See if required, replace by dynamic palette selection, no need to clone
        for (int j = 0; j < 4; j++) {
            for (int i = Tileset.MAIN_PAL_COUNT - 1; i < 13; i++) {
                global.getPalette(j)[i] = local.getPalette(j)[i];
            }

            local.getPalettes()[j] = global.getPalette(j);
        }

        global.renderPalettedTiles();
        local.renderPalettedTiles();

        global.startTileLoaders();
        local.startTileLoaders();
    }
}
