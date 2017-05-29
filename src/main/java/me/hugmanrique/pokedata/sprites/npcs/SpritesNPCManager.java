package me.hugmanrique.pokedata.sprites.npcs;

import me.hugmanrique.pokedata.sprites.SpriteManager;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
public class SpritesNPCManager extends SpriteManager<SpriteNPC> {
    public SpritesNPCManager(ROM rom, int count) {
        for (int i = 0; i < count; i++) {
            sprites.add(new SpriteNPC(rom));
        }
    }

    public SpritesNPCManager(ROM rom, int offset, int count) {
        // TODO Make sure this in super calls constructor above
        super(rom, offset, count);
    }
}
