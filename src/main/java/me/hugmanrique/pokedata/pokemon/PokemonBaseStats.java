package me.hugmanrique.pokedata.pokemon;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_base_stats_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 01/05/2017
 */
@Getter
@ToString
public class PokemonBaseStats extends Data {
    private byte baseHp;
    private byte baseAttack;
    private byte baseDefense;
    private byte baseSpeed;
    private byte baseSpAttack;
    private byte baseSpDefense;
    private Type type1;
    private Type type2;
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

    public PokemonBaseStats(ROM rom) {
        baseHp = rom.readByte();
        baseAttack = rom.readByte();
        baseDefense = rom.readByte();
        baseSpeed = rom.readByte();
        baseSpAttack = rom.readByte();
        baseSpDefense = rom.readByte();
        type1 = Type.byId(rom.readByte());
        type2 = Type.byId(rom.readByte());
        catchRate = rom.readByte();
        baseExp = rom.readByte();
        effort = rom.readWord();
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

    public byte getEvHp() {
        return (byte) BitConverter.getBitRange(effort, 0, 2);
    }

    public byte getEvAttack() {
        return (byte) BitConverter.getBitRange(effort, 2, 4);
    }

    public byte getEvDefense() {
        return (byte) BitConverter.getBitRange(effort, 4, 6);
    }

    public byte getEvSpeed() {
        return (byte) BitConverter.getBitRange(effort, 6, 8);
    }

    public byte getEvSpAttack() {
        return (byte) BitConverter.getBitRange(effort, 8, 10);
    }

    public byte getEvSpDefense() {
        return (byte) BitConverter.getBitRange(effort, 10, 12);
    }

    public Gender getGenderType() {
        return Gender.byValue(gender);
    }

    public static PokemonBaseStats load(ROM rom, ROMData data, int id) {
        int offset = data.getPokemonData() + (id * 28);
        rom.setInternalOffset(offset);

        return new PokemonBaseStats(rom);
    }
}
