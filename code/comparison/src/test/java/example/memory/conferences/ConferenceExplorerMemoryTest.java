package example.memory.conferences;

import example.Pools;
import org.openjdk.jol.info.GraphLayout;

import java.text.NumberFormat;

public class ConferenceExplorerMemoryTest
{
    public static void main(String[] args)
    {
        int[] sizes = {1_000_000};

        Pools.disablePooling();
        for (int size : sizes)
        {
            SyntheticDataGenerator.generateSyntheticData(size, "conferences");
            NumberFormat format = NumberFormat.getIntegerInstance();
            System.out.println("Size: " + format.format(size));
            System.out.println("==============");

            graphDataFrameEC(size);
            graphMutableSetEC(size);
            graphImmutableSetEC(size);
            graphMutableListEC(size);
            graphImmutableListEC(size);
            graphImmutableSetJava(size);
            graphImmutableListJava(size);
        }
    }

    private static void graphImmutableListJava(int size)
    {
        Pools.clearPools();
        example.nativejava.conferences.immutable.list.ConferenceExplorer.setCSVSize(size);
        var nativeJavaImmutableListCE = new example.nativejava.conferences.immutable.list.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(nativeJavaImmutableListCE).toFootprint());
    }

    private static void graphImmutableSetJava(int size)
    {
        Pools.clearPools();
        example.nativejava.conferences.immutable.set.ConferenceExplorer.setCSVSize(size);
        var nativeJavaImmutableSetCE = new example.nativejava.conferences.immutable.set.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(nativeJavaImmutableSetCE).toFootprint());
    }

    private static void graphImmutableListEC(int size)
    {
        Pools.clearPools();
        example.eclipse.collections.conferences.immutable.list.ConferenceExplorer.setCSVSize(size);
        var ecImmutableListCE = new example.eclipse.collections.conferences.immutable.list.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(ecImmutableListCE).toFootprint());
    }

    private static void graphMutableListEC(int size)
    {
        Pools.clearPools();
        example.eclipse.collections.conferences.mutable.list.ConferenceExplorer.setCSVSize(size);
        var ecMutableListCE = new example.eclipse.collections.conferences.mutable.list.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(ecMutableListCE).toFootprint());
    }

    private static void graphImmutableSetEC(int size)
    {
        Pools.clearPools();
        example.eclipse.collections.conferences.immutable.set.ConferenceExplorer.setCSVSize(size);
        var ecImmutableSetCE = new example.eclipse.collections.conferences.immutable.set.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(ecImmutableSetCE).toFootprint());
    }

    private static void graphMutableSetEC(int size)
    {
        Pools.clearPools();
        example.eclipse.collections.conferences.mutable.set.ConferenceExplorer.setCSVSize(size);
        var ecMutableSetCE = new example.eclipse.collections.conferences.mutable.set.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(ecMutableSetCE).toFootprint());
    }

    private static void graphDataFrameEC(int size)
    {
        Pools.clearPools();
        example.dataframeec.conferences.ConferenceExplorer.setCSVSize(size);
        var dataframeEcCE = new example.dataframeec.conferences.ConferenceExplorer(2023);
        System.out.println(GraphLayout.parseInstance(dataframeEcCE).toFootprint());
    }
}
