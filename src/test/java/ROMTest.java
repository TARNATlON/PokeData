import me.hugmanrique.pokedata.roms.ROMLoader;
import me.hugmanrique.pokedata.roms.ReadableROM;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class ROMTest {
    public static final File JAR_FOLDER = getJarFolder();

    public ReadableROM load() {
        File location = new File(JAR_FOLDER.getParentFile().getParentFile(), "tests.gba");

        ReadableROM rom = new ReadableROM();

        try {
            ROMLoader.load(rom, location);
        } catch (IOException e) {
            throw new Error("Expected tests.gba ROM file to load and parse correctly");
        }


        return rom;
    }

    private static File getJarFolder() {
        ProtectionDomain domain = ROMTest.class.getProtectionDomain();
        CodeSource source = domain.getCodeSource();

        return new File(source.getLocation().getPath());
    }

}
