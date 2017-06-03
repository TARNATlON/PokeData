package me.hugmanrique.pokedata.trainers;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * Aka Party member, Trainer's Pokémon representation
 * @author Hugmanrique
 * @since 02/06/2017
 */
@Getter
@ToString
public class TrainerPokemon extends Data {
    private int evs;
    private int species;
    private int level;
    private int heldItem;
    private int[] attacks;

    public TrainerPokemon(ROM rom, Trainer trainer) {
        evs = rom.readWord();
        level = rom.readWord();
        species = rom.readWord();

        if (trainer.isHeldsItems()) {
            heldItem = rom.readWord();
        }

        if (trainer.isCustomAttacks()) {
            attacks = new int[4];

            for (int j = 0; j < 4; j++) {
                attacks[j] = rom.readWord();
            }
        }

        if (!trainer.isHeldsItems()) {
            rom.addInternalOffset(2);
        }
    }
}
