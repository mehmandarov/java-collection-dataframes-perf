package example.memory.collections;

import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.HashSet;
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

    @Test
    public void fixedSizeSetsToSizeFour()
    {
        HashSet hashSet = new HashSet();
        System.out.println("HashSet Empty: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet.add(new Object());
        System.out.println("HashSet 1: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet.add(new Object());
        System.out.println("HashSet 2: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet.add(new Object());
        System.out.println("HashSet 3: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet.add(new Object());
        System.out.println("HashSet 4: " +
                GraphLayout.parseInstance(hashSet).totalSize());

        hashSet = new HashSet(0);
        System.out.println("HashSet 0: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet.add(new Object());
        System.out.println("HashSet0 1: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet = new HashSet(0);
        hashSet.add(new Object());
        hashSet.add(new Object());
        System.out.println("HashSet0 2: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet = new HashSet(0);
        hashSet.add(new Object());
        hashSet.add(new Object());
        hashSet.add(new Object());
        System.out.println("HashSet0 3: " +
                GraphLayout.parseInstance(hashSet).totalSize());
        hashSet = new HashSet(0);
        hashSet.add(new Object());
        hashSet.add(new Object());
        hashSet.add(new Object());
        hashSet.add(new Object());
        System.out.println("HashSet0 4: " + GraphLayout.parseInstance(hashSet).totalSize());

        Set set = Sets.fixedSize.empty();
        System.out.println("FixedSizeSet Empty: " + GraphLayout.parseInstance(set).totalSize());
        set = Sets.fixedSize.of(new Object());
        System.out.println("FixedSizeSet 1: " +
                GraphLayout.parseInstance(set).totalSize());
        set = Sets.fixedSize.of(new Object(), new Object());;
        System.out.println("FixedSizeSet 2: " +
                GraphLayout.parseInstance(set).totalSize());
        set = Sets.fixedSize.of(new Object(), new Object(), new Object());
        System.out.println("FixedSizeSet 3: " +
                GraphLayout.parseInstance(set).totalSize());
        set = Sets.fixedSize.of(new Object(), new Object(), new Object(), new Object());
        System.out.println("FixedSizeSet 4: " +
                GraphLayout.parseInstance(set).totalSize());
    }
}
