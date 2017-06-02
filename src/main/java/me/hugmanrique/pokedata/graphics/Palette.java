package me.hugmanrique.pokedata.graphics;

import lombok.Getter;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

import java.awt.*;

/**
 * Thanks to shinyquagsire23 for implementing this on GBAUtils
 * https://github.com/shinyquagsire23/GBAUtils
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
public class Palette {
    private static final Color BLACK = Color.black;

    private Color[] colors;

    private byte[] reds;
    private byte[] greens;
    private byte[] blues;

    public Palette(ImageType type, int[] data) {
        int size = type.getSize();

        colors = new Color[size];
        reds = new byte[size];
        greens = new byte[size];
        blues = new byte[size];

        for (int i = 0; i < data.length; i += 2) {
            int color = data[i] + (data[i + 1] << 8);

            int red = (color & 0x1F) << 3;
            int green = (color & 0x3E0) >> 2;
            int blue = (color & 0x7C00) >> 7;

            int index = i / 2;

            reds[index] = (byte) red;
            greens[index] = (byte) green;
            blues[index] = (byte) blue;
            colors[index] = new Color(red, green, blue);
        }
    }

    public Palette(ImageType type, byte[] data) {
        this(type, BitConverter.toInts(data));
    }

    public Palette(ImageType type, ROM rom, int offset) {
        this(type, rom.readBytes(offset, type.getROMSize()));
    }

    public Color getColor(int index) {
        if (!checkBounds(index)) {
            return BLACK;
        }

        return colors[index];
    }

    public int getRgbInt(int index) {
        if (!checkBounds(index)) {
            return BLACK.getRGB();
        }

        Color color = colors[index];

        return color.getRed() +
                (color.getGreen() << 8) +
                (color.getBlue() << 16);
    }

    public int getSize() {
        return colors.length;
    }

    public ImageType getType() {
        if (colors.length == 16) {
            return ImageType.C16;
        } else {
            return ImageType.C256;
        }
    }

    private boolean checkBounds(int index) {
        if (index > colors.length) {
            warnOutside();
            return false;
        }

        return true;
    }

    private void warnOutside() {
        System.out.println("Attempted to grab color outside of palette bounds");
    }
}
