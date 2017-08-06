import me.hugmanrique.pokedata.roms.Game;
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

    }
}
