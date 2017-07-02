package me.hugmanrique.pokedata.tiles;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public class TileLoader extends Thread implements Runnable {
    private Tileset tileset;
    private Map<Integer, BufferedImage>[] buffer;
    private int palette;

    public TileLoader(Tileset tileset, Map<Integer, BufferedImage>[] hash, int palette) {
        this.tileset = tileset;
        this.buffer = hash;
        this.palette = palette;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1023; i++) {
            try {
                BufferedImage tile = tileset.getTile(i, palette, false, false, 0);

                buffer[palette].put(i, tile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
