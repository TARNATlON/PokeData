package me.hugmanrique.pokedata.sprites.npcs;

import me.hugmanrique.pokedata.sprites.SpriteManager;
import me.hugmanrique.pokedata.utils.ROM;

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
}
