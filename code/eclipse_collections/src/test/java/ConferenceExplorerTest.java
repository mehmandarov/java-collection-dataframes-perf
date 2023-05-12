import java.time.Month;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.primitive.ObjectLongMaps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.set.ImmutableSetMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
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
        Multimap<Country, Conference> byCountry = explorer.groupByCountry();
        RichIterable<Conference> conferences = byCountry.get(Country.getByName("Greece"));
        Assertions.assertEquals(1, conferences.size());
        Assertions.assertEquals("Athens", conferences.getFirst().city());
    }

    @Test
    public void groupByCity()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Multimap<String, Conference> byCountry = explorer.groupByCity();
        RichIterable<Conference> conferences = byCountry.get("Athens");
        Assertions.assertEquals(1, conferences.size());
        Assertions.assertEquals(Country.getByName("Greece"), conferences.getFirst().country());
    }

    @Test
    public void sortByDaysToEvent()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        ImmutableList<Conference> conferences = explorer.sortByDaysToEvent();
        Conference closestEvent = conferences.getFirst();
        Assertions.assertEquals("jChampionsConf", closestEvent.eventName());
        Conference furthestEvent = conferences.getLast();
        Assertions.assertEquals("Devoxx Greece", furthestEvent.eventName());
    }

    @Test
    public void groupBySessionTypes()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        ImmutableSetMultimap<SessionType, Conference> bySessionType = explorer.groupBySessionType();
        Assertions.assertEquals(7, bySessionType.get(SessionType.TALK).size());
        Assertions.assertEquals(6, bySessionType.get(SessionType.WORKSHOP).size());
        RichIterable<Conference> difference =
                bySessionType.get(SessionType.TALK).difference(bySessionType.get(SessionType.WORKSHOP));
        Assertions.assertEquals("jChampionsConf", difference.getFirst().eventName());
    }

    @Test
    public void getCountries()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        ImmutableSet<String> flags = explorer.getCountries().collect(Country::flag);
        ImmutableSet<String> expectedFlags = Sets.immutable.with("🇬🇷", "🇵🇱", "🇺🇸", "🇩🇪", "🇷🇴", "🇸🇪", "🌐");
        Assertions.assertEquals(expectedFlags, flags);
    }

    @Test
    public void countByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Bag<Country> expected = Bags.immutable.with(
                Country.getByName("Sweden"),
                Country.getByName("United States"),
                Country.getByName("Romania"),
                Country.getByName("Germany"),
                Country.getByName("Greece"),
                Country.getByName("WWW"),
                Country.getByName("Poland"));
        Assertions.assertEquals(expected, explorer.countByCountry());
    }

    @Test
    public void countBySessionType()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Bag<SessionType> expected = Bags.immutable.withOccurrences(
                SessionType.TALK, 7,
                SessionType.WORKSHOP, 6);
        Assertions.assertEquals(expected, explorer.countBySessionType());
    }

    @Test
    public void countByMonth()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        MutableBag<Month> expected = Bags.mutable.empty();
        expected.addOccurrences(Month.JANUARY, 1);
        expected.addOccurrences(Month.FEBRUARY, 1);
        expected.addOccurrences(Month.MARCH, 2);
        expected.addOccurrences(Month.APRIL, 2);
        expected.addOccurrences(Month.MAY, 1);
        Assertions.assertEquals(expected, explorer.countByMonth());
    }

    @Test
    public void sumConferenceDaysByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        ObjectLongMap<Country> expected = ObjectLongMaps.mutable.<Country>empty()
                .withKeyValue(Country.getByName("Sweden"), 3L)
                .withKeyValue(Country.getByName("United States"), 3L)
                .withKeyValue(Country.getByName("Romania"), 3L)
                .withKeyValue(Country.getByName("Greece"), 3L)
                .withKeyValue(Country.getByName("Germany"), 3L)
                .withKeyValue(Country.getByName("WWW"), 6L)
                .withKeyValue(Country.getByName("Poland"), 3L);
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
        System.out.println("Eclipse Collections size (bytes): " + GraphLayout.parseInstance(explorer).totalSize());
        System.out.println("Eclipse Collections: ");
        System.out.println(GraphLayout.parseInstance(explorer).toFootprint());
    }
}
