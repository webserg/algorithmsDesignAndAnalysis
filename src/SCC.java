import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 27.02.13
 */
public class SCC {
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());
    {
        log.setLevel(Level.SEVERE);
    }
    @Test
    public void testScc1() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{3, 3, 3,0,0}), Arrays.toString( sccCore.run(9, "resource/SCCTest1.txt")));
    }

    @Test
    public void testScc2() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{4, 3, 3,1,0}), Arrays.toString(sccCore.run(11, "resource/SCCTest2.txt")));
    }

    @Test
    public void testScc3() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{3,3,2,0,0}), Arrays.toString(sccCore.run(8, "resource/SCCTest3.txt")));
    }

    @Test
    public void testScc4() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{6,3,2,1,0}), Arrays.toString(sccCore.run(12, "resource/SCCTest4.txt")));
    }

    @Test
    public void testScc5() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{35, 7, 1, 1, 1}), Arrays.toString(sccCore.run(50, "resource/SCCTest5.txt")));
    }

    @Test
    public void testScc6() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{ 1,1,1,0,0}), Arrays.toString(sccCore.run(3, "resource/SCCTest6.txt")));
    }

    @Test
    public void testScc7() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{ 1,1,1,0,0}), Arrays.toString(sccCore.run(3, "resource/SCCTest7.txt")));
    }

    @Test
    public void testScc9() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{ 2,1,0,0,0}), Arrays.toString(sccCore.run(3, "resource/SCCTest9.txt")));
    }

//    @Test
    public void testScc8() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{ 1,1,1,0,0}), Arrays.toString(sccCore.run(875714, "resource/SCC.txt")));
    }

    public static void main(String[] args) throws Exception{
         new SCC().testScc8();
    }

    class SCCCore {
        private int[] f;
//        private TIntArrayList leader;
        private int s = 0;
        private int t = -1;
        PriorityQueue<Integer> finalLeaderSize = new PriorityQueue<>(5, Collections.reverseOrder());
        private int leader = 0;
        public int[] run(int N, String fileName) throws Exception {
            finalLeaderSize.addAll(Arrays.asList(new Integer[]{ 0,0,0,0,0})) ;
            f = new int[N];

            GraphSCC initGraph = FilesHelper.readGraphSCCFromFile(fileName, N);
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
//                    s = i;
//                    log.info("s=" + s);
                    dfs(initGraph, i);
                }
            }
            initGraph.vertexVisit = new BitSet(N);
            initGraph.adjacencyListIn = (TIntArrayList[]) new TIntArrayList[N];
            finishTime(initGraph);
            //  f = new int[0];
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    leader=0;
                    log.info("s = " + s);
                    dfsLeader(initGraph, i);
                   // if(finalLeaderSize.peek())
                    finalLeaderSize.offer(leader);
                }
            }
            initGraph.adjacencyListIn = (TIntArrayList[]) new TIntArrayList[0];
            log.info(Arrays.toString(finalLeaderSize.toArray()));
            int[] res = new int[5];
            for(int i=0;i<5;i++) res[i] = finalLeaderSize.poll();
            return res;
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
                    if (graph.adjacencyListOut[arc] == null) graph.adjacencyListOut[arc] = new TIntArrayList();
                    graph.adjacencyListOut[arc].add(i);

                    if (!graph.vertexVisit.get(arc)) {
                        dfs(graph, arc);
                    }
                }
            t++;
            f[i] = t;
            log.info("f("+i+")=" + t);
        }


        private void dfsLeader(GraphSCC graph, int i) {
            graph.vertexVisit.set(i);//mark i explored
            leader++;
            log.info("leader(" + i + ")=" + leader + " s=" + s);
            TIntArrayList arcs = graph.adjacencyListIn[i];
            arcs.sort();
            if (arcs != null) {
                for (int j = 0; j < arcs.size(); j++) {
                    int arc = arcs.get(j);
                    if (!graph.vertexVisit.get(arc)) {
                        dfsLeader(graph, arc);
                    }
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
                graph.adjacencyListIn[f[i]] = edgesFs;
            }
             graph.adjacencyListOut = (TIntArrayList[]) new TIntArrayList[0];
        }

        private void reverseArcs(GraphSCC graph) {

        }
    }
}
