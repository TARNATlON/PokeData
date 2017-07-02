package me.hugmanrique.pokedata.tiles;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * http://datacrystal.romhacking.net/wiki/Pok%C3%A9mon_3rd_Generation#Maps
 * @author Hugmanrique
 * @since 02/07/2017
 */
@Getter
public class TilesetHeader extends Data {
    private boolean compressed;
    private boolean primary;
    private long tilesetImgPtr;
    private long palettesPtr;
    private long blocksPtr;
    private long animationPtr;
    private long behaviorPtr;

    public TilesetHeader(ROM rom) {
        compressed = rom.readByte() == 1;
        primary = rom.readByte() == 0;

        // Skip two unknown bytes
        rom.addInternalOffset(2);

        tilesetImgPtr = rom.getPointer();
        palettesPtr = rom.getPointer();
        blocksPtr = rom.getPointer();

        if (rom.getGame().isElements()) {
            animationPtr = rom.getPointer();
            behaviorPtr = rom.getPointer();
        } else {
            behaviorPtr = rom.getPointer();
            animationPtr = rom.getPointer();
        }
    }
}
