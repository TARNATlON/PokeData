package me.hugmanrique.pokedata.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
@AllArgsConstructor
public class ROMImage {
    private Palette palette;
    private int[] data;
    private Point size;

    public ROMImage(Palette palette, int[] data, int width, int height) {
        this.palette = palette;
        this.data = data;
        this.size = new Point(width, height);
    }

    public static ROMImage fromImage(Image img, Palette palette) {
        BufferedImage image = (BufferedImage) img;
        int dims = image.getWidth() * image.getHeight();

        int x = -1;
        int y = 0;
        int blockX = 0;
        int blockY = 0;

        int[] data = new int[dims / 2];

        for (int i = 0; i < dims; i++) {
            x++;

            if (x >= 8) {
                x = 0;
                y++;
            }

            if (y >= 8) {
                y = 0;
                blockX++;
            }

            if (blockX > (image.getWidth() / 8) - 1) {
                blockX = 0;
                blockY++;
            }

            int rgb = image.getRGB(blockX * 8 + x, blockY * 8 + y);
            Color color = new Color(rgb, true);
            int pal = 0;

            for (int j = 0; j < 16; j++) {
                Color col = palette.getColor(j);

                if (col.equals(color)) {
                    pal = j;
                }
            }

            int write = data[i / 2];

            if ((i & 1) == 0) {
                write |= (pal & 0xF);
            } else {
                write |= ((pal << 4) & 0xF0);
            }

            data[i / 2] = write;
        }

        Point size = new Point(image.getWidth(null), image.getHeight(null));

        return new ROMImage(palette, data, size);
    }

    public BufferedImage toBufferedImage() {
        return toBufferedImage(true);
    }

    public BufferedImage toBufferedImage(boolean transparency) {
        BufferedImage image = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);
        ImageType type = palette.getType();
        Graphics graphics = null;

        if (type.isGraphics()) {
            graphics = image.createGraphics();
        }

        int x = -1;
        int y = 0;
        int blockX = 0;
        int blockY = 0;

        for (int i = 0; i < data.length * type.getResize(); i++) {
            x++;

            if (x >= 8) {
                x = 0;
                y++;
            }

            if (y >= 8) {
                y = 0;
                blockX++;
            }

            if (blockX > (image.getWidth() / 8) - 1) {
                blockX = 0;
                blockY++;
            }

            int pal = data[i / type.getResize()];
            pal = type.getPal(i, pal);

            try {
                type.setPixel(image, graphics, blockX * 8 + x, blockY * 8 + y, palette, pal, transparency);
            } catch (Exception ignored) {}
        }

        return image;
    }

    public BufferedImage getImage(Palette palette) {
        return getImage(palette, true);
    }

    public BufferedImage getImage(Palette palette, boolean transparency) {
        FastBitmap bitmap = new FastBitmap(size.x, size.y);
        //BufferedImage image = new BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB);

        int x = -1;
        int y = 0;
        int blockX = 0;
        int blockY = 0;

        for (int i = 0; i < data.length * 2; i++) {
            x++;

            if (x >= 8) {
                x = 0;
                y++;
            }

            if (y >= 8) {
                y = 0;
                blockX++;
            }

            if (blockX > bitmap.getWidth() / 8 - 1) {
                blockX = 0;
                blockY++;
            }

            int pal = data[i / 2];

            if ((i & 1) == 0) {
                pal &= 0xF;
            } else {
                pal = (pal & 0xF0) >> 4;
            }

            try {
                int red = palette.getReds()[pal];
                int green = palette.getGreens()[pal];
                int blue = palette.getBlues()[pal];
                int alpha = transparency && pal == 0 ? 0 : 255;

                int xPos = x + blockX * 8;
                int yPos = y + blockY * 8;

                bitmap.setRGBA(xPos, yPos, red, green, blue, alpha);
            } catch (Exception ignored) {}
        }

        return bitmap.getImage();
    }
}
