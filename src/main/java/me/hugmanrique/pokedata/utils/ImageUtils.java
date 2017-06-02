package me.hugmanrique.pokedata.utils;

import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;

import java.awt.*;
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
    private static Palette blackWhitePal = new Palette(Color.black, Color.white);

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

    public static Palette getBlackWhitePal() {
        return blackWhitePal;
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
}
