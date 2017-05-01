package me.hugmanrique.pokedata.items;

import me.hugmanrique.pokedata.Data;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Item_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
public class Item extends Data {
    private String name;
    private short index;
    private short price;
    private byte holdEffect;
    private byte parameter;
    private long descriptionPtr;
    private short mysteryValue;
    private byte pocket;
    private byte type;
    private long usageCodePtr;
    private long battleUsage;
    private long battleUsagePtr;
    private long extraParam;
}
