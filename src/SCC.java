import gnu.trove.list.array.TIntArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 27.02.13
 */
public class SCC {
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());

    @Test
    public void testScc1() throws Exception {
        SCCCore sccCore = new SCCCore();
        Object[] scc = sccCore.run(9, "resource/SCCTest1.txt");
        Assert.assertEquals(Arrays.toString(new int[]{3, 3, 3}), Arrays.toString(scc));
    }

    @Test
    public void testScc2() throws Exception {
        SCCCore sccCore = new SCCCore();
        Object[] scc = sccCore.run(11, "resource/SCCTest2.txt");
        Assert.assertEquals(Arrays.toString(new int[]{4, 3, 3,1,0}), Arrays.toString(scc));
    }

    class SCCCore {
        private int[] f;
//        private TIntArrayList leader;
        private int s = 0;
        private int t = -1;
        PriorityQueue<Integer> finalLeaderSize = new PriorityQueue<>();
        private int leader = 0;
        public Object[] run(int N, String fileName) throws Exception {
            f = new int[N];

            GraphSCC initGraph = FilesHelper.readGraphSCCFromFile(fileName, N);
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    dfs(initGraph, i);
                }
            }
            initGraph.vertexVisit = new BitSet(N);
            finishTime(initGraph);
            //  f = new int[0];
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    leader=0;
                    dfsLeader(initGraph, i);
                    finalLeaderSize.offer(leader);
                }
            }
            initGraph.adjacencyListIn = (TIntArrayList[]) new TIntArrayList[0];
            log.info(Arrays.toString(finalLeaderSize.toArray()));
            return finalLeaderSize.toArray();
        }

//        private void addLeader(int leader) {
//
//            if (finalLeaderSize.containsKey(leader)) {
//                finalLeaderSize.put(leader, finalLeaderSize.get(leader) + 1);
//            } else {
//                finalLeaderSize.put(leader, 0);
//            }
//
//        }


        private void dfs(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            TIntArrayList arcs = graph.adjacencyListIn[i];
            if (arcs != null)
                for (int j = 0; j < arcs.size(); j++) {
                    int arc = arcs.get(j);
                    log.info("arc=" + arc);
                    if (!graph.vertexVisit.get(arc)) {
                        if (graph.adjacencyListOut[arc] == null) graph.adjacencyListOut[arc] = new TIntArrayList();
                        graph.adjacencyListOut[arc].add(i);
                        dfs(graph, arc);
                    }
                }
            t++;
            f[i] = t;
        }


        private void dfsLeader(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            leader++;
            TIntArrayList arcs = graph.adjacencyListIn[i];
            if (arcs != null)
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
                TIntArrayList edgesFs = new TIntArrayList(edges != null ? edges.size() : 0);
                if (edges != null)
                    for (int j = 0; j < edges.size(); j++) {
                        edgesFs.add(f[edges.get(j)]);
                    }
                 graph.adjacencyListOut[i] = null;
            }
             graph.adjacencyListOut = (TIntArrayList[]) new TIntArrayList[0];
        }

        private void reverseArcs(GraphSCC graph) {

        }
    }
}
