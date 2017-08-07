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

        this.pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }

    public void setRGBA(int x, int y, int red, int green, int blue, int alpha) {
        pixels[x * width + y] = alpha << 24 | red << 16 | green << 8 | blue;
    }
}
