import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SessionType
{
    TALK("Talks"), WORKSHOP("Workshops");

    private final String name;

    SessionType(String name)
    {
        this.name = name;
    }

    public static SessionType fromString(String sessionType)
    {
        return Stream.of(SessionType.values())
                .filter(type -> sessionType.equalsIgnoreCase(type.name))
                .findFirst()
                .orElse(null);
    }

    public static Set<SessionType> setFromString(String sessionTypes)
    {
        String csv = sessionTypes.replace("[", "").replace("]", "");
        String[] types = csv.split(",");
        return Stream.of(types)
                .map(SessionType::fromString)
                .collect(Collectors.toUnmodifiableSet());
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
