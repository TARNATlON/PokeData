package me.hugmanrique.pokedata.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public interface ImageRenderer {
    void setPixel(BufferedImage image, Graphics graphics, int x, int y, Palette palette, int pal, boolean transparency);

    int getPal(int i, int original);
}
