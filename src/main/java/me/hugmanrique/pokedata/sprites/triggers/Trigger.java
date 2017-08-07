package me.hugmanrique.pokedata.sprites.triggers;

import lombok.Getter;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.sprites.Sprite;

/**
 * Although triggers aren't sprites, they have the same base structure
 * @author Hugmanrique
 * @since 02/05/2017
 */
@Getter
public class Trigger extends Sprite {
    private byte b2;
    private byte b4;
    private int h3;
    private int flagCheck;
    private int flagValue;
    private int h6;
    private long scriptPtr;

    public Trigger(ROM rom) {
        x = rom.readByte();
        b2 = rom.readByte();
        y = rom.readByte();
        b4 = rom.readByte();
        h3 = rom.readWord();
        flagCheck = rom.readWord();
        flagValue = rom.readWord();
        h6 = rom.readWord();
        scriptPtr = rom.getPointer();
    }

    @Override
    public int getSize() {
        return 16;
    }
}
