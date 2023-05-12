import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ConferenceExplorer
{
    private Set<Conference> conferences;
    private Set<Country> countries;

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
        this.conferences = lists.stream()
                .map(this::createConference)
                .filter(initialFilter)
                .collect(Collectors.toUnmodifiableSet());
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
            this.countries = lists.stream()
                    .map(this::createCountry)
                    .collect(Collectors.toUnmodifiableSet());
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

    public Set<Conference> getConferences()
    {
        return this.conferences;
    }

    public Map<Country, Set<Conference>> groupByCountry()
    {
        return this.conferences.stream()
                .collect(Collectors.groupingBy(
                        Conference::country,
                        Collectors.toUnmodifiableSet()));
    }

    public Map<String, Set<Conference>> groupByCity()
    {
        return this.conferences.stream()
                .collect(Collectors.groupingBy(
                        Conference::city,
                        Collectors.toUnmodifiableSet()));
    }

    public List<Conference> sortByDaysToEvent()
    {
        return this.conferences.stream()
                .sorted(Comparator.comparing(Conference::daysToEvent))
                .toList();
    }

    public Map<SessionType, Set<Conference>> groupBySessionType()
    {
        return Map.copyOf(this.conferences.stream()
                .flatMap(conference -> conference.sessionTypes().stream()
                        .map(sessionType -> new AbstractMap.SimpleEntry<>(sessionType, conference)))
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toUnmodifiableSet()))));
    }

    public Set<Country> getCountries()
    {
        return this.conferences.stream()
                .map(Conference::country)
                .collect(Collectors.toUnmodifiableSet());
    }

    public Map<Country, Long> countByCountry()
    {
        return this.conferences.stream()
                .collect(Collectors.groupingBy(
                        Conference::country,
                        Collectors.counting()));
    }

    public Map<Month, Long> countByMonth()
    {
        return this.conferences.stream()
                .collect(Collectors.groupingBy(
                        Conference::getMonth,
                        Collectors.counting()));
    }

    public Map<SessionType, Long> countBySessionType()
    {
        return this.conferences.stream()
                .flatMap(conference -> conference.sessionTypes().stream()
                        .map(sessionType -> new AbstractMap.SimpleEntry<>(sessionType, conference)))
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.counting()));
    }

    public Map<Country, Long> sumConferenceDaysByCountry()
    {
        return this.conferences.stream().
                collect(Collectors.groupingBy(
                        Conference::country,
                        Collectors.summingLong(Conference::durationInDays)));
    }

    private ObjectMapper getObjectMapper()
    {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public String outputToJson(Object result)
    {
        try
        {
            return this.getObjectMapper().writeValueAsString(result);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
