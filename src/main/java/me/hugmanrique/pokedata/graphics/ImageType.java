package me.hugmanrique.pokedata.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
@AllArgsConstructor
public enum ImageType implements ImageRenderer {
    C16(16, 2, false) {
        @Override
        public void setPixel(BufferedImage image, Graphics graphics, int x, int y, Palette palette, int pal, boolean transparency) {
            Color color = palette.getColor(pal);
            int[] pixel = new int[] {
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                transparency && pal == 0 ? 0 : 255
            };

            image.getRaster().setPixel(x, y, pixel);
        }

        @Override
        public int getPal(int i, int original) {
            if ((i & 1) == 0) {
                original &= 0xF;
            } else {
                original = (original & 0xF0) >> 4;
            }

            return original;
        }
    },
    C256(256, 1, true) {
        @Override
        public void setPixel(BufferedImage image, Graphics graphics, int x, int y, Palette palette, int pal, boolean transparency) {
            graphics.setColor(palette.getColor(pal));
            graphics.drawRect(x, y, 1, 1);
        }

        @Override
        public int getPal(int i, int original) {
            return original;
        }
    };

    private int size;
    private int resize;
    private boolean graphics;

    public int getROMSize() {
        return size * 2;
    }
}
