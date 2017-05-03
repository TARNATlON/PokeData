package me.hugmanrique.pokedata.sprites.npcs;

import lombok.Getter;
import me.hugmanrique.pokedata.sprites.Sprite;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * @author Hugmanrique
 * @since 02/05/2017
 */
@Getter
public class SpriteNPC extends Sprite {
    private byte b1;
    private int spriteSet;
    private byte b4;
    private byte b6;
    private byte b8;
    private byte b9;
    private byte behavior1;
    private byte b10;
    private byte behavior2;
    private byte isTrainer;
    private byte b14;
    private byte trainerLOS;
    private byte b16;
    private long scriptPtr;
    private int flag;
    private byte b23;
    private byte b24;

    public SpriteNPC(ROM rom) {
        b1 = rom.readByte();
        spriteSet = rom.readWord();
        b4 = rom.readByte();
        x = rom.readByte();
        b6 = rom.readByte();
        y = rom.readByte();
        b8 = rom.readByte();
        b9 = rom.readByte();
        behavior1 = rom.readByte();
        b10 = rom.readByte();
        behavior2 = rom.readByte();
        isTrainer = rom.readByte();
        b14 = rom.readByte();
        trainerLOS = rom.readByte();
        b16 = rom.readByte();
        scriptPtr = rom.getPointer();
        flag = rom.readWord();
        b23 = rom.readByte();
        b24 = rom.readByte();
    }

    @Override
    public int getSize() {
        return 24;
    }
}
