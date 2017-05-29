package me.hugmanrique.pokedata.items;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Item_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
public class Item extends Data {
    private String name;
    private int index;
    private int price;
    private byte holdEffect;
    private byte parameter;
    private long descriptionPtr;
    private int mysteryValue;
    private byte pocket;
    private byte type;
    private long usageCodePtr;
    private long battleUsage;
    private long battleUsagePtr;
    private long extraParam;

    public Item(ROM rom) {
        name = rom.readPokeText(14);
        index = rom.readWord();
        price = rom.readWord();
        holdEffect = rom.readByte();
        parameter = rom.readByte();
        descriptionPtr = rom.getPointer();
        mysteryValue = rom.readWord();
        pocket = rom.readByte();
        type = rom.readByte();
        usageCodePtr = rom.getPointer();
        battleUsage = rom.readLong();
        battleUsagePtr = rom.getPointer();
        extraParam = rom.readLong();
    }

    public static Item load(ROM rom, ROMData data, int index) {
        int offset = data.getItemData() + (index * 44);
        rom.setInternalOffset(offset);

        return new Item(rom);
    }
}
