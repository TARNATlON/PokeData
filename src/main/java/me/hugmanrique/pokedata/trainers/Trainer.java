package me.hugmanrique.pokedata.trainers;

import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.graphics.Imageable;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/06/2017
 */
public class Trainer extends Data implements Imageable {
    private byte clazz;
    private boolean gender;
    private byte music;
    private byte sprite;
    private String name;
    private boolean heldsItems;
    private short[] items;
    private boolean doubleBattle;
    private int ai;

    private boolean customAttacks;
    private int partyOffset;
    // Pokemon array
    //private

    private int index;

    public Trainer(ROM rom, int index) {
        this.index = index;
    }

    public static Trainer load(ROM rom, ROMData data, int index) {

    }







    @Override
    public ROMImage getImage(ROM rom, ROMData data) {
        return null;
    }
}
