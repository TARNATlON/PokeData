package me.hugmanrique.pokedata.pokemon;

/**
 * @author Hugmanrique
 * @since 28/05/2017
 */
public enum Gender {
    MALE,
    MIXED,
    FEMALE,
    GENDERLESS;

    public static Gender byValue(byte value) {
        switch (value) {
            case 0:
                return MALE;
            case (byte) 254:
                return FEMALE;
            case (byte) 255:
                return GENDERLESS;
            default:
                return MIXED;
        }
    }
}
