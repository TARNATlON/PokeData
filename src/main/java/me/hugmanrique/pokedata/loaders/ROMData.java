package me.hugmanrique.pokedata.loaders;

import lombok.Getter;
import me.hugmanrique.pokedata.pokemon.ev.EvolutionParam;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

/**
 * @author Hugmanrique
 * @since 27/05/2017
 */
@Getter
public class ROMData {
    private static final int BANKS = 42;

    private String romName;
    private long itemData;
    private long attackNames;
    private long tmData;
    private int totalTMsAndHMs;
    private int totalTMs;
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

    public ROMData(Wini store, String header) {
        DataLoader loader = new DataLoader(store, header);

        romName = loader.getString("ROMName");
        itemData = loader.getLong("ItemData");
        attackNames = loader.getLong("AttackNames");
        tmData = loader.getLong("TMData");
        totalTMsAndHMs = loader.getInt("TotalTMsPlusHMs");
        totalTMs = loader.getInt("TotalTMs");
        itemImgData = loader.getLong("ItemIMGData");
        itemsNumber = loader.getInt("NumberOfItems");
        attacksNumber = loader.getInt("NumberOfAttacks");
        moveTutorAttacks = loader.getLong("MoveTutorAttacks");
        moveTutorAttacksNumber = loader.getInt("NumberOfMoveTutorAttacks");
        pokemonNames = loader.getLong("PokemonNames");
        pokemonsNumber = loader.getInt("NumberOfPokemon");
        nationalDexTable = loader.getLong("NationalDexTable");
        secondDexTable = loader.getLong("SecondDexTable");
        pokedexData = loader.getLong("PokedexData");
        dexEntriesNumber = loader.getInt("NumberOfDexEntries");
        regionDexEntriesNumber = loader.getInt("NumberOfRegionDex");
        pokemonData = loader.getLong("PokemonData");
        abilityNames = loader.getLong("AbilityNames");
        abilitiesNumber = loader.getInt("NumberOfAbilities");

        pointerOfMapBanksPointer = loader.getLong("Pointer2PointersToMapBanks");

        bankPointers = new long[BANKS];
        mapsInBanksNumber = new int[BANKS];

        for (int i = 0; i < BANKS; i++) {
            bankPointers[i] = loader.getLong("OriginalBankPointer" + i);
            mapsInBanksNumber[i] = loader.getInt("NumberOfMapsInBank" + i);
        }

        mapLabelData = loader.getLong("MapLabelData");
        mapLabelsNumber = loader.getInt("NumberOfMapLabels");

        // TODO Load tiles in tileset

        pokemonFrontSprites = loader.getLong("PokemonFrontSprites");
        pokemonBackSprites = loader.getLong("PokemonBackSprites");
        pokemonNormalPal = loader.getLong("PokemonNormalPal");
        pokemonShinyPal = loader.getLong("PokemonShinyPal");
        iconPointerTable = loader.getLong("IconPointerTable");
        iconPalTable = loader.getLong("IconPalTable");
        cryTable = loader.getLong("CryTable");
        cryTable2 = loader.getLong("CryTable2");
        cryCoversionTable = loader.getLong("CryConversionTable");
        footPrintTable = loader.getLong("FootPrintTable");
        pokemonAttackTable = loader.getLong("PokemonAttackTable");
        pokemonEvolutions = loader.getLong("PokemonEvolutions");
        tmhmcCompatibility = loader.getLong("TMHMCompatibility");
        tmhmlLenPerPoke = loader.getInt("TMHMLenPerPoke");
        moveTutorCompatibility = loader.getLong("MoveTutorCompatibility");
        enemyYTable = loader.getLong("EnemyYTable");
        playerYTable = loader.getLong("PlayerYTable");
        enemyAltitudeTable = loader.getLong("EnemyAltitudeTable");
        attackData = loader.getLong("AttackData");
        attackDescriptionTable = loader.getLong("AttackDescriptionTable");
        abilityDescriptionTable = loader.getLong("AbilityDescriptionTable");
        attackAnimationTable = loader.getLong("AttackAnimationTable");
        iconPals = loader.getLong("IconPals");
        jamboLearnableMovesTerm = loader.getLong("JamboLearnableMovesTerm");
        startSearchingForSpaceOffset = loader.getLong("StartSearchingForSpaceOffset");
        freeSpaceSearchInterval = loader.getInt("FreeSpaceSearchInterval");
        evolutionsPerPokeNumber = loader.getInt("NumberOfEvolutionsPerPokemon");
        evolutionTypesNumber = loader.getInt("NumberOfEvolutionTypes");
        evolutionParams = new EvolutionParam[evolutionTypesNumber];

        for (int i = 0; i < evolutionTypesNumber; i++) {
            String param = loader.getString("Evolution" + i + "Param");
            evolutionParams[i] = EvolutionParam.byConfig(param);
        }

        eggMoveTable = loader.getLong("EggMoveTable");
        eggMoveTableLimiter = loader.getLong("EggMoveTableLimiter");
        habitatTable = loader.getLong("HabitatTable");
        itemAnimationTable = loader.getLong("ItemAnimationTable");
        trainerTable = loader.getLong("TrainerTable");
        trainersNumber = loader.getInt("NumberOfTrainers");
        trainerClasses = loader.getLong("TrainerClasses");
        trainerClassesNumber = loader.getInt("NumberOfTrainerClasses");
        trainerImageTable = loader.getLong("TrainerImageTable");
        trainerImagesNumber = loader.getInt("NumberOfTrainerImages");
        trainerPaletteTable = loader.getLong("TrainerPaletteTable");
        trainerMoneyTable = loader.getLong("TrainerMoneyTable");
        dexSizeTrainerSprite = loader.getInt("DexSizeTrainerSprite");
        tradeData = loader.getLong("TradeData");
        tradesNumber = loader.getInt("NumberOfTrades");
        pokedexAlphabetTable = loader.getLong("PokedexAlphabetTable");
        pokedexLightestTable = loader.getLong("PokedexLightestTable");
        pokedexSmallestTable = loader.getLong("PokedexSmallestTable");
        pokedexTypeTable = loader.getLong("PokedexTypeTable");
    }

    public ROMData(File file, String header) throws IOException {
        this(new Wini(file), header);
    }

    public ROMData(String header) throws IOException {
        this(
            new Wini(
                ROMData.class.getClassLoader().getResourceAsStream("roms.ini")
            ),
            header
        );
    }
}
