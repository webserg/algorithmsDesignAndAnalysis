import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
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
        Assert.assertEquals(Arrays.toString(new int[]{9, 6, 4}), Arrays.toString(scc));
    }
    class SCCCore {
        private int[] f;
        private TIntArrayList leader = new TIntArrayList();
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
            initGraph.vertexVisit = new BitSet(N);
            finishTime(initGraph);
            f = new int[0];
            for (int i = N; i <= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    dfsLeader(initGraph, i);
                }
            }
            initGraph.adjacencyListIn =  (TIntArrayList[]) new TIntArrayList[0];
            TIntSet finalLeader  = new TIntHashSet(leader);

            log.info(Arrays.toString(f));
            return new int[3];
        }

        private void dfs(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            TIntArrayList arcs = graph.adjacencyListIn[i];
            for (int j = 0; j < arcs.size(); j++) {
                if (!graph.vertexVisit.get(arcs.get(j))) {
                    int arc = arcs.removeAt(j);
                    if(graph.adjacencyListOut[arc] == null)  graph.adjacencyListOut[arc] = new TIntArrayList();
                    graph.adjacencyListOut[arc].add(i);
                    dfs(graph, arc);
                }
            }
            t++;
            f[i] = t;
        }


        private void dfsLeader(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            leader.add(s);
            TIntArrayList arcs = graph.adjacencyListIn[i];
            for (int j = 0; j < arcs.size(); j++) {
                if (!graph.vertexVisit.get(arcs.get(j))) {
                    int arc = arcs.removeAt(j);
                    dfsLeader(graph, arc);
                }
            }
        }

        private void finishTime(GraphSCC graph) {
            for (int i = 0; i < graph.adjacencyListOut.length; i++) {
                TIntArrayList edges = graph.adjacencyListOut[i];
                TIntArrayList edgesFs = new TIntArrayList(edges.size());
                for (int j = 0; j < edges.size(); j++) {
                    edgesFs.add(f[edges.get(j)]);
                }
                graph.adjacencyListOut[i] = null;
            }
            graph.adjacencyListOut = (TIntArrayList[]) new TIntArrayList[0];
        }

        private void reverseArcs(GraphSCC graph){

        }
    }
}
