package me.hugmanrique.pokedata.loaders;

import me.hugmanrique.pokedata.pokemon.ev.EvolutionParam;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
public class ROMData {
    private String romName;
    private long itemData;
    private long attackNames;
    private long tmData;
    private short totalTMsAndHMs;
    private short totalTMs;
    private long itemImgData;
    private int itemsNumber;
    private int attacksNumber;
    private long moveTutorAttacks;
    private int moveTutorAttacksNumber;
    private long pokemonNames;
    private int pokemonsNumber;
    private long nationalDexTable;
    private long secondDexTable;
    private long pokedexData;
    private int dexEntriesNumber;
    private int regionDexEntriesNumber;
    private long pokemonData;
    private long abilityNames;
    private int abilitiesNumber;
    private long pointerOfMapBanksPointer;

    // Should always have a length of 42
    private long[] bankPointers;
    private int[] mapsInBanksNumber;

    private long mapLabelData;
    private int mapLabelsNumber;

    // TODO Add all the tilesets

    private long pokemonFrontSprites;
    private long pokemonBackSprites;
    private long pokemonNormalPal;
    private long pokemonShinyPal;
    private long iconPointerTable;
    private long iconPalTable;
    private long cryTable;
    private long cryTable2;
    private long cryCoversionTable;
    private long footPrintTable;
    private long pokemonAttackTable;
    private long pokemonEvolutions;
    private long tmhmcCompatibility;
    private int tmhmlLenPerPoke;
    private long moveTutorCompatibility;
    private long enemyYTable;
    private long playerYTable;
    private long enemyAltitudeTable;
    private long attackData;
    private long attackDescriptionTable;
    private long abilityDescriptionTable;
    private long attackAnimationTable;
    private long iconPals;
    private long jamboLearnableMovesTerm;
    private long startSearchingForSpaceOffset;
    private int freeSpaceSearchInterval;
    private int evolutionsPerPokeNumber;
    private int evolutionTypesNumber;

    // Should have a length of `evolutionTypesNumber`
    private String[] evolutionNames;
    private EvolutionParam[] evolutionParams;

    private long eggMoveTable;
    private long eggMoveTableLimiter;
    private long habitatTable;
    private long itemAnimationTable;
    private long trainerTable;
    private int trainersNumber;
    private long trainerClasses;
    private int trainerClassesNumber;
    private long trainerImageTable;
    private int trainerImagesNumber;
    private long trainerPaletteTable;
    private long trainerMoneyTable;
    private int dexSizeTrainerSprite;
    private long tradeData;
    private int tradesNumber;
    private long pokedexAlphabetTable;
    private long pokedexLightestTable;
    private long pokedexSmallestTable;
    private long pokedexTypeTable;
}
