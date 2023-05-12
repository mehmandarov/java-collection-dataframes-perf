import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record Country(String name, String code, String flag)
{
    private static Map<String, Integer> LETTER_TO_UNICODE = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("A", 0x1F1E6),
            new AbstractMap.SimpleEntry<>("B", 0x1F1E7),
            new AbstractMap.SimpleEntry<>("C", 0x1F1E8),
            new AbstractMap.SimpleEntry<>("D", 0x1F1E9),
            new AbstractMap.SimpleEntry<>("E", 0x1F1EA),
            new AbstractMap.SimpleEntry<>("F", 0x1F1EB),
            new AbstractMap.SimpleEntry<>("G", 0x1F1EC),
            new AbstractMap.SimpleEntry<>("H", 0x1F1ED),
            new AbstractMap.SimpleEntry<>("I", 0x1F1EE),
            new AbstractMap.SimpleEntry<>("J", 0x1F1EF),
            new AbstractMap.SimpleEntry<>("K", 0x1F1F0),
            new AbstractMap.SimpleEntry<>("L", 0x1F1F1),
            new AbstractMap.SimpleEntry<>("M", 0x1F1F2),
            new AbstractMap.SimpleEntry<>("N", 0x1F1F3),
            new AbstractMap.SimpleEntry<>("O", 0x1F1F4),
            new AbstractMap.SimpleEntry<>("P", 0x1F1F5),
            new AbstractMap.SimpleEntry<>("Q", 0x1F1F6),
            new AbstractMap.SimpleEntry<>("R", 0x1F1F7),
            new AbstractMap.SimpleEntry<>("S", 0x1F1F8),
            new AbstractMap.SimpleEntry<>("T", 0x1F1F9),
            new AbstractMap.SimpleEntry<>("U", 0x1F1FA),
            new AbstractMap.SimpleEntry<>("V", 0x1F1FB),
            new AbstractMap.SimpleEntry<>("W", 0x1F1FC),
            new AbstractMap.SimpleEntry<>("X", 0x1F1FD),
            new AbstractMap.SimpleEntry<>("Y", 0x1F1FE),
            new AbstractMap.SimpleEntry<>("Z", 0x1F1FF),
            new AbstractMap.SimpleEntry<>("WWW", 0x1F310),
            new AbstractMap.SimpleEntry<>("**UNKNOWN**", 0x1F3F3));

    private static Map<String, Country> COUNTRY_BY_NAME = new ConcurrentHashMap<>();

    public Country(String name, String code)
    {
        this(name, code, Country.flagFromCode(code));
        COUNTRY_BY_NAME.putIfAbsent(name.toUpperCase(), this);
    }

    public static Country newIfAbsent(String name, String code)
    {
        Country country = COUNTRY_BY_NAME.get(name.toUpperCase());
        if (country == null)
        {
            return new Country(name, code);
        }
        return country;
    }

    public static Country getByName(String name)
    {
        return COUNTRY_BY_NAME.get(name.toUpperCase());
    }

    private static String flagFromCode(String countryCode)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        if (countryCode.equalsIgnoreCase("www") || countryCode.equalsIgnoreCase("**unknown**"))
        {
            final Integer intCode = LETTER_TO_UNICODE.get(countryCode.toUpperCase());
            for (Character character : Character.toChars(intCode))
            {
                stringBuilder.append(character);
            }
            return stringBuilder.toString();
        }
        for (char letter : countryCode.toCharArray())
        {
            final Integer intCode = LETTER_TO_UNICODE.get((letter + "").toUpperCase());
            for (Character character : Character.toChars(intCode))
            {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}
