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
    public static final BlockRenderer DEFAULT = new BlockRenderer();

    private Tileset global;
    private Tileset local;

    private static int currentTime = 0;

    public void setTilesets(Tileset global, Tileset local) {
        this.global = global;
        this.local = local;
    }

    public Image renderBlock(ROM rom, int blockNum) {
        return renderBlock(rom, blockNum, true);
    }

    public Image renderBlock(ROM rom, int blockNum, boolean transparency) {
        int originalNum = blockNum;
        boolean secondary = false;

        if (blockNum >= Tileset.MAIN_BLOCKS) {
            secondary = true;
            blockNum -= Tileset.MAIN_BLOCKS;
        }

        Tileset tileset = (secondary ? local : global);

        int blockPtr = (int) tileset.getHeader().getBlocksPtr() + blockNum * 16;

        BufferedImage block = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = getGraphics(block);

        int x = 0;
        int y = 0;
        int layer = 0;

        long behaviourByte = getBehaviourByte(rom, originalNum);
        long behaviour = behaviourByte >> (rom.getGame().isElements() ? 24 : 8);

        TripleType type = TripleType.getType(behaviour, rom.getGame());

        if (type == TripleType.LEGACY2) {
            blockPtr += 8;
        }

        int size = type != TripleType.NONE ? 24 : 16;

        for (int i = 0; i < size; i++) {
            if (type == TripleType.REFERENCE && i == 16) {
                boolean second = false;
                int tripleNum = (int) (behaviourByte >> 14) & 0x3FF;

                if (tripleNum >= Tileset.MAIN_BLOCKS) {
                    second = true;
                    tripleNum = Tileset.MAIN_BLOCKS;
                }

                blockPtr = (int) (second ? local : global).getHeader().getBlocksPtr() + (tripleNum * 16) + 8;
                blockPtr -= i;
            }

            int original = rom.readWord(blockPtr + i);
            int tileNum = original & 0x3FF;
            int palette = (original & 0xF000) >> 12;

            boolean flipX = (original & 0x400) > 0;
            boolean flipY = (original & 0x800) > 0;

            // We need a background
            if (transparency && layer == 0) {
                try {
                    Color bg = global.getPalette(currentTime)[palette].getColor(0);
                    graphics.setColor(bg);
                } catch (Exception ignored) {}

                graphics.fillRect(x * 8, y * 8, 8, 8);
            }

            int tileIndex = tileNum;
            Tileset tileTileset = global;

            if (tileNum >= Tileset.MAIN_SIZE) {
                tileIndex = tileNum - Tileset.MAIN_SIZE;
                tileTileset = local;
            }

            BufferedImage tile = tileTileset.getTile(tileIndex, palette, flipX, flipY, currentTime);

            graphics.drawImage(tile, x * 8, y * 8, null);

            x++;

            if (x > 1) {
                x = 0;
                y++;
            }

            if (y > 1) {
                x = 0;
                y = 0;
                layer++;
            }

            i++;
        }

        return block;
    }

    private long getBehaviourByte(ROM rom, int blockId) {
        int behaviourPtr = (int) global.getHeader().getBehaviorPtr();
        int blockNum = blockId;

        if (blockNum >= Tileset.MAIN_BLOCKS) {
            blockNum -= Tileset.MAIN_BLOCKS;
            behaviourPtr = (int) global.getHeader().getBehaviorPtr();
        }

        boolean elements = rom.getGame().isElements();

        int offset = behaviourPtr + blockNum * (elements ? 4 : 2);
        long behaviour = rom.getPointer(offset, true);

        if (elements) {
            behaviour &= 0xFFFF;
        }

        return behaviour;
    }

    private Graphics2D getGraphics(BufferedImage image) {
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        return graphics;
    }
}
