package me.hugmanrique.pokedata.sprites;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * Bulbapedia article missing
 * @author Hugmanrique
 * @since 02/05/2017
 */
@Getter
public class SpritesHeader extends Data {
    private byte npcAmount;
    private byte exitsAmount;
    private byte triggersAmount;
    private byte signsAmount;

    private long npcPtr;
    private long exitsPtr;
    private long triggersPtr;
    private long signsPtr;

    public SpritesHeader(ROM rom) {
        npcAmount = rom.readByte();
        exitsAmount = rom.readByte();
        triggersAmount = rom.readByte();
        signsAmount = rom.readByte();

        npcPtr = rom.getPointer();
        exitsPtr = rom.getPointer();
        triggersPtr = rom.getPointer();
        signsPtr = rom.getPointer();
    }
}
