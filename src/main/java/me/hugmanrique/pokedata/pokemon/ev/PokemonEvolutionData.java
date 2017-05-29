package me.hugmanrique.pokedata.pokemon.ev;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_evolution_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class PokemonEvolutionData extends Data {
    private Evolution[] evolutions = new Evolution[5];

    public PokemonEvolutionData(ROM rom) {
        for (int i = 0; i < 5; i++) {
            evolutions[i] = new Evolution(rom);
        }
    }
}
