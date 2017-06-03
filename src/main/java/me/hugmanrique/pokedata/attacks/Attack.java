package me.hugmanrique.pokedata.attacks;

import lombok.Getter;
import lombok.ToString;
import me.hugmanrique.pokedata.Data;
import me.hugmanrique.pokedata.loaders.ROMData;
import me.hugmanrique.pokedata.roms.ROM;
import me.hugmanrique.pokedata.utils.BitConverter;

import static me.hugmanrique.pokedata.utils.BitConverter.isBitSet;

/**
 * http://bulbapedia.bulbagarden.net/wiki/Move_data_structure_in_Generation_III
 * @author Hugmanrique
 * @since 30/04/2017
 */
@Getter
@ToString
public class Attack extends Data {
    private byte effect;
    private byte basePower;
    private byte type;
    private byte accuracy;
    private byte pp;
    private byte effectAccuracy;
    private Affects affects;
    private byte priority;
    private byte flags;

    public Attack(ROM rom) {
        effect = rom.readByte();
        basePower = rom.readByte();
        type = rom.readByte();
        accuracy = rom.readByte();
        pp = rom.readByte();
        effectAccuracy = rom.readByte();
        affects = Affects.byId(rom.readByte());
        priority = rom.readByte();
        flags = rom.readByte();
        rom.addInternalOffset(3);
    }

    public boolean makesContact() {
        return isBitSet(flags, 5);
    }

    public boolean affectedByProtect() {
        return isBitSet(flags, 4);
    }

    public boolean affectedByMagicCoat() {
        return isBitSet(flags, 3);
    }

    public boolean affectedBySnatch() {
        return isBitSet(flags, 2);
    }

    public boolean usedWithMirror() {
        return isBitSet(flags, 1);
    }

    /**
     * The flinch effect is considered an additional effect for the purposes of Shield Dust, but not Serene Grace.
     */
    public boolean isAffectedByKingRock() {
        return isBitSet(flags, 0);
    }

    public static Attack load(ROM rom, ROMData data, int id) {
        int offset = data.getAttackData() + (id * 12);
        rom.seek(offset);

        return new Attack(rom);
    }
}
