package me.hugmanrique.pokedata.pokedex.ev;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_evolution_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class EvolutionData extends Data {
    private Evolution[] evolutions = new Evolution[5];

    public EvolutionData(ROM rom) {
        for (int i = 0; i < 5; i++) {
            evolutions[i] = Evolution.createIfPresent(rom);
        }
    }

    public static EvolutionData load(ROM rom, ROMData data, int id) {
        int offset = data.getPokemonEvolutions() + (id * 40);
        rom.setInternalOffset(offset);

        return new EvolutionData(rom);
    }
}
