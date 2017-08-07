import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.Map;
import me.hugmanrique.pokedata.maps.MapData;
import me.hugmanrique.pokedata.maps.MapHeader;
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

        checkEquals("Map data", header.getMapPtr(), 0x2DD4C0);
        checkEquals("Event data", header.getSpritesPtr(), 0x3B4E50);
        checkEquals("Map scripts", header.getScriptPtr(), 0x16545A);
        checkEquals("Connections", header.getConnectPtr(), 0x35276C);

        // Check data
        MapData mapData = map.getData();

        checkEquals("Border", mapData.getBorderTilePtr(), 0x2DD0F8);
        checkEquals("Tile structure", mapData.getMapTilesPtr(), 0x2DD100);
        checkEquals("Global tileset", mapData.getGlobalTilesetPtr(), 0x2D4A94);
        checkEquals("Local tileset", mapData.getLocalTilesetPtr(), 0x2D4AAC);

        // TODO Check scripts, connections...
    }
}
