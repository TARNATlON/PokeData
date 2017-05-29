package me.hugmanrique.pokedata.graphics;

import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
public interface Imageable {
    ROMImage getImage(ROM rom, ROMData data);
}
