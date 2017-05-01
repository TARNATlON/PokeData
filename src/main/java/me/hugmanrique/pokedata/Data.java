package me.hugmanrique.pokedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * Represents a class containing ROM data
 * @author Hugmanrique
 * @since 30/04/2017
 */
@AllArgsConstructor
@Getter
public class Data {
    public Data(ROM rom) {
        read(rom);
    }

    public Data(ROM rom, int offset) {
        rom.seek(offset);
        read(rom);
    }

    public void read(ROM rom) {}
}
