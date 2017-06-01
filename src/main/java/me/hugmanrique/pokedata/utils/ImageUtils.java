package me.hugmanrique.pokedata.utils;

import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 01/06/2017
 */
public class ImageUtils {
    public static Palette getPalette(ROM rom, long pointer, ImageType type) {
        int[] data = Lz77.decompress(rom, (int) pointer);

        return new Palette(type, data);
    }

    public static Palette getPalette(ROM rom, long pointer) {
        return getPalette(rom, pointer, ImageType.C16);
    }

    public static ROMImage getImage(ROM rom, long pointer, Palette palette, int width, int height) {
        int[] data = Lz77.decompress(rom, (int) pointer);

        return new ROMImage(palette, data, width, height);
    }
}
