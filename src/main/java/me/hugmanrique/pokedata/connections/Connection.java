package me.hugmanrique.pokedata.connections;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * Missing bulbapedia article
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class Connection extends Data {
    private long type;
    private long offset;
    private byte bank;
    private byte map;

    public Connection(ROM rom) {
        type = rom.getPointer(true);
        offset = rom.getSignedLong(true);
        bank = rom.readByte();
        map = rom.readByte();
        rom.addInternalOffset(2);
    }
}
