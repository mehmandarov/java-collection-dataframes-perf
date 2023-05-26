package example.nativejava.conferences.immutable.set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.Pools;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public record Conference(
        String eventName,
        Country country,
        String city,
        LocalDate startDate,
        LocalDate endDate,
        Set<SessionType> sessionTypes,
        int trackCount,
        int sessionCount,
        int speakerCount,
        int cost)
{
    public Conference(String eventName,
                      String country,
                      String city,
                      String startDate,
                      String endDate,
                      String sessionTypes,
                      int trackCount,
                      int sessionCount,
                      int speakerCount,
                      int cost)
    {
        this(eventName,
                Country.getByName(country),
                Pools.poolString(city),
                Pools.poolDate(LocalDate.parse(startDate)),
                Pools.poolDate(LocalDate.parse(endDate)),
                Pools.poolSet(SessionType.setFromString(sessionTypes)),
                trackCount,
                sessionCount,
                speakerCount,
                cost);
    }

    public long durationInDays()
    {
        return ChronoUnit.DAYS.between(this.startDate, this.endDate.plusDays(1L));
    }

    public long daysToEvent()
    {
        return ChronoUnit.DAYS.between(LocalDate.now(), this.startDate);
    }

    @JsonIgnore
    public boolean hasWorkshops()
    {
        return this.sessionTypes.contains(SessionType.WORKSHOP);
    }

    @JsonIgnore
    public boolean hasTalks()
    {
        return this.sessionTypes.contains(SessionType.TALK);
    }

    public String countryFlag()
    {
        return this.country.flag();
    }

    public String countryName()
    {
        return this.country.name();
    }

    @JsonIgnore
    public Month getMonth()
    {
        return this.startDate.getMonth();
    }
}
