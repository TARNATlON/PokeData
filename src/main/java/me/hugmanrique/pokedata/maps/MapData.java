package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
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

    public MapData(ROM rom) {
        width = rom.getPointer(true);
        height = rom.getPointer(true);

        borderTilePtr = rom.getPointerAsInt();

        mapTilesPtr = rom.getPointerAsInt();
        globalTilesetPtr = rom.getPointerAsInt();
        localTilesetPtr = rom.getPointerAsInt();

        System.out.println(globalTilesetPtr);
        System.out.println(localTilesetPtr);

        // Borders on Emerald are always 2x2
        if (rom.getGame().isGem()) {
            borderWidth = 2;
            borderHeight = 2;

            Tileset.LOCAL_BLOCKS = 0xA2;
        } else {
            int[] borderDims = BitConverter.toInts(rom.readBytes(2));

            borderWidth = borderDims[0];
            borderHeight = borderDims[1];
        }
    }

    public static MapData load(ROM rom, int offset) {
        rom.seek(offset);

        return new MapData(rom);
    }
}
