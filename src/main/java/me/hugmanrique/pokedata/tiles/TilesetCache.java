package me.hugmanrique.pokedata.tiles;

import me.hugmanrique.pokedata.loaders.ROMData;
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

    public static Tileset get(ROM rom, ROMData data, int offset) {
        if (cache.containsKey(offset)) {
            return cache.get(offset);
        }

        Tileset tileset = Tileset.load(rom, data, offset);
        cache.put(offset, tileset);

        return tileset;
    }

    public static void switchTileset(ROM rom, ROMData data, MapData mapData) {
        Tileset global = get(rom, data, mapData.getGlobalTilesetPtr());
        Tileset local = get(rom, data, mapData.getLocalTilesetPtr());

        for (int j = 0; j < Tileset.MAX_TIME; j++) {
            for (int i = data.getMainPalCount() - 1; i < 13; i++) {
                global.getPalette(j)[i] = local.getPalette(j)[i];
            }

            local.setPalettes(j, global.getPalette(j));
        }

        global.renderPalettedTiles();
        local.renderPalettedTiles();

        global.startTileLoaders();
        local.startTileLoaders();
    }
}
