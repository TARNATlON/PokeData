package me.hugmanrique.pokedata.maps.blocks;

import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.tiles.Tileset;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Hugmanrique
 * @since 02/07/2017
 */
public class BlockRenderer {

    private Tileset global;
    private Tileset local;

    private static int currentTime = 0;

    public void setTilesets(Tileset global, Tileset local) {
        this.global = global;
        this.local = local;
    }

    public Image renderBlock(int blockNum) {
        renderBlock(blockNum, true);
    }

    public Image renderBlock(ROM rom, int blockNum, boolean transparency) {
        int original = blockNum;
        boolean secondary = false;

        if (blockNum >= Tileset.MAIN_BLOCKS) {
            secondary = true;
            blockNum -= Tileset.MAIN_BLOCKS;
        }

        int blockPtr = (int) (secondary ? local : global).getHeader().getBlocksPtr() + blockNum * 16;

        BufferedImage block = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = getGraphics(block);

        int x = 0;
        int y = 0;
        int layer = 0;

        long behaviour = getBehaviourByte(rom, original) >> (rom.getGame().isElements() ? 24 : 8);
        TripleType type = TripleType.getType(behaviour, rom.getGame());

        if (type == TripleType.LEGACY2) {
            blockPtr += 8;
        }

        int size = type != TripleType.NONE ? 24 : 16;

        for (int i = 0; i < size; i++) {

        }










    }

    private long getBehaviourByte(ROM rom, int blockId) {
        int behaviourPtr = (int) global.getHeader().getBehaviorPtr();
        int blockNum = blockId;

        if (blockNum >= Tileset.MAIN_BLOCKS) {
            blockNum -= Tileset.MAIN_BLOCKS;
            behaviourPtr = (int) local.getHeader().getBehaviorPtr();
        }

        boolean elements = rom.getGame().isElements();

        rom.seek(behaviourPtr + blockNum * (elements ? 4 : 2));

        return elements ? rom.getPointer(true) : rom.getPointer(true) & 0xFFFF;
    }

    private Graphics2D getGraphics(BufferedImage image) {
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        return graphics;
    }
}
