import me.hugmanrique.pokedata.utils.BitConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
public class BitRangeTest {
    @Test
    public void test16BitRanges() {
        assertEquals(0b10, BitConverter.getBitRange(0b1000000000000000, 0, 2));
        assertEquals(0b10, BitConverter.getBitRange(0b0010000000000000, 2, 4));
    }

    @Test
    public void testArbitraryLength() {
        // TODO Fix
        //assertEquals(0b1, BitConverter.getBitRange(0b00100, 3, 4));
    }
}
