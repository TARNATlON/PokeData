package me.hugmanrique.pokedata.pokemon;

import lombok.Getter;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.roms.ROM;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
public class Pokemon extends Data {
    private int personality;
    private int otId;
    private String nickname;
    private short language;
    private String otName;
    private byte markings;
    private short checksum;

    private PokemonData data;

    private int status;
    private byte level;
    private byte pokerusRem;
    private short hp;
    private short totalHp;

    private short attack;
    private short defense;
    private short speed;
    private short spAttack;
    private short spDefense;

    public Pokemon(ROM rom) {
        // TODO
    }
}
