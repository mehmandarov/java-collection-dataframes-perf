import java.util.Map;

import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dataframe.util.DataFrameCompare;
import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

public class ConferenceExplorerTest
{
    @Test
    public void loadConferences()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Assertions.assertEquals(7, explorer.getConferences().rowCount());
    }

    @Test
    public void sortByDaysToEvent()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame conferences = explorer.sortByDaysToEvent();
        String closestEvent = conferences.getString("EventName", 0);
        Assertions.assertEquals("jChampionsConf", closestEvent);
        String furthestEvent = conferences.getString("EventName", conferences.rowCount() - 1);
        Assertions.assertEquals("Devoxx Greece", furthestEvent);
    }

    @Test
    public void countByMonth()
    {
        DataFrame expected = new DataFrame("Expected")
                .addStringColumn("Month").addLongColumn("MonthCount")
                .addRow("JANUARY", 1L)
                .addRow("FEBRUARY", 1L)
                .addRow("MARCH", 2L)
                .addRow("APRIL", 2L)
                .addRow("MAY", 1L);

        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame countByMonth = explorer.countByMonth();
        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expected, countByMonth));
    }

    @Test
    public void countByCountry()
    {
        DataFrame expected = new DataFrame("Expected")
                .addStringColumn("Country").addLongColumn("CountryCount")
                .addRow("Sweden", 1L)
                .addRow("United States", 1L)
                .addRow("Romania", 1L)
                .addRow("Germany", 1L)
                .addRow("Greece", 1L)
                .addRow("WWW", 1L)
                .addRow("Poland", 1L);

        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame countByCountry = explorer.countByCountry();
        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expected, countByCountry));
    }

    @Test
    public void sumConferenceDaysByCountry()
    {
        DataFrame expected = new DataFrame("Expected")
                .addStringColumn("Country").addLongColumn("Duration")
                .addRow("Sweden", 3L)
                .addRow("United States", 3L)
                .addRow("Romania", 3L)
                .addRow("Germany", 3L)
                .addRow("Greece", 3L)
                .addRow("WWW", 6L)
                .addRow("Poland", 3L);

        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame sumByCountry = explorer.sumConferenceDaysByCountry();
        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expected, sumByCountry));
    }

    @Test
    public void groupByCountry()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        explorer.groupByCountry()
                .iterateAt("Greece")
                .forEach(c -> Assertions.assertEquals("Athens", c.getString("City")));
    }

    @Test
    public void groupByCity()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        explorer.groupByCity()
                .iterateAt("Athens")
                .forEach(c -> Assertions.assertEquals("Greece", c.getString("Country")));
    }

    @Test
    public void getCountries()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame countries = explorer.getCountries();

        DataFrame expectedCountries = new DataFrame("Countries")
                .addStringColumn("Country").addStringColumn("Alpha2Code").addStringColumn("Flag")
                .addRow("Sweden", "SE", "🇸🇪")
                .addRow("United States", "US", "🇺🇸")
                .addRow("Romania", "RO", "🇷🇴")
                .addRow("Germany", "DE", "🇩🇪")
                .addRow("Greece", "GR", "🇬🇷")
                .addRow("WWW", "www", "🌐")
                .addRow("Poland", "PL", "🇵🇱");

        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expectedCountries, countries));

        DataFrame flags = expectedCountries.distinct(Lists.immutable.of("Flag"));
        DataFrame expectedFlags = new DataFrame("Flags")
                .addStringColumn("Flag")
                .addRow("🇸🇪")
                .addRow("🇺🇸")
                .addRow("🇷🇴")
                .addRow("🇩🇪")
                .addRow("🇬🇷")
                .addRow("🌐")
                .addRow("🇵🇱");
        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expectedFlags, flags));
    }

    @Test
    public void groupBySessionTypes()
    {
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        Map<String, DataFrame> bySessionType = explorer.groupBySessionType();

        Assertions.assertEquals(7, bySessionType.get("talks").rowCount());
        Assertions.assertEquals(6, bySessionType.get("workshops").rowCount());

        Assertions.assertEquals(1, bySessionType.get("talks").selectBy("EventName == 'jChampionsConf'").rowCount());
        Assertions.assertEquals(0, bySessionType.get("workshops").selectBy("EventName == 'jChampionsConf'").rowCount());
    }

    @Test
    public void countBySessionType()
    {
        DataFrame expected = new DataFrame("Expected")
                .addStringColumn("Session Type").addLongColumn("Count")
                .addRow("talks", 7L)
                .addRow("workshops", 6L);
        ConferenceExplorer explorer = new ConferenceExplorer(2023);
        DataFrame countBySessionType = explorer.countBySessionType();
        Assertions.assertTrue(new DataFrameCompare().equalIgnoreOrder(expected, countBySessionType));
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
        System.out.println("DataFrame-EC size (bytes): " + GraphLayout.parseInstance(explorer).totalSize());
        System.out.println("DataFrame-EC: ");
        System.out.println(GraphLayout.parseInstance(explorer).toFootprint());
    }
}
