package me.hugmanrique.pokedata.sprites.triggers;

import me.hugmanrique.pokedata.sprites.SpriteManager;
import me.hugmanrique.pokedata.roms.ROM;

import java.util.List;

/**
 * Explanation on the {@link SpriteManager} inheritance
 * is available in the {@link Trigger}'s javadocs
 * @author Hugmanrique
 * @since 02/05/2017
 */
public class TriggerManager extends SpriteManager<Trigger> {
    public TriggerManager(ROM rom, int count) {
        for (int i = 0; i < count; i++) {
            sprites.add(new Trigger(rom));
        }
    }

    public TriggerManager(ROM rom, int offset, int count) {
        super(rom, offset, count);
    }

    // Getter mapping, triggers aren't sprites
    public List<Trigger> getTriggers() {
        return sprites;
    }
}
