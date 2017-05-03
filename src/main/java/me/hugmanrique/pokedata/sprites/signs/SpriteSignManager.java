package me.hugmanrique.pokedata.sprites.signs;

import me.hugmanrique.pokedata.sprites.SpriteManager;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
public class SpriteSignManager extends SpriteManager<SpriteSign> {
    public SpriteSignManager(ROM rom, int count) {
        for (int i = 0; i < count; i++) {
            sprites.add(new SpriteSign(rom));
        }
    }

    public SpriteSignManager(ROM rom, int offset, int count) {
        super(rom, offset, count);
    }
}
