package me.hugmanrique.pokedata.moves;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Move_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class Move extends Data {
    private byte effect;
    private byte basePower;
    private byte type;
    private byte accuracy;
    private byte pp;
    private byte effectAccuracy;
    private byte affects;
    private byte priority;
    private byte flags;

    @Override
    public void read(ROM rom) {
        effect = rom.readByte();
        basePower = rom.readByte();
        type = rom.readByte();
        accuracy = rom.readByte();
        pp = rom.readByte();
        effectAccuracy = rom.readByte();
        affects = rom.readByte();
        priority = rom.readByte();
        flags = rom.readByte();
        rom.addInternalOffset(3);
    }
}
