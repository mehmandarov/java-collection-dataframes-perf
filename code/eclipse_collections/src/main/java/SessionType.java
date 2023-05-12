import org.eclipse.collections.api.factory.primitive.CharSets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableCharSet;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.factory.Strings;
import org.eclipse.collections.impl.utility.ArrayIterate;

public enum SessionType
{
    TALK("Talks"), WORKSHOP("Workshops");

    private static final ImmutableCharSet BRACKETS = CharSets.immutable.with('[', ']');
    private final String name;

    SessionType(String name)
    {
        this.name = name;
    }

    public static SessionType fromString(String sessionType)
    {
        return ArrayIterate.detect(SessionType.values(), type -> sessionType.equalsIgnoreCase(type.name));
    }

    public static ImmutableSet<SessionType> setFromString(String sessionTypes)
    {
        String csv = Strings.asChars(sessionTypes).reject(BRACKETS::contains).toString();
        String[] types = csv.split(",");
        return ArrayIterate.collect(types, SessionType::fromString)
                .reject(Predicates.isNull())
                .toImmutableSet();
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
