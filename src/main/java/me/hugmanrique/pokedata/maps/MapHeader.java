package me.hugmanrique.pokedata.maps;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
@ToString
public class MapHeader extends Data {
    private long mapPtr;
    private long spritesPtr;
    private long scriptPtr;
    private long connectPtr;
    private int music;
    private int map;
    private int labelId;
    private byte flash;
    private byte weather;
    private byte type;
    private byte labelToggle;
    private byte battleFieldModel;

    @Setter(AccessLevel.PACKAGE)
    private String name = "";

    public MapHeader(ROM rom) {
        mapPtr = rom.getPointer();
        spritesPtr = rom.getPointer();
        scriptPtr = rom.getPointer();
        connectPtr = rom.getPointer();
        music = rom.readWord();
        map = rom.readWord();

        // Label is a word, ROMHacking.net is wrong
        labelId = rom.readWord(rom.getInternalOffset());
        rom.addInternalOffset(1);

        flash = rom.readByte();
        weather = rom.readByte();
        type = rom.readByte();
        rom.addInternalOffset(2);
        labelToggle = rom.readByte();
        battleFieldModel = rom.readByte();
        rom.addInternalOffset(1);
    }
}
