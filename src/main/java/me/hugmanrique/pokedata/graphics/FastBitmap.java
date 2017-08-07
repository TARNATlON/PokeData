package me.hugmanrique.pokedata.graphics;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

@Getter
public class FastBitmap {
    private BufferedImage image;
    private int[] pixels;
    private int width;

    public FastBitmap(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.width = width;

        this.pixels = ((DataBufferInt) image.getAlphaRaster().getDataBuffer()).getData();
    }

    public void setRGBA(int x, int y, byte red, byte green, byte blue, byte alpha) {
        pixels[y * width + x] = (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
    }
}
