import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountryToFlagUtilTest {

    @Test
    public void toFlagEmoji() {
        CountryToFlagUtil emoji = new CountryToFlagUtil();
        // Test a few countries
        Assertions.assertEquals("🇳🇴", emoji.toFlagEmoji("NO"));
        Assertions.assertEquals("🇪🇸", emoji.toFlagEmoji("ES"));
        // Test special cases
        Assertions.assertEquals("🌐", emoji.toFlagEmoji("WWW"));
        Assertions.assertEquals("🏳", emoji.toFlagEmoji("**unknown**"));
    }
}
