package example.memory.boxing;

import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.ImmutableIntBag;
import org.eclipse.collections.api.bag.primitive.MutableIntBag;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableIntSet;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.utility.StringIterate;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.text.NumberFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BoxingCollectionsTest
{
    @Test
    public void toFootprintSets()
    {
        HashSet<Integer> jdkBoxedSet = new HashSet<>(Interval.oneTo(10));
        MutableIntSet ecPrimitiveSet = IntInterval.oneTo(10).toSet();

        System.out.println(GraphLayout.parseInstance(jdkBoxedSet).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecPrimitiveSet).toFootprint());
    }

    @Test
    public void toFootprintLists()
    {
        List<Integer> jdkBoxedList = new ArrayList<>(Interval.oneTo(10));
        MutableIntList ecPrimitiveList = IntInterval.oneTo(10).toList();

        System.out.println(GraphLayout.parseInstance(jdkBoxedList).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecPrimitiveList).toFootprint());
    }

    public static void main(String[] args)
    {
        boxingImmutableCollections(1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000);
        boxingMutableCollections(1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000);
    }

    private static void boxingImmutableCollections(int... sizes)
    {
        boxingImmutableLists(sizes);
        System.out.println("-".repeat(50));
        boxingImmutableSets(sizes);
        System.out.println("-".repeat(50));
        boxingImmutableBags(sizes);
    }

    private static void boxingMutableCollections(int... sizes)
    {
        boxingMutableLists(sizes);
        System.out.println("-".repeat(50));
        boxingMutableSets(sizes);
        System.out.println("-".repeat(50));
        boxingMutableBags(sizes);
    }

    private static void boxingImmutableBags(int[] sizes)
    {
        String title = "Boxing Immutable Bags - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            immutablePrimitiveBags(size);
        }
    }

    private static void boxingMutableBags(int[] sizes)
    {
        String title = "Boxing Mutable Bags - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            mutablePrimitiveBags(size);
        }
    }

    private static void boxingImmutableSets(int[] sizes)
    {
        String title = "Boxing Immutable Sets - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            immutablePrimitiveSets(size);
        }
    }

    private static void boxingMutableSets(int[] sizes)
    {
        String title = "Boxing Mutable Sets - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            mutablePrimitiveSets(size);
        }
    }

    private static void boxingImmutableLists(int[] sizes)
    {
        String title = "Boxing Immutable Lists - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            immutablePrimitiveLists(size);
        }
    }

    private static void boxingMutableLists(int[] sizes)
    {
        String title = "Boxing Mutable Lists - Sizes: " + Arrays.toString(sizes);
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        for (int size : sizes)
        {
            mutablePrimitiveLists(size);
        }
    }

    private static void immutablePrimitiveLists(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        List<Integer> immutableJdkList = List.copyOf(Interval.oneTo(size));
        System.out.println(StringIterate.padOrTrim("List<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableJdkList).totalSize()));

        ImmutableList<Integer> immutableECList = Interval.oneTo(size).toImmutableList();
        System.out.println(StringIterate.padOrTrim("ImmutableList<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECList).totalSize()));

        ImmutableIntList immutableECPrimitiveList = IntInterval.oneTo(size).toList().toImmutable();
        System.out.println(StringIterate.padOrTrim("ImmutableIntList " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECPrimitiveList).totalSize()));
    }

    private static void mutablePrimitiveLists(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        List<Integer> mutableJdkList = new ArrayList<>(Interval.oneTo(size));
        System.out.println(StringIterate.padOrTrim("List<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableJdkList).totalSize()));

        MutableList<Integer> mutableECList = Interval.oneTo(size).toList();
        System.out.println(StringIterate.padOrTrim("MutableList<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableECList).totalSize()));

        MutableIntList mutableECPrimitiveList = IntInterval.oneTo(size).toList();
        System.out.println(StringIterate.padOrTrim("MutableIntList " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableECPrimitiveList).totalSize()));
    }

    private static void immutablePrimitiveSets(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        Interval interval = Interval.oneTo(size);
        Set<Integer> immutableJdkSet = Set.copyOf(interval);
        System.out.println(StringIterate.padOrTrim("Set<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableJdkSet).totalSize()));

        ImmutableSet<Integer> immutableECSet = interval.toImmutableSet();
        System.out.println(StringIterate.padOrTrim("ImmutableSet<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECSet).totalSize()));

        ImmutableIntSet immutableECPrimitiveSet = IntInterval.oneTo(size).toSet().toImmutable();
        System.out.println(StringIterate.padOrTrim("ImmutableIntSet " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECPrimitiveSet).totalSize()));
    }

    private static void mutablePrimitiveSets(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        Interval interval = Interval.oneTo(size);
        Set<Integer> mutableJdkSet = new HashSet<>(interval);
        System.out.println(StringIterate.padOrTrim("Set<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableJdkSet).totalSize()));

        MutableSet<Integer> immutableECSet = interval.toSet();
        System.out.println(StringIterate.padOrTrim("MutableSet<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECSet).totalSize()));

        MutableIntSet mutableECPrimitiveSet = IntInterval.oneTo(size).toSet();
        System.out.println(StringIterate.padOrTrim("MutableIntSet " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableECPrimitiveSet).totalSize()));
    }

    private static void immutablePrimitiveBags(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        Map<Integer, Long> immutableJdkBag = Map.copyOf(Interval.oneTo(size).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        System.out.println(StringIterate.padOrTrim("Map<Integer, Long> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableJdkBag).totalSize()));

        ImmutableBag<Integer> immutableECBag = Interval.oneTo(size).toImmutableBag();
        System.out.println(StringIterate.padOrTrim("ImmutableBag<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECBag).totalSize()));

        ImmutableIntBag immutableECPrimitiveBag = IntInterval.oneTo(size).toBag().toImmutable();
        System.out.println(StringIterate.padOrTrim("ImmutableIntBag " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECPrimitiveBag).totalSize()));
    }

    private static void mutablePrimitiveBags(int size)
    {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String sizeString = format.format(size);
        Interval interval = Interval.oneTo(size);
        Map<Integer, Long> mutableJdkBag = interval.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(StringIterate.padOrTrim("Map<Integer, Long> " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableJdkBag).totalSize()));

        MutableBag<Integer> immutableECBag = interval.toBag();
        System.out.println(StringIterate.padOrTrim("MutableBag<Integer> " + sizeString, 40) + format.format(GraphLayout.parseInstance(immutableECBag).totalSize()));

        MutableIntBag mutableECPrimitiveBag = IntInterval.oneTo(size).toBag();
        System.out.println(StringIterate.padOrTrim("MutableIntBag " + sizeString, 40) + format.format(GraphLayout.parseInstance(mutableECPrimitiveBag).totalSize()));
    }
}
