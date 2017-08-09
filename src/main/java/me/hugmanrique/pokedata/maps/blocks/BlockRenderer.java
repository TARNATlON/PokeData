package me.hugmanrique.pokedata.maps.blocks;

import me.hugmanrique.pokedata.loaders.ROMData;
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

    public void renderLayer(BlockRenderData renderData, int layer) {
        ROM rom = renderData.getRom();
        ROMData data = renderData.getData();

        Graphics2D graphics = renderData.getGraphics();
        int blockPtr = renderData.getBlockPtr();

        int x = 0;
        int y = 0;

        for (int i = 0; i < 8; i += 2) {
            int offset = 8 * layer + i;

            int original = rom.readWord(blockPtr + offset);
            int tileNum = original & 0x3FF;
            int palette = (original & 0xF000) >> 12;

            boolean flipX = (original & 0x400) > 0;
            boolean flipY = (original & 0x800) > 0;

            if (renderData.isTransparency() && layer == 0) {
                try {
                    Color bg = global.getPalette(currentTime)[palette].getColor(0);

                    graphics.setColor(bg);
                } catch (Exception ignored) {}

                graphics.fillRect(x * 8, y * 8, 8, 8);
            }

            int tileIndex = tileNum;
            Tileset tileset = global;

            if (tileNum >= data.getMainTilesetSize()) {
                tileIndex -= data.getMainTilesetSize();
                tileset = local;
            }

            BufferedImage tile = tileset.getTile(tileIndex, palette, flipX, flipY, currentTime);

            graphics.drawImage(tile, x * 8, y * 8, null);

            if (++x > 1) {
                x = 0;
                y++;
            }
        }
    }

    public Image renderBlock(ROM rom, ROMData data, int blockNum) {
        return renderBlock(rom, data, blockNum, true);
    }

    public Image renderBlock(ROM rom, ROMData data, int blockNum, boolean transparency) {
        boolean secondary = false;

        if (blockNum >= data.getMainBlocks()) {
            secondary = true;
            blockNum -= data.getMainBlocks();
        }

        Tileset tileset = (secondary ? local : global);

        int blockPtr = (int) tileset.getHeader().getBlocksPtr() + blockNum * 16;

        BufferedImage block = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = getGraphics(block);

        long behaviourByte = getBehaviourByte(rom, tileset, blockNum);
        long behaviour = behaviourByte >> (rom.getGame().isElements() ? 24 : 8);

        TripleType type = TripleType.getType(behaviour, rom.getGame());

        if (type == TripleType.LEGACY2) {
            blockPtr += 8;
        }

        BlockRenderData renderData = new BlockRenderData(rom, data, graphics, blockPtr, behaviourByte, type, transparency);

        int layers = type != TripleType.NONE ? 3 : 2;

        for (int layer = 0; layer < layers; layer++) {
            renderLayer(renderData, layer);
        }

        graphics.dispose();

        return block;
    }

    private long getBehaviourByte(ROM rom, Tileset tileset, int blockNum) {
        int behaviourPtr = (int) tileset.getHeader().getBehaviorPtr();
        boolean elements = rom.getGame().isElements();

        int offset = behaviourPtr + blockNum * (elements ? 4 : 2);
        long behaviour = rom.getPointer(offset, true);

        if (!elements) {
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
