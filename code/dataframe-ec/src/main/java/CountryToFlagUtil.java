import org.eclipse.collections.api.map.primitive.ImmutableObjectIntMap;
import org.eclipse.collections.impl.factory.primitive.ObjectIntMaps;

public class CountryToFlagUtil {
    public static ImmutableObjectIntMap<String> letter2unicode = ObjectIntMaps.mutable.<String>empty()
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


    public String toFlagEmoji(String countryCode) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (countryCode.equalsIgnoreCase("www") || countryCode.equalsIgnoreCase("**unknown**")){
            final int intCode = letter2unicode.get(countryCode.toUpperCase());
            for(Character character : Character.toChars(intCode)) {
                stringBuilder.append(character);
            }
            return stringBuilder.toString();
        }
        for(char letter : countryCode.toCharArray()) {
            final int intCode = letter2unicode.get(letter+"");
            for(Character character : Character.toChars(intCode)) {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }
}
