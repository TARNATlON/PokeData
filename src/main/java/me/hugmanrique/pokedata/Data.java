package me.hugmanrique.pokedata;

import me.hugmanrique.pokedata.utils.ROM;

/**
 * Represents a class containing ROM data
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class Data {
    protected Data() {}

    public Data(ROM rom) {}

    public Data(ROM rom, int offset) {
        this(rom.seekAndGet(offset));
    }
}
