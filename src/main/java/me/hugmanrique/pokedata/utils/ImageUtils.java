package me.hugmanrique.pokedata.utils;

import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Palette} and {@link ROMImage} loader that keeps a palette cache to
 * avoid too many {@link me.hugmanrique.pokedata.compression.HexInputStream} creation to decompress Lz77
 * @author Hugmanrique
 * @since 01/06/2017
 * @see Palette
 * @see ROMImage
 */
public class ImageUtils {
    // TODO Implement size limit
    private static Map<Integer, Palette> paletteCache = new HashMap<>();

    public static Palette getPalette(ROM rom, int pointer, ImageType type, boolean cache) {
        if (cache && paletteCache.containsKey(pointer)) {
            return paletteCache.get(pointer);
        }

        int[] data = Lz77.decompress(rom, pointer);
        Palette palette = new Palette(type, data);

        if (cache) {
            paletteCache.put(pointer, palette);
        }

        return palette;
    }

    public static Palette getPalette(ROM rom, int pointer, ImageType type) {
        return getPalette(rom, pointer, type, true);
    }

    public static Palette getPalette(ROM rom, int pointer) {
        return getPalette(rom, pointer, ImageType.C16);
    }

    public static ROMImage getImage(ROM rom, int pointer, Palette palette, int width, int height) {
        int[] data = Lz77.decompress(rom, pointer);

        return new ROMImage(palette, data, width, height);
    }

    /**
     * Create a custom {@link Palette} and store it in the cache.
     * The cache key is determined by the {@link Color}'s hashcode,
     * and it might create a collision with already
     * stored values (this is highly unlikely though)
     * @param colors
     * @return
     */
    public static Palette createCustomPal(Color... colors) {
        int hash = 0;

        for (Color color : colors) {
            hash += color.hashCode();
        }

        // Check if already cached
        if (paletteCache.containsKey(hash)) {
            return paletteCache.get(hash);
        }

        // Create palette
        Palette palette = new Palette(colors);

        paletteCache.put(palette.hashCode(), palette);

        return palette;
    }

    public static Palette getBlackWhitePal() {
        return createCustomPal(Color.WHITE, Color.BLACK);
    }

    public static Palette getTransBlackPal() {
        return createCustomPal(
            new Color(0, 0, 0, 0),
            Color.BLACK
        );
    }

    /**
     * Converts a byte array to individual colors mapped to a {@link Palette}
     */
    public static ROMImage loadRawSprite(byte[] bits, Palette palette, int width, int height) {
        int[] colors = new int[bits.length * 8];

        for (int i = 0; i < bits.length; i++) {
            int offset = i * 8;
            byte data = bits[i];

            for (int j = 0; j < 8; j++) {
                colors[offset + j] = (data >> j) & 1;
            }
        }

        return new ROMImage(palette, colors, width, height);
    }

    public static void addCache(int pointer, Palette palette) {
        paletteCache.put(pointer, palette);
    }

    public static void clearCache() {
        paletteCache.clear();
    }

    public static Palette getCache(int pointer) {
        return paletteCache.get(pointer);
    }

    // Transformation utils
    public BufferedImage applyTransforms(BufferedImage image, boolean flipX, boolean flipY) {
        if (flipX) {
            image = horizontalFlip(image);
        }

        if (flipY) {
            image = verticalFlip(image);
        }

        return image;
    }

    public BufferedImage horizontalFlip(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        return rotationTransform(image, width, 0, 0, height);
    }

    public BufferedImage verticalFlip(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        return rotationTransform(image, 0, height, width, 0);
    }

    private BufferedImage rotationTransform(BufferedImage image, int sx1, int sy1, int sx2, int sy2) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, image.getType());

        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, sx1, sy1, sx2, sy2, null);
        graphics.dispose();

        return newImage;
    }
}
