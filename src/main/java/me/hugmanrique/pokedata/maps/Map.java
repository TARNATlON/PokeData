package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.connections.ConnectionData;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.banks.BankLoader;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.sprites.SpritesHeader;
import me.hugmanrique.pokedata.sprites.exits.SpritesExitManager;
import me.hugmanrique.pokedata.sprites.npcs.SpritesNPCManager;
import me.hugmanrique.pokedata.sprites.signs.SpritesSignManager;
import me.hugmanrique.pokedata.sprites.triggers.TriggerManager;
import me.hugmanrique.pokedata.tiles.TilesetCache;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * Bulbapedia article missing
 * As the map data class contains a lot of values, each "section" is
 * separated into child classes
 *
 * Data structure gathered by @shinyquagsire23,
 * Extracted from MEH (https://github.com/shinyquagsire23/MEH)
 * Thanks!
 *
 * @author shinyquagsire23, Hugmanrique
 * @since 30/04/2017
 */
@Getter
@ToString
public class Map extends Data {
    private MapHeader header;
    private ConnectionData connections;
    private SpritesHeader sprites;

    private SpritesNPCManager npcManager;
    private SpritesSignManager signManager;
    private TriggerManager triggerManager;
    private SpritesExitManager exitManager;

    private MapData data;
    private MapTileData tileData;

    public Map(ROM rom, ROMData data) {
        header = new MapHeader(rom);

        rom.seek(BitConverter.shortenPointer(header.getConnectPtr()));
        connections = new ConnectionData(rom);

        // Sprites loading
        rom.seek((int) header.getSpritesPtr() & 0x1FFFFFF);
        sprites = new SpritesHeader(rom);

        npcManager = new SpritesNPCManager(rom, (int) sprites.getNpcPtr(), sprites.getNpcAmount());
        signManager = new SpritesSignManager(rom, (int) sprites.getSignsPtr(), sprites.getSignsAmount());
        triggerManager = new TriggerManager(rom, (int) sprites.getTriggersPtr(), sprites.getTriggersAmount());
        exitManager = new SpritesExitManager(rom, (int) sprites.getExitsPtr(), sprites.getExitsAmount());

        this.data = MapData.load(rom, data, (int) header.getMapPtr());
        tileData = new MapTileData(rom, this.data);
    }

    public static Map load(ROM rom, ROMData data, int bank, int id) {
        int pointer = BankLoader.getMapHeaderPointer(rom, data, bank, id);
        rom.seek(pointer);

        Map map = new Map(rom, data);
        injectMapName(rom, data, map);

        // Load and cache map tilesets
        TilesetCache.switchTileset(rom, data, map.getData());

        return map;
    }

    private static void injectMapName(ROM rom, ROMData data, Map map) {
        byte labelId = map.getHeader().getLabelId();
        int offset;

        // Switch depending on the engine version
        if (rom.getGame().isElements()) {
            offset = data.getMapLabelData() + (labelId  - 0x58) * 4;
        } else {
            // TODO Will cause issues with custom games
            offset = data.getMapLabelData() + (labelId * 8) + 4;
        }

        int namePointer = rom.getPointerAsInt(offset);
        String name = rom.readPokeText(namePointer, -1);

        map.setHeaderName(name);
    }

    private void setHeaderName(String name) {
        getHeader().setName(name);
    }
}
