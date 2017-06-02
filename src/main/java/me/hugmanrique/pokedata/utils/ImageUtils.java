package me.hugmanrique.pokedata.utils;

import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;

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
    private static Map<Integer, Palette> paletteCache = new HashMap<>();

    public static Palette getPalette(ROM rom, int pointer, ImageType type) {
        if (paletteCache.containsKey(pointer)) {
            return paletteCache.get(pointer);
        }

        int[] data = Lz77.decompress(rom, pointer);

        Palette palette = new Palette(type, data);
        paletteCache.put(pointer, palette);

        return palette;
    }

    public static Palette getPalette(ROM rom, int pointer) {
        return getPalette(rom, pointer, ImageType.C16);
    }

    public static ROMImage getImage(ROM rom, int pointer, Palette palette, int width, int height) {
        int[] data = Lz77.decompress(rom, pointer);

        return new ROMImage(palette, data, width, height);
    }
}
