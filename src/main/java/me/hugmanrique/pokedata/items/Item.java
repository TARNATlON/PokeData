package me.hugmanrique.pokedata.items;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.compression.Lz77;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Imageable;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.ImageUtils;

import java.awt.*;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Item_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
@ToString
public class Item extends Data implements Imageable {
    private String name;
    private int index;
    private int price;
    private byte holdEffect;
    private byte parameter;
    private long descriptionPtr;
    private int mysteryValue;
    private Pocket pocket;
    private byte type;
    private long usageCodePtr;
    private long battleUsage;
    private long battleUsagePtr;
    private long extraParam;

    private int id;

    public Item(ROM rom, int id) {
        this.id = id;

        name = rom.readPokeText(14);
        index = rom.readWord();
        price = rom.readWord();
        holdEffect = rom.readByte();
        parameter = rom.readByte();
        descriptionPtr = rom.getPointer();
        mysteryValue = rom.readWord();
        pocket = Pocket.byId(rom.getGame(), rom.readByte());
        type = rom.readByte();
        usageCodePtr = rom.getPointer();
        battleUsage = rom.readLong();
        battleUsagePtr = rom.getPointer();
        extraParam = rom.readLong();
    }

    public String getDescription(ROM rom) {
        return rom.readPokeText((int) descriptionPtr, -1);
    }

    public static Item load(ROM rom, ROMData data, int index) {
        int offset = data.getItemData() + (index * 44);
        rom.seek(offset);

        return new Item(rom, index);
    }

    @Override
    public ROMImage getImage(ROM rom, ROMData data) {
        rom.seek(data.getItemImgData() + (id * 8));

        int imagePtr = rom.getPointerAsInt();
        int palettePtr = rom.getPointerAsInt();

        Palette palette = ImageUtils.getPalette(rom, palettePtr);

        return ImageUtils.getImage(rom, imagePtr, palette, 24, 24);
    }
}
