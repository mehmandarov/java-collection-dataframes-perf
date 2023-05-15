package example.eclipse.collections.conferences.immutable.list;

import example.eclipse.collections.conferences.immutable.set.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountryTest
{
    @Test
    public void getFlag()
    {
        Assertions.assertEquals("🇺🇸", example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("United States", "US").flag());
        Assertions.assertEquals("🇺🇸", example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("USA", "US").flag());
        Assertions.assertEquals("🇺🇸", example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("usa", "us").flag());
        Assertions.assertEquals("🇬🇧", example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("UK", "GB").flag());
    }

    @Test
    public void caseInsensitiveLookup()
    {
        Assertions.assertSame(example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("USA", "US"), example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("usa", "us"));
        Assertions.assertNotSame(example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("USA", "US"), Country.newIfAbsent("United States", "us"));
    }
}
