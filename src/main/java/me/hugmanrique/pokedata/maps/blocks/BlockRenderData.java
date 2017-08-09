package me.hugmanrique.pokedata.maps.blocks;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

import java.awt.*;

@Data
@AllArgsConstructor
public class BlockRenderData {
    private ROM rom;
    private ROMData data;

    private Graphics2D graphics;

    private int blockPtr;
    // TODO Rename, this isn't a byte
    private long behaviourByte;
    private TripleType type;

    private boolean transparency;
}
