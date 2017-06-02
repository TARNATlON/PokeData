package me.hugmanrique.pokedata.trainers;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/06/2017
 */
public class TrainerPokemon extends Data {
    private short evs;
    private short species;
    private short level;
    private short heldItem;
    private short[] attacks;

    private int index;

    public TrainerPokemon(ROM rom, int index) {
        this.index = index;


    }

    public static TrainerPokemon load(ROM rom, ROMData data, int index) {

    }
}
