package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.maps.blocks.BlockRenderer;
import me.hugmanrique.pokedata.tiles.MapTile;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.tiles.Tileset;
import me.hugmanrique.pokedata.tiles.TilesetCache;
import me.hugmanrique.pokedata.utils.BitConverter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
@Getter
@ToString
public class MapTileData extends Data {
    private static final int TILE_COLS = 8;

    private int pointer;
    private int size;

    private MapTile[][] tiles;

    private BufferedImage imageBuffer;
    private Graphics buffer;

    public MapTileData(ROM rom, MapData data) {
        int width = (int) data.getWidth();
        int height = (int) data.getHeight();

        tiles = new MapTile[width][height];
        pointer = data.getMapTilesPtr();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = (y * width) + x;

                int shortened = BitConverter.shortenPointer(pointer + index * 2);
                int raw = rom.readWord(shortened);

                MapTile tile = new MapTile((raw & 0x3FF), (raw & 0xFC00) >> 10);
                tiles[x][y] = tile;
            }
        }

        calcSize(width, height);
    }

    public BufferedImage drawTileset(ROM rom, MapData mapData) {
        return drawTileset(rom, BlockRenderer.DEFAULT, mapData);
    }

    /**
     * Creates an {@link Image} with a grid of all the tiles from the global and local tileset
     */
    public BufferedImage drawTileset(ROM rom, BlockRenderer renderer, MapData mapData) {
        Tileset global = TilesetCache.get(rom, mapData.getGlobalTilesetPtr());
        Tileset local = TilesetCache.get(rom, mapData.getLocalTilesetPtr());

        renderer.setTilesets(global, local);

        Dimension imageDims = getDimension();
        imageBuffer = rerenderTiles(rom, imageDims, renderer, 0, Tileset.MAIN_BLOCKS + 0x200, false);

        return imageBuffer;
    }

    private BufferedImage rerenderTiles(ROM rom, Dimension dimension, BlockRenderer renderer, int start, int end, boolean complete) {
        if (complete && rom.getGame().isGem()) {
            dimension.height = 3048;
        }

        BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);

        buffer = image.getGraphics();

        for (int i = start; i < end; i++) {
            int x = (i % TILE_COLS) * 16;
            int y = (i / TILE_COLS) * 16;

            buffer.drawImage(renderer.renderBlock(rom, i), x, y, null);
        }

        return image;
    }

    private Dimension getDimension() {
        int height = (Tileset.MAIN_SIZE / TILE_COLS + Tileset.LOCAL_SIZE / TILE_COLS) * 16;

        return new Dimension(TILE_COLS * 16, height);
    }

    private void calcSize(int width, int height) {
        size = width * height * 2;
    }
}
