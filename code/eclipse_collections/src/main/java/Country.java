import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.factory.Strings;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;
import org.eclipse.collections.impl.map.strategy.mutable.UnifiedMapWithHashingStrategy;

public record Country(String name, String code, String flag)
{
    private static ImmutableObjectIntMap<String> LETTER_TO_UNICODE = ObjectIntMaps.mutable.<String>empty()
            .withKeyValue("A", 0x1F1E6)
            .withKeyValue("B", 0x1F1E7)
            .withKeyValue("C", 0x1F1E8)
            .withKeyValue("D", 0x1F1E9)
            .withKeyValue("E", 0x1F1EA)
            .withKeyValue("F", 0x1F1EB)
            .withKeyValue("G", 0x1F1EC)
            .withKeyValue("H", 0x1F1ED)
            .withKeyValue("I", 0x1F1EE)
            .withKeyValue("J", 0x1F1EF)
            .withKeyValue("K", 0x1F1F0)
            .withKeyValue("L", 0x1F1F1)
            .withKeyValue("M", 0x1F1F2)
            .withKeyValue("N", 0x1F1F3)
            .withKeyValue("O", 0x1F1F4)
            .withKeyValue("P", 0x1F1F5)
            .withKeyValue("Q", 0x1F1F6)
            .withKeyValue("R", 0x1F1F7)
            .withKeyValue("S", 0x1F1F8)
            .withKeyValue("T", 0x1F1F9)
            .withKeyValue("U", 0x1F1FA)
            .withKeyValue("V", 0x1F1FB)
            .withKeyValue("W", 0x1F1FC)
            .withKeyValue("X", 0x1F1FD)
            .withKeyValue("Y", 0x1F1FE)
            .withKeyValue("Z", 0x1F1FF)
            .withKeyValue("WWW", 0x1F310)
            .withKeyValue("**UNKNOWN**", 0x1F3F3).toImmutable();

    private static MutableMap<String, Country> COUNTRY_BY_NAME =
            UnifiedMapWithHashingStrategy.<String, Country>newMap(HashingStrategies.fromFunction(String::toUpperCase)).asSynchronized();

    public Country(String name, String code)
    {
        this(name, code, Country.flagFromCode(code));
        COUNTRY_BY_NAME.putIfAbsent(name, this);
    }

    public static Country newIfAbsent(String name, String code)
    {
        return COUNTRY_BY_NAME.getIfAbsent(name, () -> new Country(name, code));
    }

    public static Country getByName(String name)
    {
        return COUNTRY_BY_NAME.get(name);
    }

    private static String flagFromCode(String countryCode)
    {
        if (countryCode.equalsIgnoreCase("www") || countryCode.equalsIgnoreCase("**unknown**"))
        {
            final int intCode = LETTER_TO_UNICODE.get(countryCode.toUpperCase());
            return Strings.toCodePoints(intCode).toString();
        }
        return Strings.asCodePoints(countryCode)
                .collectInt(i -> LETTER_TO_UNICODE.get(Strings.toCodePoints(i).toString()))
                .toString();
    }
}
