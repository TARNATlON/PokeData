package me.hugmanrique.pokedata.pokedex;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.graphics.Imageable;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.Game;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.ImageUtils;

/**
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
@ToString
public class Pokedex extends Data implements Imageable {
    private String name;
    private int height;
    private int weight;
    private long desc1Ptr;
    private long desc2Ptr;
    private int scale;
    private int offset;
    private int trainerScale;
    private int trainerOffset;

    private int index;

    public Pokedex(ROM rom, boolean emerald, int index) {
        this.index = index;
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

        return new Pokedex(rom, emerald, pokemon);
    }

    @Override
    public ROMImage getImage(ROM rom, ROMData data) {
        throw new UnsupportedOperationException("Use getFrontImage or getBackImage instead");
    }

    public Palette getNormalPal(ROM rom, ROMData data) {
        rom.setInternalOffset(data.getPokemonNormalPal() + (index * 8));
        int pointer = rom.getPointerAsInt();

        return ImageUtils.getPalette(rom, pointer);
    }

    public Palette getShinyPal(ROM rom, ROMData data) {
        rom.setInternalOffset(data.getPokemonShinyPal() + (index * 8));
        int pointer = rom.getPointerAsInt();

        return ImageUtils.getPalette(rom, pointer);
    }

    public ROMImage getFrontImage(ROM rom, ROMData data, boolean shiny) {
        Palette palette = !shiny ? getNormalPal(rom, data) : getShinyPal(rom, data);
        int pointer = data.getPokemonFrontSprites() + (index * 8);

        return ImageUtils.getImage(rom, pointer, palette, 64, 64);
    }

    public ROMImage getBackImage(ROM rom, ROMData data, boolean shiny) {
        Palette palette = !shiny ? getNormalPal(rom, data) : getShinyPal(rom, data);
        int pointer = data.getPokemonBackSprites() + (index * 8);

        return ImageUtils.getImage(rom, pointer, palette, 64, 64);
    }
}
