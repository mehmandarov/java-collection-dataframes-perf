package example.memory.conferences;

import org.openjdk.jol.info.GraphLayout;

public class ConferenceExplorerMemoryTest
{
    public static void main(String[] args)
    {
        var dataframeEcCE = new example.dataframeec.conferences.ConferenceExplorer(2023);
        var ecCE = new example.eclipse.collections.conferences.immutable.set.ConferenceExplorer(2023);
        var nativeJavaCE = new example.nativejava.conferences.immutable.set.ConferenceExplorer(2023);

        System.out.println(GraphLayout.parseInstance(dataframeEcCE).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecCE).toFootprint());
        System.out.println(GraphLayout.parseInstance(nativeJavaCE).toFootprint());
    }
}
