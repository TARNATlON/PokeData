package me.hugmanrique.pokedata.sprites.signs;

import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.sprites.SpriteManager;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
public class SpritesSignManager extends SpriteManager<SpriteSign> {
    public SpritesSignManager(ROM rom, int count) {
        for (int i = 0; i < count; i++) {
            sprites.add(new SpriteSign(rom));
        }
    }

    public SpritesSignManager(ROM rom, int offset, int count) {
        super(rom, offset, count);
    }
}
