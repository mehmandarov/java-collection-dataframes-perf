package example.memory.boxing;

import org.eclipse.collections.api.factory.primitive.LongObjectMaps;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.set.primitive.MutableLongSet;
import org.eclipse.collections.impl.list.primitive.LongInterval;
import org.openjdk.jol.info.GraphLayout;

import java.util.*;

public class MapOfLongToSetOfLongTest
{
    public static void main(String[] args)
    {
        PrimitiveIterator.OfLong random1 = new Random(0L).longs(10_000L, 2_000_000L).iterator();
        Map<Long, Set<Long>> map = new HashMap<>();
        LongInterval lim = LongInterval.oneTo(200_000L);
        LongInterval lis = LongInterval.oneTo(10);
        lim.forEach(each -> map.put(each, lis.collect(l -> random1.next(), new HashSet<>())));
        System.out.println(GraphLayout.parseInstance(map).toFootprint());

        PrimitiveIterator.OfLong random2 = new Random(0L).longs(10_000L, 2_000_000L).iterator();
        MutableLongObjectMap<MutableLongSet> pMap = LongObjectMaps.mutable.empty();
        lim.forEach(each -> pMap.put(each, lis.asLazy().collectLong(l -> random2.next()).toSet()));
        System.out.println(GraphLayout.parseInstance(pMap).toFootprint());
    }
}
