package example.memory.collections;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.GraphLayout;

import java.util.Map;

public class SmallMapsMemoryTest
{
    @Test
    public void jdkMapOfToSizeFive()
    {
        Map<String, String>[] array = new Map[]{
                Map.of(),
                Map.of("1", "1"),
                Map.of("1", "1", "2", "2"),
                Map.of("1", "1", "2", "2", "3", "3"),
                Map.of("1", "1", "2", "2", "3", "3", "4", "4"),
                Map.of("1", "1", "2", "2", "3", "3", "4", "4", "5", "5"),
        };
        Assertions.assertEquals(736L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }

    @Test
    public void ecMapOfToSizeFive()
    {
        ImmutableMap<String, String>[] array = new ImmutableMap[]{
                Maps.immutable.of(),
                Maps.immutable.of("1", "1"),
                Maps.immutable.of("1", "1", "2", "2"),
                Maps.immutable.of("1", "1", "2", "2", "3", "3"),
                Maps.immutable.of("1", "1", "2", "2", "3", "3", "4", "4"),
                Maps.immutable.of("1", "1", "2", "2", "3", "3", "4", "4").newWithKeyValue("5", "5"),
        };
        Assertions.assertEquals(592L, GraphLayout.parseInstance(array).totalSize());
        System.out.println(GraphLayout.parseInstance(array).toFootprint());
    }
}
