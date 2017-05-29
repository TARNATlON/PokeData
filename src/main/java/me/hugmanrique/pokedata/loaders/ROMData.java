package me.hugmanrique.pokedata.loaders;

import lombok.Getter;
import me.hugmanrique.pokedata.pokedex.EvolutionParam;
import me.hugmanrique.pokedata.roms.ROM;
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
    private int itemData;
    private int attackNames;
    private int tmData;
    private int totalTMsAndHMs;
    private int totalTMs;
    private int itemImgData;
    private int itemsNumber;
    private int attacksNumber;
    private int moveTutorAttacks;
    private int moveTutorAttacksNumber;
    private int pokemonNames;
    private int pokemonsNumber;
    private int nationalDexTable;
    private int secondDexTable;
    private int pokedexData;
    private int dexEntriesNumber;
    private int regionDexEntriesNumber;
    private int pokemonData;
    private int abilityNames;
    private int abilitiesNumber;
    private int pointerOfMapBanksPointer;

    // Should always have a length of 42
    private int[] bankPointers;
    private int[] mapsInBanksNumber;

    private int mapLabelData;
    private int mapLabelsNumber;

    // TODO Add all the tilesets

    private int pokemonFrontSprites;
    private int pokemonBackSprites;
    private int pokemonNormalPal;
    private int pokemonShinyPal;
    private int iconPointerTable;
    private int iconPalTable;
    private int cryTable;
    private int cryTable2;
    private int cryCoversionTable;
    private int footPrintTable;
    private int pokemonAttackTable;
    private int pokemonEvolutions;
    private int tmhmcCompatibility;
    private int tmhmlLenPerPoke;
    private int moveTutorCompatibility;
    private int enemyYTable;
    private int playerYTable;
    private int enemyAltitudeTable;
    private int attackData;
    private int attackDescriptionTable;
    private int abilityDescriptionTable;
    private int attackAnimationTable;
    private int iconPals;
    private int jamboLearnableMovesTerm;
    private int startSearchingForSpaceOffset;
    private int freeSpaceSearchInterval;
    private int evolutionsPerPokeNumber;
    private int evolutionTypesNumber;

    // Should have a length of `evolutionTypesNumber`
    private String[] evolutionNames;
    private EvolutionParam[] evolutionParams;

    private int eggMoveTable;
    private int eggMoveTableLimiter;
    private int habitatTable;
    private int itemAnimationTable;
    private int trainerTable;
    private int trainersNumber;
    private int trainerClasses;
    private int trainerClassesNumber;
    private int trainerImageTable;
    private int trainerImagesNumber;
    private int trainerPaletteTable;
    private int trainerMoneyTable;
    private int dexSizeTrainerSprite;
    private int tradeData;
    private int tradesNumber;
    private int pokedexAlphabetTable;
    private int pokedexLightestTable;
    private int pokedexSmallestTable;
    private int pokedexTypeTable;

    public ROMData(Wini store, String header) {
        DataLoader loader = new DataLoader(store, header);

        romName = loader.getString("ROMName");
        itemData = loader.getPointer("ItemData");
        attackNames = loader.getPointer("AttackNames");
        tmData = loader.getPointer("TMData");
        totalTMsAndHMs = loader.getInt("TotalTMsPlusHMs");
        totalTMs = loader.getInt("TotalTMs");
        itemImgData = loader.getPointer("ItemIMGData");
        itemsNumber = loader.getInt("NumberOfItems");
        attacksNumber = loader.getInt("NumberOfAttacks");
        moveTutorAttacks = loader.getPointer("MoveTutorAttacks");
        moveTutorAttacksNumber = loader.getInt("NumberOfMoveTutorAttacks");
        pokemonNames = loader.getPointer("PokemonNames");
        pokemonsNumber = loader.getInt("NumberOfPokemon");
        nationalDexTable = loader.getPointer("NationalDexTable");
        secondDexTable = loader.getPointer("SecondDexTable");
        pokedexData = loader.getPointer("PokedexData");
        dexEntriesNumber = loader.getInt("NumberOfDexEntries");
        regionDexEntriesNumber = loader.getInt("NumberOfRegionDex");
        pokemonData = loader.getPointer("PokemonData") + 28;
        abilityNames = loader.getPointer("AbilityNames");
        abilitiesNumber = loader.getInt("NumberOfAbilities");

        pointerOfMapBanksPointer = loader.getPointer("Pointer2PointersToMapBanks");

        bankPointers = new int[BANKS];
        mapsInBanksNumber = new int[BANKS];

        for (int i = 0; i < BANKS; i++) {
            bankPointers[i] = loader.getPointer("OriginalBankPointer" + i);
            mapsInBanksNumber[i] = loader.getInt("NumberOfMapsInBank" + i);
        }

        mapLabelData = loader.getPointer("MapLabelData");
        mapLabelsNumber = loader.getInt("NumberOfMapLabels");

        // TODO Load tiles in tileset

        pokemonFrontSprites = loader.getPointer("PokemonFrontSprites");
        pokemonBackSprites = loader.getPointer("PokemonBackSprites");
        pokemonNormalPal = loader.getPointer("PokemonNormalPal");
        pokemonShinyPal = loader.getPointer("PokemonShinyPal");
        iconPointerTable = loader.getPointer("IconPointerTable");
        iconPalTable = loader.getPointer("IconPalTable");
        cryTable = loader.getPointer("CryTable");
        cryTable2 = loader.getPointer("CryTable2");
        cryCoversionTable = loader.getPointer("CryConversionTable");
        footPrintTable = loader.getPointer("FootPrintTable");
        pokemonAttackTable = loader.getPointer("PokemonAttackTable");
        pokemonEvolutions = loader.getPointer("PokemonEvolutions");
        tmhmcCompatibility = loader.getPointer("TMHMCompatibility");
        tmhmlLenPerPoke = loader.getInt("TMHMLenPerPoke");
        moveTutorCompatibility = loader.getPointer("MoveTutorCompatibility");
        enemyYTable = loader.getPointer("EnemyYTable");
        playerYTable = loader.getPointer("PlayerYTable");
        enemyAltitudeTable = loader.getPointer("EnemyAltitudeTable");
        attackData = loader.getPointer("AttackData");
        attackDescriptionTable = loader.getPointer("AttackDescriptionTable");
        abilityDescriptionTable = loader.getPointer("AbilityDescriptionTable");
        attackAnimationTable = loader.getPointer("AttackAnimationTable");
        iconPals = loader.getPointer("IconPals");
        jamboLearnableMovesTerm = loader.getPointer("JamboLearnableMovesTerm");
        startSearchingForSpaceOffset = loader.getPointer("StartSearchingForSpaceOffset");
        freeSpaceSearchInterval = loader.getInt("FreeSpaceSearchInterval");
        evolutionsPerPokeNumber = loader.getInt("NumberOfEvolutionsPerPokemon");
        evolutionTypesNumber = loader.getInt("NumberOfEvolutionTypes");
        evolutionParams = new EvolutionParam[evolutionTypesNumber];

        for (int i = 0; i < evolutionTypesNumber; i++) {
            String param = loader.getString("Evolution" + i + "Param");
            evolutionParams[i] = EvolutionParam.byConfig(param);
        }

        eggMoveTable = loader.getPointer("EggMoveTable");
        eggMoveTableLimiter = loader.getPointer("EggMoveTableLimiter");
        habitatTable = loader.getPointer("HabitatTable");
        itemAnimationTable = loader.getPointer("ItemAnimationTable");
        trainerTable = loader.getPointer("TrainerTable");
        trainersNumber = loader.getInt("NumberOfTrainers");
        trainerClasses = loader.getPointer("TrainerClasses");
        trainerClassesNumber = loader.getInt("NumberOfTrainerClasses");
        trainerImageTable = loader.getPointer("TrainerImageTable");
        trainerImagesNumber = loader.getInt("NumberOfTrainerImages");
        trainerPaletteTable = loader.getPointer("TrainerPaletteTable");
        trainerMoneyTable = loader.getPointer("TrainerMoneyTable");
        dexSizeTrainerSprite = loader.getInt("DexSizeTrainerSprite");
        tradeData = loader.getPointer("TradeData");
        tradesNumber = loader.getInt("NumberOfTrades");
        pokedexAlphabetTable = loader.getPointer("PokedexAlphabetTable");
        pokedexLightestTable = loader.getPointer("PokedexLightestTable");
        pokedexSmallestTable = loader.getPointer("PokedexSmallestTable");
        pokedexTypeTable = loader.getPointer("PokedexTypeTable");
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

    public ROMData(ROM rom) throws IOException {
        this(rom.getGameCode());
    }
}
