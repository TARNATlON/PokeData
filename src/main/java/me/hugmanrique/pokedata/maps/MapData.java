package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
@Getter
public class MapData extends Data {
    private long width;
    private long height;

    private int borderTilePtr;
    private int mapTilesPtr;
    private int globalTilesetPtr;
    private int localTilesetPtr;

    private int borderWidth = 2;
    private int borderHeight = 2;
    private int secondarySize;

    public MapData(ROM rom) {
        super(rom);

        width = rom.getPointer(true);
        height = rom.getPointer(true);

        borderTilePtr = rom.getPointerAsInt();
        mapTilesPtr = rom.getPointerAsInt();

        globalTilesetPtr = rom.getPointerAsInt();
        localTilesetPtr = rom.getPointerAsInt();

        // Borders on Emerald are always 2x2
        if (rom.getGame().isElements()) {
            int[] borderDims = BitConverter.toInts(rom.readBytes(2));

            borderWidth = borderDims[0];
            borderHeight = borderDims[1];
        }

        secondarySize = borderWidth + 0xA0;
    }

    public MapData(ROM rom, int offset) {
        super(rom, offset);
    }
}
