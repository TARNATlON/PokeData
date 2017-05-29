package me.hugmanrique.pokedata.sprites.exits;

import me.hugmanrique.pokedata.sprites.SpriteManager;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
public class SpritesExitManager extends SpriteManager<SpriteExit> {
    public SpritesExitManager(ROM rom, int count) {
        for (int i = 0; i < count; i++) {
            sprites.add(new SpriteExit(rom));
        }
    }

    public SpritesExitManager(ROM rom, int offset, int count) {
        super(rom, offset, count);
    }
}
