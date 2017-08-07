package me.hugmanrique.pokedata.maps.banks;

import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * Loads map banks data, may be run asynchronously if the
 * {@link ROM} implementation is thread safe.
 * @author Hugmanrique
 * @since 02/06/2017
 */
public class BankLoader {
    private static int[] bankPointers;
    private static int[] mapsInBanksNum;

    public static int getMapHeaderPointer(ROM rom, ROMData data, int bank, int map) {
        if (bankPointers == null) {
            loadPointers(data);
        }

        // Perform bounds validation
        if (bank >= bankPointers.length) {
            throw new IllegalArgumentException("Bank number is too high. Got: " + bank + ", expected less than " + bankPointers.length);
        }

        if (map >= mapsInBanksNum[bank]) {
            throw new IllegalArgumentException("Map number for bank " + bank + " is too hight. Got: " + map + ", expected less than " + mapsInBanksNum[bank]);
        }

        int bankOffset = bankPointers[bank] + (map * 4);

        return rom.getPointerAsInt(bankOffset);
    }

    private static void loadPointers(ROMData data) {
        bankPointers = data.getBankPointers();
        mapsInBanksNum = data.getMapsInBanksNumber();
    }
}
