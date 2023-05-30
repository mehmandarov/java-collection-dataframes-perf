package example.eclipse.collections.conferences.immutable.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.Pools;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.tuple.Tuples;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public record Conference(
        String eventName,
        Country country,
        String city,
        Twin<LocalDate> dates, // Replaced separate startDate and endDate LocalDate fields
        ImmutableList<SessionType> sessionTypes,
        byte trackCount,    // Replace int with byte
        short sessionCount, // Replace int with short
        short speakerCount, // Replace int with short
        short cost)         // Replace int with short
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
                Pools.poolTwin(Tuples.twin(Pools.poolDate(LocalDate.parse(startDate)), Pools.poolDate(LocalDate.parse(endDate)))),
                Pools.poolImmutableList(SessionType.listFromString(sessionTypes)),
                (byte) trackCount,
                (short) sessionCount,
                (short) speakerCount,
                (short) cost);
    }

    public long durationInDays()
    {
        return ChronoUnit.DAYS.between(this.dates.getOne(), this.dates.getTwo().plusDays(1L));
    }

    public long daysToEvent()
    {
        return ChronoUnit.DAYS.between(LocalDate.now(), this.dates.getOne());
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

    @JsonIgnore
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
        return this.dates.getOne().getMonth();
    }

    public LocalDate startDate()
    {
        return this.dates.getOne();
    }

    public LocalDate endDate()
    {
        return this.dates.getTwo();
    }
}
