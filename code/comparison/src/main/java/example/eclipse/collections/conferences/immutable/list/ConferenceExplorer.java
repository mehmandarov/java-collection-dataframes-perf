package example.eclipse.collections.conferences.immutable.list;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.eclipsecollections.EclipseCollectionsModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.set.Pool;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.utility.LazyIterate;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class ConferenceExplorer
{
    private ImmutableList<Conference> conferences;
    private ImmutableList<Country> countries;

    private static String DATA_CONFERENCES_CSV = "data/conferences.csv";
    public static void setCSVSize(int size)
    {
        if (size > 0)
        {
            DATA_CONFERENCES_CSV = "data/conferences_" + size + ".csv";
        }
        else
        {
            DATA_CONFERENCES_CSV = "data/conferences.csv";
        }
    }

    public ConferenceExplorer(int year)
    {
        this(yearPredicate(year));
    }

    public ConferenceExplorer(Predicate<Conference> initialFilter)
    {
        this.loadCountriesFromCsv();
        this.loadConferencesFromCsv(initialFilter);
    }

    private static Predicate<Conference> yearPredicate(int year)
    {
        return conference -> conference.startDate().getYear() == year;
    }

    private void loadConferencesFromCsv(Predicate<Conference> initialFilter)
    {
        CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();
        URL url = ConferenceExplorer.class.getClassLoader()
                .getResource(DATA_CONFERENCES_CSV);
        final CsvMapper mapper = new CsvMapper();
        try (
                MappingIterator<Map<String, String>> it = mapper
                        .readerForMapOf(String.class)
                        .with(headerSchema)
                        .readValues(url))
        {
            this.createConferencesFromList(initialFilter, it.readAll());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void createConferencesFromList(Predicate<Conference> initialFilter, List<Map<String, String>> lists)
    {
        this.conferences = LazyIterate.collect(lists, this::createConference)
                .select(initialFilter)
                .toImmutableList();
    }

    private Conference createConference(Map<String, String> map)
    {
        return new Conference(
                map.get("EventName"),
                map.get("Country"),
                map.get("City"),
                map.get("StartDate"),
                map.get("EndDate"),
                map.get("SessionTypes"),
                Integer.parseInt(map.get("TrackCount")),
                Integer.parseInt(map.get("SessionCount")),
                Integer.parseInt(map.get("SpeakerCount")),
                Integer.parseInt(map.get("Cost")));
    }

    private void loadCountriesFromCsv()
    {
        CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();
        URL url = ConferenceExplorer.class.getClassLoader().getResource("data/country_codes.csv");
        final CsvMapper mapper = new CsvMapper();
        try (
                MappingIterator<Map<String, String>> it = mapper
                        .readerForMapOf(String.class)
                        .with(headerSchema)
                        .readValues(url))
        {
            List<Map<String, String>> lists = it.readAll();
            this.countries = LazyIterate.collect(lists, this::createCountry)
                    .toImmutableList();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private Country createCountry(Map<String, String> map)
    {
        return Country.newIfAbsent(
                map.get("Country"),
                map.get("Alpha2Code")
        );
    }

    public ImmutableList<Conference> getConferences()
    {
        return this.conferences;
    }

    public ImmutableListMultimap<Country, Conference> groupByCountry()
    {
        return this.conferences.groupBy(Conference::country);
    }

    public ImmutableListMultimap<String, Conference> groupByCity()
    {
        return this.conferences.groupBy(Conference::city);
    }

    public ImmutableList<Conference> sortByDaysToEvent()
    {
        return this.conferences.toImmutableSortedListBy(Conference::daysToEvent);
    }

    public ImmutableListMultimap<SessionType, Conference> groupBySessionType()
    {
        return this.conferences.groupByEach(Conference::sessionTypes);
    }

    public ImmutableList<Country> getCountries()
    {
        return this.conferences.collect(Conference::country);
    }

    public Bag<Country> countByCountry()
    {
        return this.conferences.countBy(Conference::country);
    }

    public Bag<Month> countByMonth()
    {
        return this.conferences.countBy(Conference::getMonth);
    }

    public Bag<SessionType> countBySessionType()
    {
        return this.conferences.countByEach(Conference::sessionTypes);
    }

    public ObjectLongMap<Country> sumConferenceDaysByCountry()
    {
        return this.conferences.sumByLong(Conference::country, Conference::durationInDays);
    }

    private ObjectMapper getObjectMapper()
    {
        return new ObjectMapper()
                .registerModule(new EclipseCollectionsModule())
                .registerModule(new JavaTimeModule());
    }

    public String outputToJson(Object result)
    {
        try
        {
            return this.getObjectMapper().writeValueAsString(switch (result)
                    {
                        case Bag<?> bag -> bag.toMapOfItemToCount();
                        default -> result;
                    });
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
