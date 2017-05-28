package me.hugmanrique.pokedata.loaders;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
public abstract class Loader<T> {
    protected ROMData data;

    public Loader(ROMData data) {
        this.data = data;
    }

    public abstract T load();
}
