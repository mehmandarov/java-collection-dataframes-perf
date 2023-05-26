package example.nativejava.conferences.immutable.list;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SessionType
{
    TALK("Talks"), WORKSHOP("Workshops"), LIGHTNING_TALK("Lightning Talks");

    private final String name;

    SessionType(String name)
    {
        this.name = name;
    }

    public static SessionType fromString(String sessionType)
    {
        return Stream.of(SessionType.values())
                .filter(type -> sessionType.trim().equalsIgnoreCase(type.name))
                .findFirst()
                .orElse(null);
    }

    public static List<SessionType> listFromString(String sessionTypes)
    {
        String csv = sessionTypes.replace("[", "").replace("]", "");
        String[] types = csv.split(",");
        return Stream.of(types)
                .map(SessionType::fromString)
                .filter(Objects::nonNull)
                .toList();
    }

    public boolean isTalk()
    {
        return this == TALK;
    }

    public boolean isWorkshop()
    {
        return this == WORKSHOP;
    }
}
