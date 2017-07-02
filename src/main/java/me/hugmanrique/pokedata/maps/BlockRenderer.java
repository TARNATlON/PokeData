package me.hugmanrique.pokedata.maps;

import me.hugmanrique.pokedata.tiles.Tileset;

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



}
