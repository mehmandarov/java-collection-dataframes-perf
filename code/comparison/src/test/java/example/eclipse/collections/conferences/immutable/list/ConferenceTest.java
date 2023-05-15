package example.eclipse.collections.conferences.immutable.list;

import example.eclipse.collections.conferences.immutable.set.Conference;
import example.eclipse.collections.conferences.immutable.set.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ConferenceTest
{
    @Test
    public void createDevoxxGreece()
    {
        example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("Greece", "GR");
        example.eclipse.collections.conferences.immutable.set.Conference conference = new example.eclipse.collections.conferences.immutable.set.Conference("Devoxx Greece","Greece","Athens","2023-05-04","2023-05-06","[talks,workshops]");
        Assertions.assertTrue(conference.hasTalks());
        Assertions.assertTrue(conference.hasWorkshops());
        Assertions.assertEquals(example.eclipse.collections.conferences.immutable.set.Country.getByName("Greece"), conference.country());
        Assertions.assertEquals(3, conference.durationInDays());
        Assertions.assertEquals("🇬🇷", conference.countryFlag());
        Assertions.assertEquals("Greece", conference.countryName());
        long expectedDaysUntil = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(2023, 5, 4));
        Assertions.assertEquals(expectedDaysUntil, conference.daysToEvent());
    }

    @Test
    public void createJChampionsConf()
    {
        example.eclipse.collections.conferences.immutable.set.Country.newIfAbsent("WWW", "www");
        example.eclipse.collections.conferences.immutable.set.Conference conference = new Conference("jChampionsConf","WWW","Online event","2023-01-19","2023-01-24","[talks,]");
        Assertions.assertTrue(conference.hasTalks());
        Assertions.assertFalse(conference.hasWorkshops());
        Assertions.assertEquals(Country.getByName("WWW"), conference.country());
        Assertions.assertEquals(6, conference.durationInDays());
        Assertions.assertEquals("🌐", conference.countryFlag());
        Assertions.assertEquals("WWW", conference.countryName());
        long expectedDaysUntil = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.of(2023, 1, 19));
        Assertions.assertEquals(expectedDaysUntil, conference.daysToEvent());
    }
}
