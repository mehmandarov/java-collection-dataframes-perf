package example;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.Pool;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Pools
{
    private static boolean POOLING_ENABLED = false;
    private static Pool<String> STRING_POOL = new UnifiedSet<>();
    private static Pool<LocalDate> DATE_POOL = new UnifiedSet<>();
    private static Pool<ImmutableSet<?>> IMMUTABLE_SET_POOL = new UnifiedSet<>();
    private static Pool<ImmutableList<?>> IMMUTABLE_LIST_POOL = new UnifiedSet<>();
    private static Pool<Set<?>> SET_POOL = new UnifiedSet<>();
    private static Pool<List<?>> LIST_POOL = new UnifiedSet<>();
    private static Pool<Twin<?>> TWIN_POOL = new UnifiedSet<>();
    private static Pool<Pair<?, ?>> PAIR_POOL = new UnifiedSet<>();

    public static void enablePooling()
    {
        POOLING_ENABLED = true;
    }

    public static void disablePooling()
    {
        POOLING_ENABLED = false;
    }

    public static void clearPools()
    {
        STRING_POOL.clear();
        DATE_POOL.clear();
        IMMUTABLE_SET_POOL.clear();
        IMMUTABLE_LIST_POOL.clear();
        SET_POOL.clear();
        LIST_POOL.clear();
        TWIN_POOL.clear();
        PAIR_POOL.clear();
    }

    public static String poolString(String value)
    {
        if (POOLING_ENABLED)
        {
            return STRING_POOL.put(value);
        }
        return value;
    }

    public static LocalDate poolDate(LocalDate value)
    {
        if (POOLING_ENABLED)
        {
            return DATE_POOL.put(value);
        }
        return value;
    }

    public static <R extends ImmutableSet<?>> R poolImmutableSet(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) IMMUTABLE_SET_POOL.put(value);
        }
        return value;
    }

    public static <R extends ImmutableList<?>> R poolImmutableList(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) IMMUTABLE_LIST_POOL.put(value);
        }
        return value;
    }

    public static <R extends Set<?>> R poolSet(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) SET_POOL.put(value);
        }
        return value;
    }

    public static <R extends List<?>> R poolList(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) LIST_POOL.put(value);
        }
        return value;
    }

    public static <R extends Twin<?>> R poolTwin(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) TWIN_POOL.put(value);
        }
        return value;
    }

    public static <R extends Pair<?, ?>> R poolPair(R value)
    {
        if (POOLING_ENABLED)
        {
            return (R) PAIR_POOL.put(value);
        }
        return value;
    }
}
