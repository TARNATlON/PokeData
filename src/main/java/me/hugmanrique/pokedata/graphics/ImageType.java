package me.hugmanrique.pokedata.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Hugmanrique
 * @since 29/05/2017
 */
@Getter
@AllArgsConstructor
public enum ImageType {
    C16(16),
    C256(256);

    private int size;

    public int getROMSize() {
        return size * 2;
    }
}
