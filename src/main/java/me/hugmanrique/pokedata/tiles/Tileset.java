package me.hugmanrique.pokedata.tiles;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public class Tileset extends Data {
    private static final int MAIN_HEIGHT = 0x100;
    private static final int LOCAL_HEIGHT = 0x100;

    // Cache last primary as it's used a lot
    private static Tileset lastPrimary;

    private TilesetHeader header;
    private ROMImage image;

    private BufferedImage[][] images;
    private Palette[][] palettes;

    private Map<Integer, BufferedImage>[] renderedTiles;

    private int blockCount;

    public Tileset(ROM rom) {
        header = new TilesetHeader(rom);
        blockCount = 1024;
    }

    public static Tileset load(ROM rom, int offset) {
        rom.seek(offset);

        return new Tileset(rom);
    }

    public void render(ROM rom) {
        renderPalettes(rom);
        renderGraphics(rom);
    }

    private void renderPalettes(ROM rom) {
        palettes = new Palette[4][16];
        images = new BufferedImage[4][16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 16; j++) {
                int offset = (int) header.getPalettesPtr() + (32 * j + i * 0x200);

                byte[] data = rom.readBytes(offset, 32);
                palettes[i][j] = new Palette(ImageType.C16, data);
            }
        }
    }

    private void renderGraphics(ROM rom) {
        int imgDataPtr = (int) header.getTilesetImgPtr();

        if (header.isPrimary()) {
            lastPrimary = this;
        }

        int[] data;

        if (header.isCompressed()) {
            data = Lz77.decompress(rom, imgDataPtr);
        } else {
            // TODO Try to fix ROM by writing data

            // Pull uncompressed data
            int size = getHeight() * 128 / 2;
            data = BitConverter.toInts(rom.readBytes(imgDataPtr, size));
        }

        renderedTiles = new HashMap[16 * 4];

        for (int i = 0; i < 16 * 4; i++) {
            renderedTiles[i] = new HashMap<>();
        }

        image = new ROMImage(palettes[0][0], data, 128, getHeight());
    }

    private int getHeight() {
        return header.isPrimary() ? MAIN_HEIGHT : LOCAL_HEIGHT;
    }
}
