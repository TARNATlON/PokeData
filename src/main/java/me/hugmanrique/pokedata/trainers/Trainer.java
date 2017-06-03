package me.hugmanrique.pokedata.trainers;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.graphics.Imageable;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 02/06/2017
 */
@Getter
@ToString
public class Trainer extends Data implements Imageable {
    private static final int ITEM_LIMIT = 4;

    private byte trainerClass;
    // TODO Convert to boolean?
    private byte gender;
    private byte music;
    private byte sprite;
    private String name;
    private boolean heldsItems;
    private int[] items;
    private boolean doubleBattle;
    private long ai;

    private boolean customAttacks;
    private int partyOffset;
    private TrainerPokemon[] party;

    private int index;

    public Trainer(ROM rom, int index) {
        this.index = index;

        byte flags = rom.readByte();
        customAttacks = (flags & 1) == 1;
        heldsItems = (flags & 2) == 2;

        trainerClass = rom.readByte();

        byte genderMusic = rom.readByte();
        gender = (byte) ((genderMusic & 128) >> 7);
        music = (byte) (genderMusic & 127);

        sprite = rom.readByte();
        name = rom.readPokeText(12);

        items = new int[ITEM_LIMIT];

        for (int i = 0; i < ITEM_LIMIT; i++) {
            items[i] = rom.readWord();
        }

        doubleBattle = rom.readByte() == 1;
        rom.addInternalOffset(3);

        ai = rom.getPointer();

        int partyCount = rom.readByte();
        rom.addInternalOffset(3);
        partyOffset = rom.getPointerAsInt();

        loadParty(rom, partyCount);
    }

    private void loadParty(ROM rom, int count) {
        if (count <= 0 || partyOffset <= 0) {
            return;
        }

        rom.setInternalOffset(partyOffset);

        party = new TrainerPokemon[count];

        for (int i = 0; i < count; i++) {
            party[i] = new TrainerPokemon(rom, this);
        }
    }

    public static Trainer load(ROM rom, ROMData data, int index) {
        int offset = data.getTrainerTable() + (index * 40);
        rom.seek(offset);

        return new Trainer(rom, index);
    }

    @Override
    public ROMImage getImage(ROM rom, ROMData data) {
        return null;
    }
}
