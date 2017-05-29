package me.hugmanrique.pokedata.connections;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.BitConverter;
import me.hugmanrique.pokedata.roms.ROM;

import java.util.ArrayList;
import java.util.List;

/**
 * Bulbapedia article missing
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class ConnectionData extends Data {
    private long numConnsPtr;
    private long dataPtr;

    private List<Connection> connections;

    public ConnectionData(ROM rom) {
        numConnsPtr = rom.getPointer(true);
        dataPtr = rom.getPointer(true);

        connections = new ArrayList<>();
        loadConnections(rom);
    }

    private void loadConnections(ROM rom) {
        rom.seek(BitConverter.shortenPointer(dataPtr));

        for (int i = 0; i < numConnsPtr; i++) {
            connections.add(new Connection(rom));
        }
    }
}
