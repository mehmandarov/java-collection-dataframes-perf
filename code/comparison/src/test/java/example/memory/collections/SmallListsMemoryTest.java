package example.memory.collections;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

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
