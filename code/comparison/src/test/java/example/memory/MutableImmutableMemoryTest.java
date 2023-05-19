package example.memory;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MutableImmutableMemoryTest
{
    @Test
    public void toFootprintJdkSets()
    {
        Set<Integer> jdkMutableSet = new HashSet<>();
        jdkMutableSet.add(1);
        jdkMutableSet.add(2);
        Set<Integer> jdkImmutableSet = Set.copyOf(jdkMutableSet);

        System.out.println(GraphLayout.parseInstance(jdkMutableSet).toFootprint());
        System.out.println(GraphLayout.parseInstance(jdkImmutableSet).toFootprint());
    }

    @Test
    public void toFootprintJdkLists()
    {
        List<Integer> jdkMutableList = new ArrayList<>();
        jdkMutableList.add(1);
        jdkMutableList.add(2);
        List<Integer> jdkImmutableList = List.copyOf(jdkMutableList);

        System.out.println(GraphLayout.parseInstance(jdkMutableList).toFootprint());
        System.out.println(GraphLayout.parseInstance(jdkImmutableList).toFootprint());
    }

    @Test
    public void toFootprintEcSets()
    {
        MutableSet<Integer> ecMutableSet = Sets.mutable.<Integer>empty().with(1).with(2);
        ImmutableSet<Integer> ecImmutableSets = ecMutableSet.toImmutable();

        System.out.println(GraphLayout.parseInstance(ecMutableSet).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecImmutableSets).toFootprint());
    }

    @Test
    public void toFootprintEcLists()
    {
        MutableList<Integer> ecMutableList = Lists.mutable.<Integer>empty().with(1).with(2);
        ImmutableList<Integer> ecImmutableList = ecMutableList.toImmutable();

        System.out.println(GraphLayout.parseInstance(ecMutableList).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecImmutableList).toFootprint());
    }
}

