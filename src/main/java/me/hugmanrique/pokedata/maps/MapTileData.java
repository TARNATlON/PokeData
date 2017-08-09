package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.blocks.BlockRenderer;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.tiles.MapTile;
import me.hugmanrique.pokedata.tiles.Tileset;
import me.hugmanrique.pokedata.tiles.TilesetCache;

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

    public MapTileData(ROM rom, MapData data) {
        int width = (int) data.getWidth();
        int height = (int) data.getHeight();

        tiles = new MapTile[width][height];
        pointer = data.getMapTilesPtr();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = (y * width) + x;
                int raw = rom.readWord(pointer + index * 2);

                MapTile tile = new MapTile(raw & 0x3FF, (raw & 0xFC00) >> 10);
                tiles[x][y] = tile;
            }
        }

        calcSize(width, height);
    }

    private BufferedImage prepareTileset(ROM rom, ROMData data, BlockRenderer renderer, MapData mapData, boolean complete) {
        Tileset global = TilesetCache.get(rom, data, mapData.getGlobalTilesetPtr());
        Tileset local = TilesetCache.get(rom, data, mapData.getLocalTilesetPtr());

        renderer.setTilesets(global, local);

        Dimension dimension = getDimension(data);

        if (complete && rom.getGame().isGem()) {
            dimension.height = 3048;
        }

        return new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage drawTileset(ROM rom, ROMData data, MapData mapData) {
        return drawTileset(rom, data, BlockRenderer.DEFAULT, mapData);
    }

    /**
     * Creates an {@link Image} with a grid of all the blocks from the global and local tilesets
     */
    public BufferedImage drawTileset(ROM rom, ROMData data, BlockRenderer renderer, MapData mapData) {
        BufferedImage image = prepareTileset(rom, data, renderer, mapData, true);

        return renderTiles(rom, data, renderer, image, 0, data.getMainBlocks() + 0x200);
    }

    public BufferedImage drawTilesetLayer(ROM rom, ROMData data, MapData mapData, int layer) {
        return drawTilesetLayer(rom, data, BlockRenderer.DEFAULT, mapData, layer);

    }

    /**
     * Creates an {@link Image} with a grid of all the blocks' specified layer from the global and local tilesets
     * @param layer 0 or 1
     */
    public BufferedImage drawTilesetLayer(ROM rom, ROMData data, BlockRenderer renderer, MapData mapData, int layer) {
        BufferedImage image = prepareTileset(rom, data, renderer, mapData, true);

        return renderLayer(rom, data, renderer, image, 0, data.getMainBlocks() + 0x200, layer);
    }

    private BufferedImage renderTiles(ROM rom, ROMData data, BlockRenderer renderer, BufferedImage image, int start, int end) {
        Graphics buffer = image.getGraphics();

        for (int i = start; i < end; i++) {
            int x = (i % TILE_COLS) * 16;
            int y = (i / TILE_COLS) * 16;

            buffer.drawImage(renderer.renderBlock(rom, data, i, true), x, y, null);
        }

        buffer.dispose();

        return image;
    }

    private BufferedImage renderLayer(ROM rom, ROMData data, BlockRenderer renderer, BufferedImage image, int start, int end, int layer) {
        Graphics buffer = image.getGraphics();

        for (int i = start; i < end; i++) {
            int x = (i % TILE_COLS) * 16;
            int y = (i / TILE_COLS) * 16;

            buffer.drawImage(renderer.renderBlock(rom, data, i, true, layer), x, y, null);
        }


        buffer.dispose();

        return image;
    }

    private Dimension getDimension(ROMData data) {
        int height = ((data.getMainTilesetSize() / TILE_COLS) + (data.getLocalTilesetSize() / TILE_COLS)) * 16;

        return new Dimension(TILE_COLS * 16, height);
    }

    private void calcSize(int width, int height) {
        size = width * height * 2;
    }
}
