import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.List;
import java.util.Map;

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
import org.eclipse.collections.api.multimap.set.ImmutableSetMultimap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.utility.LazyIterate;

public class ConferenceExplorer
{
    private ImmutableSet<Conference> conferences;
    private ImmutableSet<Country> countries;

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
                .getResource("data/conferences.csv");
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
                .toImmutableSet();
    }

    private Conference createConference(Map<String, String> map)
    {
        return new Conference(
                map.get("Event Name"),
                map.get("Country"),
                map.get("City"),
                map.get("Start Date"),
                map.get("End Date"),
                map.get("Session Types"));
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
                    .toImmutableSet();
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

    public ImmutableSet<Conference> getConferences()
    {
        return this.conferences;
    }

    public ImmutableSetMultimap<Country, Conference> groupByCountry()
    {
        return this.conferences.groupBy(Conference::country);
    }

    public ImmutableSetMultimap<String, Conference> groupByCity()
    {
        return this.conferences.groupBy(Conference::city);
    }

    public ImmutableList<Conference> sortByDaysToEvent()
    {
        return this.conferences.toImmutableSortedListBy(Conference::daysToEvent);
    }

    public ImmutableSetMultimap<SessionType, Conference> groupBySessionType()
    {
        return this.conferences.groupByEach(Conference::sessionTypes);
    }

    public ImmutableSet<Country> getCountries()
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
