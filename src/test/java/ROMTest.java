import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.Game;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.roms.ROMLoader;
import me.hugmanrique.pokedata.roms.ReadableROM;
import org.junit.Assume;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class ROMTest {
    public static final File JAR_FOLDER = getJarFolder();
    private static boolean CI = Boolean.parseBoolean(System.getenv("CI")) || Boolean.parseBoolean(System.getenv("TRAVIS"));

    protected ReadableROM load() {
        if (CI) {
            System.out.println("Running on a CI, skipping...");
            Assume.assumeTrue(false);
            return null;
        }

        File location = new File(JAR_FOLDER.getParentFile().getParentFile(), "tests.gba");

        ReadableROM rom = new ReadableROM();

        try {
            ROMLoader.load(rom, location);
        } catch (IOException e) {
            throw new Error("Expected tests.gba ROM file to load and parse correctly", e);
        }


        return rom;
    }

    protected void checkFireRed(ROM rom) {
        if (rom.getGame() != Game.FIRE_RED) {
            throw new Error("Cannot perform this test because ROM isn't a FireRed game");
        }
    }

    protected ROMData loadData(ROM rom) {
        try {
            return new ROMData(rom);
        } catch (IOException e) {
            throw new Error("Can't load data for current ROM", e);
        }
    }

    protected void checkEquals(String name, long pointer, long expected) {
        System.out.println("Checking " + name + " (got: " + pointer + ", expected: " + expected + ")");

        if (pointer != expected) {
            throwFail(name, pointer, expected);
        }
    }

    private void throwFail(String name, Object obj, Object comparedTo) {
        String message = String.format("%s don't/doesn't match (got %s, expected: %s)", name, obj, comparedTo);

        throw new Error(message);
    }

    private static File getJarFolder() {
        ProtectionDomain domain = ROMTest.class.getProtectionDomain();
        CodeSource source = domain.getCodeSource();

        return new File(source.getLocation().getPath());
    }

}
