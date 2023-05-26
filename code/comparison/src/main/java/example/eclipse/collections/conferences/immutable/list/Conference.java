package example.eclipse.collections.conferences.immutable.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.Pool;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public record Conference(
        String eventName,
        Country country,
        String city,
        LocalDate startDate,
        LocalDate endDate,
        ImmutableList<SessionType> sessionTypes,
        int trackCount,
        int sessionCount,
        int speakerCount,
        int cost)
{
    private static Pool<LocalDate> DATE_POOL = new UnifiedSet<>();

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
                city,
                DATE_POOL.put(LocalDate.parse(startDate)),
                DATE_POOL.put(LocalDate.parse(endDate)),
                SessionType.listFromString(sessionTypes),
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
        return this.startDate.getMonth();
    }
}
