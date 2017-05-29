package me.hugmanrique.pokedata.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@AllArgsConstructor
@Data
public class Pair<T, U> {
    private T first;
    private U second;

    public Pair() {
        this(null, null);
    }

    public boolean allSet() {
        return first != null && second != null;
    }
}
