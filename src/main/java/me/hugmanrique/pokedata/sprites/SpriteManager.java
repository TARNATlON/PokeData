package me.hugmanrique.pokedata.sprites;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class extended by Sprite managers
 * @author Hugmanrique
 * @since 02/05/2017
 */
public abstract class SpriteManager<T extends Sprite> extends Data {
    protected List<T> sprites = new ArrayList<T>();

    protected SpriteManager() {}

    /**
     * Constructor to be inherited by the manager class
     */
    protected SpriteManager(ROM rom, int count) {}

    public SpriteManager(ROM rom, int offset, int count) {
        this(rom.seekAndGet(offset), count);
    }

    public int getSpriteIndexAt(int x, int y) {
        T sprite = getSpriteAt(x, y);

        if (sprite != null) {
            return sprites.indexOf(sprite);
        } else {
            return -1;
        }
    }

    public T getSpriteAt(int x, int y) {
        for (T sprite : sprites) {
            if (sprite.getX() == x && sprite.getY() == y) {
                return sprite;
            }
        }

        return null;
    }

    public int getSize() {
        if (sprites.isEmpty()) {
            return 0;
        }

        // Sizes of different Sprite instances should be equal
        return sprites.size() * sprites.get(0).getSize();
    }
}
