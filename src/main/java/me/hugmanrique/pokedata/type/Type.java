package me.hugmanrique.pokedata.type;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Type_chart_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
public class Type extends Data {
    private byte attacking;
    private byte defending;
    private byte effectiveness;

    public Type(ROM rom) {
        attacking = rom.readByte();
        defending = rom.readByte();
        effectiveness = rom.readByte();
    }
}
