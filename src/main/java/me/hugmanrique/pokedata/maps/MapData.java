package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.tiles.Tileset;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
@Getter
@ToString
public class MapData extends Data {
    private long width;
    private long height;

    private int borderTilePtr;
    private int mapTilesPtr;
    private int globalTilesetPtr;
    private int localTilesetPtr;

    private int borderWidth;
    private int borderHeight;

    private int secondarySize;

    public MapData(ROM rom, ROMData data) {
        width = rom.getPointer(true);
        height = rom.getPointer(true);

        borderTilePtr = rom.getPointerAsInt();

        mapTilesPtr = rom.getPointerAsInt();
        globalTilesetPtr = rom.getPointerAsInt();
        localTilesetPtr = rom.getPointerAsInt();

        borderWidth = rom.readByte();
        borderHeight = rom.readByte();

        secondarySize = borderWidth + 0xA0;

        // Borders on Emerald are always 2x2
        if (rom.getGame().isGem()) {
            borderWidth = 2;
            borderHeight = 2;

            data.setLocalBlocks(secondarySize);
        } else {
            secondarySize = data.getLocalBlocks();
        }
    }

    public static MapData load(ROM rom, ROMData data, int offset) {
        rom.seek(offset);

        return new MapData(rom, data);
    }
}
