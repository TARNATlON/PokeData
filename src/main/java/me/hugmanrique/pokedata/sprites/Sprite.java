package me.hugmanrique.pokedata.sprites;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * Base class for diferent types of sprites
 * @author Hugmanrique
 * @since 02/05/2017
 */
@Getter
public abstract class Sprite extends Data {
    protected byte x;
    protected byte y;

    protected Sprite() {}

    public Sprite(ROM rom) {}

    public Sprite(ROM rom, int offset) {
        this(rom.seekAndGet(offset));
    }

    // Should be static, create different type enum with constants?
    public abstract int getSize();
}
