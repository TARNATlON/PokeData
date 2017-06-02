package me.hugmanrique.pokedata.pokedex;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.graphics.ImageType;
import me.hugmanrique.pokedata.graphics.Imageable;
import me.hugmanrique.pokedata.graphics.Palette;
import me.hugmanrique.pokedata.graphics.ROMImage;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.Game;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.utils.ImageUtils;

import java.awt.*;
import java.util.Arrays;

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
    private byte iconPal;

    private int index;

    public Pokedex(ROM rom, boolean emerald, int index, byte iconPal) {
        this.index = index;
        this.iconPal = iconPal;

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
        // Icon palette loading
        byte iconPal = rom.readByte(data.getIconPalTable() + pokemon + 1);

        boolean emerald = rom.getGame() == Game.EMERALD;

        int size = emerald ? 32 : 36;
        int offset = data.getPokedexData() + (pokemon * size);

        rom.setInternalOffset(offset);

        return new Pokedex(rom, emerald, pokemon, iconPal);
    }

    // Image rendering methods

    @Override
    public ROMImage getImage(ROM rom, ROMData data) {
        throw new UnsupportedOperationException("Use getFrontImage or getBackImage instead");
    }

    public Palette getNormalPal(ROM rom, ROMData data) {
        rom.setInternalOffset(data.getPokemonNormalPal() + (index * 8));
        int offset = rom.getPointerAsInt();

        return ImageUtils.getPalette(rom, offset);
    }

    public Palette getShinyPal(ROM rom, ROMData data) {
        rom.setInternalOffset(data.getPokemonShinyPal() + (index * 8));
        int offset = rom.getPointerAsInt();

        return ImageUtils.getPalette(rom, offset);
    }

    public Palette getIconPal(ROM rom, ROMData data) {
        int offset = data.getIconPals() + (iconPal * 32);

        Palette palette = ImageUtils.getCache(offset);

        if (palette != null) {
            return palette;
        }

        // Palette isn't Lz77 compressed
        palette = new Palette(ImageType.C16, rom, offset);

        ImageUtils.addCache(offset, palette);

        return palette;
    }

    public ROMImage getFrontImage(ROM rom, ROMData data, boolean shiny) {
        rom.setInternalOffset(data.getPokemonFrontSprites() + (index * 8));
        int offset = rom.getPointerAsInt();

        Palette palette = !shiny ? getNormalPal(rom, data) : getShinyPal(rom, data);

        return ImageUtils.getImage(rom, offset, palette, 64, 64);
    }

    public ROMImage getBackImage(ROM rom, ROMData data, boolean shiny) {
        rom.setInternalOffset(data.getPokemonBackSprites() + (index * 8));
        int offset = rom.getPointerAsInt();

        Palette palette = !shiny ? getNormalPal(rom, data) : getShinyPal(rom, data);

        return ImageUtils.getImage(rom, offset, palette, 64, 64);
    }

    public ROMImage getIconImage(ROM rom, ROMData data) {
        Palette palette = getIconPal(rom, data);

        rom.setInternalOffset(data.getIconPointerTable() + (index * 4));
        int offset = rom.getPointerAsInt();

        // Image isn't Lz77 compressed
        int[] imageData = BitConverter.toInts(rom.readBytes(offset, 0xFFF));

        return new ROMImage(palette, imageData, 32, 64);
    }

    public ROMImage getFootPrint(ROM rom, ROMData data, Palette palette) {
        rom.setInternalOffset(data.getFootPrintTable() + index * 4);
        int offset = rom.getPointerAsInt();

        // Image isn't Lz77 compressed
        byte[] imageData = rom.readBytes(offset, 0xFF);

        return ImageUtils.loadRawSprite(imageData, palette, 16, 16);
    }

    /**
     * Creates a {@link ROMImage} with the footprint contents
     * @param colors An array with 2 items, first will be background;
     *               second is the footprint color
     * @see Color
     */
    public ROMImage getFootPrint(ROM rom, ROMData data, Color[] colors) {
        if (colors.length != 2) {
            throw new IllegalArgumentException("Color array must have a length of 2");
        }

        Palette palette = ImageUtils.createCustomPal(colors);

        return getFootPrint(rom, data, palette);
    }

    public ROMImage getFootPrint(ROM rom, ROMData data, boolean transparent) {
        Palette palette = transparent ? ImageUtils.getTransBlackPal() : ImageUtils.getBlackWhitePal();

        return getFootPrint(rom, data, palette);
    }

    public ROMImage getAnimationImage(ROM rom, ROMData data, boolean shiny) {
        // TODO Get ROM locations for games other than Emerald
        //rom.setInternalOffset(data.getPokemonAnimations());

        throw new UnsupportedOperationException("Not supported yet");
    }

    // TODO Get ROM locations for shadow sprites other than Emerald
}
