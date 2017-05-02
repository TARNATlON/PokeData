package me.hugmanrique.pokedata.pokemon;

import lombok.Getter;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Pok%C3%A9mon_data_substructures_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
public class PokemonData {
    // Growth
    private short species;
    private short heldItem;
    private int experience;
    private byte ppBonuses;
    private byte friendship;

    // Attacks
    private short move1;
    private short move2;
    private short move3;
    private short move4;

    private byte pp1;
    private byte pp2;
    private byte pp3;
    private byte pp4;

    // EVs and Condition
    private byte hpEv;
    private byte attackEv;
    private byte defenseEv;
    private byte speedEv;
    private byte spAttackEv;
    private byte spDefenseEv;
    private byte coolness;
    private byte beauty;
    private byte cuteness;
    private byte smartness;
    private byte toughness;
    private byte feel;

    // Misc
    private byte pokerus;
    private byte metLoc;
    private short origins;
    private int ivEggAbility;
    private int ribbonsObedience;
}
