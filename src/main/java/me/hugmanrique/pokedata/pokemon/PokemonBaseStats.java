package me.hugmanrique.pokedata.pokemon;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.utils.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_base_stats_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
public class PokemonBaseStats extends Data {
    private byte baseHp;
    private byte baseAttack;
    private byte baseDefense;
    private byte baseSpeed;
    private byte baseSpAttack;
    private byte baseSpDefense;
    private byte type1;
    private byte type2;
    private byte catchRate;
    private byte baseExp;
    private int effort;
    private int item1;
    private int item2;
    private byte gender;
    private byte eggCycles;
    private byte baseFriendship;
    private byte levelUpType;
    private byte eggGroup1;
    private byte eggGroup2;
    private byte ability1;
    private byte ability2;
    private byte safariZoneRate;
    private byte colorFlip;

    @Override
    public void read(ROM rom) {
        baseHp = rom.readByte();
        baseAttack = rom.readByte();
        baseDefense = rom.readByte();
        baseSpeed = rom.readByte();
        baseSpAttack = rom.readByte();
        baseSpDefense = rom.readByte();
        type1 = rom.readByte();
        type2 = rom.readByte();
        catchRate = rom.readByte();
        baseExp = rom.readByte();
        effort = rom.readByte();
        item1 = rom.readWord();
        item2 = rom.readWord();
        gender = rom.readByte();
        eggCycles = rom.readByte();
        baseFriendship = rom.readByte();
        levelUpType = rom.readByte();
        eggGroup1 = rom.readByte();
        eggGroup2 = rom.readByte();
        ability1 = rom.readByte();
        ability2 = rom.readByte();
        safariZoneRate = rom.readByte();
        colorFlip = rom.readByte();

        // Skip word pad
        rom.addInternalOffset(2);
    }
}
