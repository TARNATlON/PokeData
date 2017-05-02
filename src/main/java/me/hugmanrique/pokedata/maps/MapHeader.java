package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class MapHeader extends Data {
    private long mapPtr;
    private long spritesPtr;
    private long scriptPtr;
    private long connectPtr;
    private int song;
    private int map;
    private byte labelId;
    private byte flash;
    private byte weather;
    private byte type;
    private byte labelToggle;

    public MapHeader(ROM rom) {
        mapPtr = rom.getPointer();
        spritesPtr = rom.getPointer();
        scriptPtr = rom.getPointer();
        connectPtr = rom.getPointer();
        song = rom.readWord();
        map = rom.readWord();
        labelId = rom.readByte();
        flash = rom.readByte();
        weather = rom.readByte();
        type = rom.readByte();
        rom.addInternalOffset(2);
        labelToggle = rom.readByte();
        rom.addInternalOffset(1);
    }
}
