package me.hugmanrique.pokedata.pokedex.ev;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_evolution_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
@ToString
public class Evolution extends Data {
    private EvolutionType type;
    private int parameter;
    private int target;

    public Evolution(int type, int parameter, int target) {
        this.type = EvolutionType.byId(type);
        this.parameter = parameter;
        this.target = target;
    }

    static Evolution createIfPresent(ROM rom) {
        int type = rom.readWord();
        int parameter = rom.readWord();
        int target = rom.readWord();

        // Skip padding due to alignment
        rom.addInternalOffset(2);

        if (!BitConverter.zeroedOut(type, parameter, target)) {
            return new Evolution(type, parameter, target);
        } else {
            return null;
        }
    }
}
