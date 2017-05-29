package me.hugmanrique.pokedata.pokedex;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.Game;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
@ToString
public class Pokedex extends Data {
    private String name;
    private int height;
    private int weight;
    private long desc1Ptr;
    private long desc2Ptr;
    private int scale;
    private int offset;
    private int trainerScale;
    private int trainerOffset;

    public Pokedex(ROM rom, boolean emerald) {
        name = rom.readPokeText(12);
        height = rom.readWord();
        weight = rom.readWord();
        desc1Ptr = rom.getPointer();

        if (!emerald) {
            desc2Ptr = rom.getPointer();
        }

        rom.addInternalOffset(2);

        scale = rom.readWord();
        offset = rom.readWord();
        trainerScale = rom.readWord();
        trainerOffset = rom.readWord();
        rom.addInternalOffset(2);
    }

    public String getDesc1(ROM rom) {
        return rom.readPokeText((int) desc1Ptr, -1);
    }

    public String getDesc2(ROM rom) {
        // Might not be set
        if (desc2Ptr == 0L) {
            return null;
        }

        return rom.readPokeText((int) desc2Ptr, -1);
    }

    public static Pokedex load(ROM rom, ROMData data, int pokemon) {
        boolean emerald = rom.getGame() == Game.EMERALD;

        int size = emerald ? 32 : 36;
        int offset = data.getPokedexData() + (pokemon * size);

        rom.setInternalOffset(offset);

        return new Pokedex(rom, emerald);
    }
}
