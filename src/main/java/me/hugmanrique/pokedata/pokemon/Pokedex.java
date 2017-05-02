package me.hugmanrique.pokedata.pokemon;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
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

    public Pokedex(ROM rom) {
        name = rom.readPokeText();
        height = rom.readWord();
        weight = rom.readWord();
        desc1Ptr = rom.getPointer();
        desc2Ptr = rom.getPointer();
        rom.addInternalOffset(2);

        scale = rom.readWord();
        offset = rom.readWord();
        trainerScale = rom.readWord();
        trainerOffset = rom.readWord();
        rom.addInternalOffset(2);
    }
}
