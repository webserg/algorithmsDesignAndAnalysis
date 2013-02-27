import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 27.02.13
 */
public class SCC {
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());
    @Test
    public void testKargerMinCut2() throws Exception {
        SCCCore sccCore = new SCCCore();
        int[] scc = sccCore.run(9,"resource/SCCTest1.txt");
        Assert.assertEquals( Arrays.toString(new int[] {9,6,4}) , Arrays.toString(scc) );
    }
    class SCCCore {
        private int[] f;
        private TIntIntMap leader = new TIntIntHashMap();
        private int s = 0;
        private int t = -1;

        public int[] run(int N, String fileName) throws Exception {
            f = new int[N];

            GraphSCC initGraph = FilesHelper.readGraphSCCFromFile(fileName, N);
            for (int i = N; i <= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    dfs(initGraph,i);
                }
            }
            log.info(Arrays.toString(f));
            return new int[3];
        }

        private void dfs(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            leader.put(i, s);
            TIntArrayList arcs = graph.adjacencyList[i];
            for (int j = 0; j < arcs.size(); j++) {
                if (!graph.vertexVisit.get(arcs.get(j))) {
                    dfs(graph, arcs.get(j));
                }
            }
            t++;
            f[i] = t;
        }

        private void reverseArcs(GraphSCC graph){

        }
    }
}
