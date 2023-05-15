package example.memory.conferences;

import org.openjdk.jol.info.GraphLayout;

public class ConferenceExplorerMemoryTest
{
    public static void main(String[] args)
    {
        var dataframeEcCE = new example.dataframeec.conferences.ConferenceExplorer(2023);
        var ecImmutableSetCE = new example.eclipse.collections.conferences.immutable.set.ConferenceExplorer(2023);
        var ecImmutableListCE = new example.eclipse.collections.conferences.immutable.list.ConferenceExplorer(2023);
        var nativeJavaImmutableSetCE = new example.nativejava.conferences.immutable.set.ConferenceExplorer(2023);

        System.out.println(GraphLayout.parseInstance(dataframeEcCE).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecImmutableSetCE).toFootprint());
        System.out.println(GraphLayout.parseInstance(ecImmutableListCE).toFootprint());
        System.out.println(GraphLayout.parseInstance(nativeJavaImmutableSetCE).toFootprint());
    }
}
