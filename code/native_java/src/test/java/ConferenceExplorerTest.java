import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

public class ConferenceExplorerTest
{
    @Test
    public void loadConferences()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Assertions.assertEquals(7, explorer.getConferences().size());
    }

    @Test
    public void groupByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<Country, Set<Conference>> byCountry = explorer.groupByCountry();
        Set<Conference> conferences = byCountry.get(Country.getByName("GREECE"));
        Assertions.assertEquals(1, conferences.size());
        Assertions.assertEquals("Athens", conferences.iterator().next().city());
    }

    @Test
    public void groupByCity()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<String, Set<Conference>> byCountry = explorer.groupByCity();
        Set<Conference> conferences = byCountry.get("Athens");
        Assertions.assertEquals(1, conferences.size());
        Assertions.assertEquals(Country.getByName("GREECE"), conferences.iterator().next().country());
    }

    @Test
    public void sortByDaysToEvent()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        List<Conference> conferences = explorer.sortByDaysToEvent();
        Conference closestEvent = conferences.get(0);
        Assertions.assertEquals("jChampionsConf", closestEvent.eventName());
        Conference furthestEvent = conferences.get(conferences.size() - 1);
        Assertions.assertEquals("Devoxx Greece", furthestEvent.eventName());
    }

    @Test
    public void groupBySessionTypes()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<SessionType, Set<Conference>> bySessionType = explorer.groupBySessionType();
        Assertions.assertEquals(7, bySessionType.get(SessionType.TALK).size());
        Assertions.assertEquals(6, bySessionType.get(SessionType.WORKSHOP).size());
        Set<Conference> difference =
                bySessionType.get(SessionType.TALK).stream()
                        .filter(each -> !bySessionType.get(SessionType.WORKSHOP).contains(each))
                        .collect(Collectors.toSet());
        Assertions.assertEquals("jChampionsConf", difference.iterator().next().eventName());
    }

    @Test
    public void getCountries()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Set<String> flags = explorer.getCountries().stream()
                .map(Country::flag)
                .collect(Collectors.toSet());
        Set<String> expectedFlags = Set.of("🇬🇷", "🇵🇱", "🇺🇸", "🇩🇪", "🇷🇴", "🇸🇪", "🌐");
        Assertions.assertEquals(expectedFlags, flags);
    }

    @Test
    public void countByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<Country, Long> expected = new HashMap<>();
        expected.put(Country.getByName("Sweden"), 1L);
        expected.put(Country.getByName("United States"), 1L);
        expected.put(Country.getByName("Romania"), 1L);
        expected.put(Country.getByName("Germany"), 1L);
        expected.put(Country.getByName("Greece"), 1L);
        expected.put(Country.getByName("WWW"), 1L);
        expected.put(Country.getByName("Poland"), 1L);
        Assertions.assertEquals(expected, explorer.countByCountry());
    }

    @Test
    public void countBySessionType()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<SessionType, Long> expected = new HashMap<>();
        expected.put(SessionType.TALK, 7L);
        expected.put(SessionType.WORKSHOP, 6L);
        Assertions.assertEquals(expected, explorer.countBySessionType());
    }

    @Test
    public void countByMonth()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<Month, Long> expected = new HashMap<>();
        expected.put(Month.JANUARY, 1L);
        expected.put(Month.FEBRUARY, 1L);
        expected.put(Month.MARCH, 2L);
        expected.put(Month.APRIL, 2L);
        expected.put(Month.MAY, 1L);
        Assertions.assertEquals(expected, explorer.countByMonth());
    }

    @Test
    public void sumConferenceDaysByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<Country, Long> expected = new HashMap<>();
        expected.put(Country.getByName("Sweden"), 3L);
        expected.put(Country.getByName("United States"), 3L);
        expected.put(Country.getByName("Romania"), 3L);
        expected.put(Country.getByName("Greece"), 3L);
        expected.put(Country.getByName("Germany"), 3L);
        expected.put(Country.getByName("WWW"), 6L);
        expected.put(Country.getByName("Poland"), 3L);
        Assertions.assertEquals(expected, explorer.sumConferenceDaysByCountry());
    }

    @Test
    public void output()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        String s = explorer.outputToJson(explorer.getConferences());
        Assertions.assertNotNull(s);
        System.out.println(s);
        String s2 = explorer.outputToJson(explorer.countByMonth());
        System.out.println(s2);
        String s3 = explorer.outputToJson(explorer.getCountries());
        System.out.println(s3);
        String s4 = explorer.outputToJson(explorer.sumConferenceDaysByCountry());
        System.out.println(s4);
        String s5 = explorer.outputToJson(explorer.countBySessionType());
        System.out.println(s5);
    }

    public static void main(String[] args)
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        System.out.println("Native Java size (bytes): " + GraphLayout.parseInstance(explorer).totalSize());
        System.out.println("Native Java: ");
        System.out.println(GraphLayout.parseInstance(explorer).toFootprint());
    }
}
