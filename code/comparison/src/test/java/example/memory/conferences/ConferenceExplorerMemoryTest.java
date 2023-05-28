package example.memory.conferences;

import example.Pools;
import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import org.openjdk.jol.info.GraphLayout;

import java.text.NumberFormat;

public class ConferenceExplorerMemoryTest
{
    public static void main(String[] args)
    {
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};

        Pools.enablePooling();
        for (int size : sizes)
        {
            SyntheticDataGenerator.generateSyntheticData(size, "conferences");
            NumberFormat format = NumberFormat.getIntegerInstance();
            System.out.println("Size: " + format.format(size));
            System.out.println("==============");
            example.dataframeec.conferences.ConferenceExplorer.setCSVSize(size);
            example.eclipse.collections.conferences.immutable.set.ConferenceExplorer.setCSVSize(size);
            example.eclipse.collections.conferences.immutable.list.ConferenceExplorer.setCSVSize(size);
            example.nativejava.conferences.immutable.set.ConferenceExplorer.setCSVSize(size);
            example.nativejava.conferences.immutable.list.ConferenceExplorer.setCSVSize(size);

            var dataframeEcCE = new example.dataframeec.conferences.ConferenceExplorer(2023);
            var ecImmutableSetCE = new example.eclipse.collections.conferences.immutable.set.ConferenceExplorer(2023);
            var ecImmutableListCE = new example.eclipse.collections.conferences.immutable.list.ConferenceExplorer(2023);
            var nativeJavaImmutableSetCE = new example.nativejava.conferences.immutable.set.ConferenceExplorer(2023);
            var nativeJavaImmutableListCE = new example.nativejava.conferences.immutable.list.ConferenceExplorer(2023);

            System.out.println(GraphLayout.parseInstance(dataframeEcCE).toFootprint());
            System.out.println(GraphLayout.parseInstance(ecImmutableSetCE).toFootprint());
            System.out.println(GraphLayout.parseInstance(ecImmutableListCE).toFootprint());
            System.out.println(GraphLayout.parseInstance(nativeJavaImmutableSetCE).toFootprint());
            System.out.println(GraphLayout.parseInstance(nativeJavaImmutableListCE).toFootprint());
        }
    }
}
