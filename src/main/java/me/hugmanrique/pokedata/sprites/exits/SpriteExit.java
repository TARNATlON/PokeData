package me.hugmanrique.pokedata.sprites.exits;

import lombok.Getter;
import me.hugmanrique.pokedata.sprites.Sprite;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
@Getter
public class SpriteExit extends Sprite {
    private byte b2;
    private byte b4;
    private byte b5;
    private byte b6;
    private byte map;
    private byte bank;

    public SpriteExit(ROM rom) {
        x = rom.readByte();
        b2 = rom.readByte();
        y = rom.readByte();
        b4 = rom.readByte();
        b5 = rom.readByte();
        b6 = rom.readByte();
        map = rom.readByte();
        bank = rom.readByte();
    }

    @Override
    public int getSize() {
        return 8;
    }
}
