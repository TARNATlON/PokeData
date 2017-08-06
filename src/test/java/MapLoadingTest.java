import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.Map;
import me.hugmanrique.pokedata.maps.MapData;
import me.hugmanrique.pokedata.maps.MapHeader;
import me.hugmanrique.pokedata.maps.MapTileData;
import me.hugmanrique.pokedata.roms.ReadableROM;
import org.junit.Test;

public class MapLoadingTest extends ROMTest {
    /**
     * Load up the ROM, check Pallet Town data and exit
     */
    @Test
    public void testMapDataIntegrity() {
        ReadableROM rom = load();

        checkFireRed(rom);

        ROMData data = loadData(rom);

        // Pallet town is in bank 3, map 0
        Map map = Map.load(rom, data, 3, 0);

        // Check header
        MapHeader header = map.getHeader();

        checkPointer("Map data", header.getMapPtr(), 0x2DD4C0);
        checkPointer("Event data", header.getSpritesPtr(), 0x3B4E50);
        checkPointer("Map scripts", header.getScriptPtr(), 0x16545A);
        checkPointer("Connections", header.getConnectPtr(), 0x35276C);

        // Check data
        MapData mapData = map.getData();

        checkPointer("Border", mapData.getBorderTilePtr(), 0x2DD0F8);
        checkPointer("Tile structure", mapData.getMapTilesPtr(), 0x2DD100);
        checkPointer("Global tileset", mapData.getGlobalTilesetPtr(), 0x2D4A94);
        checkPointer("Local tileset", mapData.getLocalTilesetPtr(), 0x2D4AAC);

        // TODO Check scripts, connections...
    }

    private void checkPointer(String name, long pointer, long expected) {
        if (pointer != expected) {
            throwFail(name, pointer, expected);
        }
    }

    private void throwFail(String name, Object obj, Object comparedTo) {
        String message = String.format("%s don't/doesn't match (got %s, expected: %s)", name, obj, comparedTo);

        throw new Error(message);
    }
}
