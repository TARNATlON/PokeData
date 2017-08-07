import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.Map;
import me.hugmanrique.pokedata.maps.MapData;
import me.hugmanrique.pokedata.maps.MapTileData;
import me.hugmanrique.pokedata.roms.ReadableROM;
import me.hugmanrique.pokedata.tiles.Tileset;
import me.hugmanrique.pokedata.tiles.TilesetCache;
import me.hugmanrique.pokedata.tiles.TilesetHeader;
import org.junit.Test;

public class MapTileLoadingTest extends ROMTest {
    @Test
    public void testMapTileLoad() {
        ReadableROM rom = load();

        checkFireRed(rom);

        ROMData data = loadData(rom);

        // Pallet town is in bank 3, map 0
        Map map = Map.load(rom, data, 3, 0);

        // Check header
        MapData mapData = map.getData();

        checkEquals("Width", mapData.getWidth(), 24);
        checkEquals("Height", mapData.getHeight(), 20);
        checkEquals("Border size", mapData.getBorderHeight(), 2);

        Tileset global = TilesetCache.get(rom, mapData.getGlobalTilesetPtr());
        Tileset local = TilesetCache.get(rom, mapData.getLocalTilesetPtr());

        testTileset("Global", global);
    }

    // TODO Get local offsets and test
    private void testTileset(String name, Tileset tileset) {
        TilesetHeader header = tileset.getHeader();

        checkEquals(name + " Compressed", header.isCompressed() ? 1 : 0, 1);
        checkEquals(name + " Primary", header.isPrimary() ? 1 : 0, 1);

        checkEquals(name + " Tileset image pointer", header.getTilesetImgPtr(), 0xEA1D68);
        checkEquals(name + " Palettes pointer", header.getPalettesPtr(), 0xEA1B68);
        checkEquals(name + " Blocks pointer", header.getBlocksPtr(), 0x29F6C8);
        checkEquals(name + " Animation routine pointer", header.getAnimationPtr(), 0x070155);
        checkEquals(name + " Behaviour pointer", header.getBehaviorPtr(), 0x2A1EC8);
    }
}
