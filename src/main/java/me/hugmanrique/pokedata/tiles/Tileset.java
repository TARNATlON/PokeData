package me.hugmanrique.pokedata.tiles;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
@Getter
public class Tileset extends Data {
    private static final byte[] GLOBAL_TSLZ_HEADER = new byte[]{ 10, 80, 9, 00, 32, 00, 00 };
    private static final byte[] LOCAL_TSLZ_HEADER = new byte[]{ 10, 80, 9, 00, 32, 00, 00 };

    public static final int MAIN_PAL_COUNT = 6;

    private static final int MAIN_HEIGHT = 0x100;
    private static final int LOCAL_HEIGHT = 0x100;

    public static final int MAIN_SIZE = 0x200;
    public static final int LOCAL_SIZE = 0x200;

    public static final int MAIN_BLOCKS = 0x200;
    public static int LOCAL_BLOCKS = 0xFE;

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

        render(rom);
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
                int offset = (int) header.getPalettesPtr() + (32 * j) + (i * 0x200);

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
        final int height = getHeight();

        if (header.isCompressed()) {
            data = Lz77.decompress(rom, imgDataPtr);
        } else {
            // Pull uncompressed data
            int size = height * 128 / 2;

            data = BitConverter.toInts(rom.readBytes(imgDataPtr, size));
        }

        renderedTiles = new HashMap[16 * 4];

        for (int i = 0; i < 16 * 4; i++) {
            renderedTiles[i] = new HashMap<>();
        }

        image = new ROMImage(palettes[0][0], data, 128, height);
    }

    public void startTileLoaders() {
        for (int i = 0; i < (header.isPrimary() ? MAIN_PAL_COUNT : 13); i++) {
            new TileLoader(this, renderedTiles, i).start();
        }
    }

    public BufferedImage getTile(int tileIndex, int palette, boolean flipX, boolean flipY, int time) {
        if (palette < MAIN_PAL_COUNT) {
            // Check if tile is cached
            Map<Integer, BufferedImage> tiles = renderedTiles[palette + (time * 16)];

            if (tiles.containsKey(tileIndex)) {
                BufferedImage image = tiles.get(tileIndex);

                return applyTransforms(image, flipX, flipY);
            }
        } else if (palette >= 13) {
            String error = String.format(
                "[WARN] Attempted to read tile %s of palette %s in %s tileset",
                tileIndex,
                palette,
                header.isPrimary() ? "global" : "local"
            );

            System.out.println(error);

            // Return empty image
            return new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        }

        // Tile isn't cached
        int x = (tileIndex % (128 / 8)) * 8;
        int y = (tileIndex / (128 / 8)) * 8;

        BufferedImage image = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);

        try {
            image = images[time][palette].getSubimage(x, y, 8, 8);
        } catch (Exception ignored) {} // Out of bounds

        if (palette < MAIN_PAL_COUNT || renderedTiles.length > MAIN_PAL_COUNT) {
            renderedTiles[palette + (time * 16)].put(tileIndex, image);
        }

        return applyTransforms(image, flipX, flipY);
    }

    private BufferedImage applyTransforms(BufferedImage image, boolean flipX, boolean flipY) {
        return ImageUtils.applyTransforms(image, flipX, flipY);
    }

    public void renderPalettedTiles() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 16; i++) {
                rerenderTileSet(i, j);
            }
        }
    }

    private void rerenderTileSet(int palette, int time) {
        images[time][palette] = image.getImage(palettes[time][palette]);
    }

    public Palette[] getPalette(int time) {
        return palettes[time];
    }

    public void setPalettes(int time, Palette[] palettes) {
        this.palettes[time] = palettes;
    }

    private int getHeight() {
        return header.isPrimary() ? MAIN_HEIGHT : LOCAL_HEIGHT;
    }
}
