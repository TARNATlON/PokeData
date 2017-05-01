# PokeData
Pokémon data classes from Gen III that can read from a ROM.

## ROM reader and writer
PokeData provides a [ROM](src/main/java/me/hugmanrique/pokedata/utils/ROM.java) which contains all the ROM reading and writing methods.

You can make your ROM readable only by leaving the `writeByte(byte value, int offset)` method empty.

TODO: Explain PokeText table loading

## Data reference:
All the classes contain an URL to Bulbapedia if available, where the data values are explain in more depth.

- Items: Item metadata
- Moves: Move metadata
- Pokédex: details about a Pokémon species
- Pokémon: basic data of a captured Pokémon
- PokemonData: battle data of a captured Pokémon 