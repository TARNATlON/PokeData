import me.hugmanrique.pokedata.loaders.ROMData;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
public class DataLoadTest {
    private static final String HEADER_NAME = "BPRE";

    @Test
    public void testLoadLocalData() {
        try {
            new ROMData(HEADER_NAME);
        } catch (IOException e) {
            throw new Error("Expected ROMData to load from local JAR ini file, but didn't", e);
        }
    }

    @Test
    public void testDataValidity() {
        ROMData data;

        try {
             data = new ROMData(HEADER_NAME);
        } catch (IOException e) {
            throw new Error(e);
        }

        assertEquals("ROM Name must be Pokemom Fire Red (English)", data.getRomName(), "Pokemon Fire Red (English)");
        assertEquals("ItemData pointer must be 0x3DB028", data.getItemData(), 0x3DB028);
    }
}
