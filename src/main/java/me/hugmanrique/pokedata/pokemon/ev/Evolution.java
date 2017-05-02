package me.hugmanrique.pokedata.pokemon.ev;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_evolution_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class Evolution extends Data {
    private int evolutionType;
    private int parameter;
    private int target;

    public Evolution(ROM rom) {
        evolutionType = rom.readWord();
        parameter = rom.readWord();
        target = rom.readWord();

        // Skip padding due to alignment
        rom.addInternalOffset(2);
    }

    /**
     * Returns if this entry is an actual evolution
     */
    public boolean isZeroedOut() {
        return BitConverter.zeroedOut(evolutionType, parameter, target);
    }
}
