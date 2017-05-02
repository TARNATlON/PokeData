package me.hugmanrique.pokedata;

import me.hugmanrique.pokedata.utils.ROM;

/**
 * Represents a class containing ROM data
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class Data {
    public Data() {}

    public Data(ROM rom) {
        read(rom);
    }

    public Data(ROM rom, int offset) {
        rom.seek(offset);
        read(rom);
    }

    public void read(ROM rom) {}
}
