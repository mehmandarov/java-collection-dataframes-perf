package example.memory.collections;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.Set;

public class SmallSetsMemoryTest
{
    @Test
    public void jdkSetOfToSizeFive()
    {
        Set<String>[] array = new Set[]{
                Set.of(),
                Set.of("1"),
                Set.of("1", "2"),
                Set.of("1", "2", "3"),
                Set.of("1", "2", "3", "4"),
                Set.of("1", "2", "3", "4", "5"),
        };
        Assertions.assertEquals(560L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }

    @Test
    public void ecSetOfToSizeFive()
    {
        ImmutableSet<String>[] array = new ImmutableSet[]{
                Sets.immutable.of(),
                Sets.immutable.of("1"),
                Sets.immutable.of("1", "2"),
                Sets.immutable.of("1", "2", "3"),
                Sets.immutable.of("1", "2", "3", "4"),
                Sets.immutable.of("1", "2", "3", "4", "5"),
        };
        Assertions.assertEquals(448L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }
}
