package me.hugmanrique.pokedata.maps;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.connections.ConnectionData;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.maps.banks.BankLoader;
import me.hugmanrique.pokedata.sprites.SpritesHeader;
import me.hugmanrique.pokedata.sprites.exits.SpritesExitManager;
import me.hugmanrique.pokedata.sprites.npcs.SpritesNPCManager;
import me.hugmanrique.pokedata.sprites.signs.SpritesSignManager;
import me.hugmanrique.pokedata.sprites.triggers.TriggerManager;
import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.roms.ROM;

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

    public Map(ROM rom) {
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
    }

    public static Map load(ROM rom, ROMData data, int bank, int map) {
        int pointer = BankLoader.getMapHeaderPointer(rom, data, bank, map);
        rom.seek(pointer);

        return new Map(rom);
    }
}
