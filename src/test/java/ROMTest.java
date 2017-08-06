import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.Game;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.roms.ROMLoader;
import me.hugmanrique.pokedata.roms.ReadableROM;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class ROMTest {
    public static final File JAR_FOLDER = getJarFolder();

    protected ReadableROM load() {
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

    private static File getJarFolder() {
        ProtectionDomain domain = ROMTest.class.getProtectionDomain();
        CodeSource source = domain.getCodeSource();

        return new File(source.getLocation().getPath());
    }

}
