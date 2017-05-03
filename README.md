# PokeData
Pokémon data classes from Gen III that can read from a ROM.

## ROM reader and writer
PokeData provides a [ROM](src/main/java/me/hugmanrique/pokedata/utils/ROM.java) interface which contains all the ROM reading and writing methods.

You can make your ROM readable-only by leaving the `writeByte(byte value, int offset)` method empty.

TODO: Explain PokeText table loading

## Data reference:
All the classes contain an URL to Bulbapedia if available, where the data values are explained in more depth.

- Items: Item metadata
- Moves: Move metadata
- Pokédex: details about a Pokémon species
- PokemonBaseStats: Base status of a Pokémon species
- Pokémon: basic data of a captured Pokémon
- PokemonData: battle data of a captured Pokémon
- Evolutions: Evolution metadata
- Types: Effectiveness of each type against others

### Map structures:
As the structure of a Map is complex, every section receives its own class:

- Map: base class for a map
- MapHeader: Map metadata
- ConnectionData: Connection pointers and list of them
- Connection: offset, borders, map destination...
- [Sprites](#sprites)

Special thanks to [@shinyquagsire23](https://github.com/shinyquagsire23/) for finding out all the data structures for this section.
These values were extracted from his project [MEH](https://github.com/shinyquagsire23/MEH), check it out!

### Sprites
PokeData has base classes for Sprites and a SpriteManager, which creates the sprite instances, here are the different types currently implemented:

- NPCs: trainer that have scripts (speeches, combats...)
- Signs: contains the pointer to the text pointer and metadata
- Triggers: also known as traps, calls scripts when passing over a tile
- Exits: move the player to other map locations, contains the Bank and the Map ID
- OverworldSprites: (Soon) raw images of the overworld sprites, offers methods to render into Java's Images