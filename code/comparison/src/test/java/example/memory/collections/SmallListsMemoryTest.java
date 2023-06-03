package example.memory.collections;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.ArrayList;
import java.util.List;

public class SmallListsMemoryTest
{
    @Test
    public void jdkListOfToSizeEleven()
    {
        List<String>[] array = new List[]{
                List.of(),
                List.of(""),
                List.of("", ""),
                List.of("", "", ""),
                List.of("", "", "", ""),
                List.of("", "", "", "", ""),
                List.of("", "", "", "", "", ""),
                List.of("", "", "", "", "", "", ""),
                List.of("", "", "", "", "", "", "", ""),
                List.of("", "", "", "", "", "", "", "", ""),
                List.of("", "", "", "", "", "", "", "", "", ""),
                List.of("", "", "", "", "", "", "", "", "", "", "")
        };
        Assertions.assertEquals(776L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }

    @Test
    public void fixedSizeListsToSizeSix()
    {
        ArrayList arrayList = new ArrayList();
        System.out.println("ArrayList Empty: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 1: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 2: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 3: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 4: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 5: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList 6: " + GraphLayout.parseInstance(arrayList).totalSize());

        arrayList = new ArrayList(0);
        System.out.println("ArrayList 0: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList.add(null);
        System.out.println("ArrayList0 1: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList = new ArrayList(0);
        arrayList.add(null);
        arrayList.add(null);
        System.out.println("ArrayList0 2: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList = new ArrayList(0);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        System.out.println("ArrayList0 3: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList = new ArrayList(0);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        System.out.println("ArrayList0 4: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList = new ArrayList(0);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        System.out.println("ArrayList0 5: " + GraphLayout.parseInstance(arrayList).totalSize());
        arrayList = new ArrayList(0);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        arrayList.add(null);
        System.out.println("ArrayList0 6: " + GraphLayout.parseInstance(arrayList).totalSize());

        List list = Lists.fixedSize.empty();
        System.out.println("FixedSizeList Empty: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of((Object)null);
        System.out.println("FixedSizeList 1: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of(null, null);;
        System.out.println("FixedSizeList 2: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of(null, null, null);
        System.out.println("FixedSizeList 3: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of(null, null, null, null);
        System.out.println("FixedSizeList 4: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of(null, null, null, null, null);
        System.out.println("FixedSizeList 5: " + GraphLayout.parseInstance(list).totalSize());
        list = Lists.fixedSize.of(null, null, null, null, null, null);
        System.out.println("FixedSizeList 6: " + GraphLayout.parseInstance(list).totalSize());
    }

    @Test
    public void ecListOfToSizeEleven()
    {
        ImmutableList<String>[] array = new ImmutableList[]{
                Lists.immutable.of(),
                Lists.immutable.of(""),
                Lists.immutable.of("", ""),
                Lists.immutable.of("", "", ""),
                Lists.immutable.of("", "", "", ""),
                Lists.immutable.of("", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", "", "", "", "", ""),
                Lists.immutable.of("", "", "", "", "", "", "", "", "", "", "")
        };
        Assertions.assertEquals(496L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }
}
