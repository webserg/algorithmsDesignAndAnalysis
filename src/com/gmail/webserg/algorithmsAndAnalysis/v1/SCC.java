package com.gmail.webserg.algorithmsAndAnalysis.v1;

import gnu.trove.list.array.TIntArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: webserg
 * Date: 27.02.13
 * VM option -Xmx2048m -Xss1024m
 */
public class SCC {
    private static Logger log = Logger.getLogger(KargerMinCut.class.getName());

    {
        log.setLevel(Level.SEVERE);
    }

    @Test
    public void testScc1() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{3, 3, 3, 0, 0}), Arrays.toString(sccCore.run(9, "resource/SCCTest1.txt")));
    }

    @Test
    public void testScc2() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{4, 3, 3, 1, 0}), Arrays.toString(sccCore.run(11, "resource/SCCTest2.txt")));
    }

    @Test
    public void testScc3() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{3, 3, 2, 0, 0}), Arrays.toString(sccCore.run(8, "resource/SCCTest3.txt")));
    }

    @Test
    public void testScc4() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{6, 3, 2, 1, 0}), Arrays.toString(sccCore.run(12, "resource/SCCTest4.txt")));
    }

    @Test
    public void testScc5() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{35, 7, 1, 1, 1}), Arrays.toString(sccCore.run(50, "resource/SCCTest5.txt")));
    }

    @Test
    public void testScc6() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{1, 1, 1, 0, 0}), Arrays.toString(sccCore.run(3, "resource/SCCTest6.txt")));
    }

    @Test
    public void testScc7() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{1, 1, 1, 0, 0}), Arrays.toString(sccCore.run(3, "resource/SCCTest7.txt")));
    }

    @Test
    public void testScc9() throws Exception {
        SCCCore sccCore = new SCCCore();
        Assert.assertEquals(Arrays.toString(new int[]{2, 1, 0, 0, 0}), Arrays.toString(sccCore.run(3, "resource/SCCTest9.txt")));
    }

    @Test
    public void testScc8() throws Exception {
        SCCCore sccCore = new SCCCore();
        long t1 = System.currentTimeMillis();
        System.out.println(Arrays.toString(sccCore.run(875714, "resource/com.gmail.webserg.algorithmsAndAnalysis.v1.SCC.txt")));
        System.out.println(System.currentTimeMillis() - t1);
//        Assert.assertEquals(Arrays.toString(new int[]{1, 1, 1, 0, 0}), Arrays.toString(sccCore.run(875714, "resource/com.gmail.webserg.algorithmsAndAnalysis.v1.SCC.txt")));
    }

    public static void main(String[] args) throws Exception {
        new SCC().testScc8();
    }

    class SCCCore {
        private int[] f;
        private int s = 0;
        private int t = -1;
        PriorityQueue<Integer> finalLeaderSize = new PriorityQueue<>(5, Collections.reverseOrder());
        private int leader = 0;

        public int[] run(int N, String fileName) throws Exception {
            finalLeaderSize.addAll(Arrays.asList(0, 0, 0, 0, 0));
            f = new int[N];

            GraphSCC initGraph = FilesHelper.readGraphSCCFromFile(fileName, N);
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    dfs(initGraph, i,false);
                }
            }
            initGraph.vertexVisit = new BitSet(N);
            initGraph.adjacencyListIn = new TIntArrayList[N];
            finishTime(initGraph);
            for (int i = N - 1; i >= 0; i--) {
                if (!initGraph.vertexVisit.get(i)) {
                    s = i;
                    leader = 0;
//                    log.info("s = " + s);
                    dfs(initGraph, i, true);
                    finalLeaderSize.offer(leader);
                }
            }
            initGraph.adjacencyListIn = new TIntArrayList[0];
            log.info(Arrays.toString(finalLeaderSize.toArray()));
            int[] res = new int[5];
            for (int i = 0; i < 5; i++) res[i] = finalLeaderSize.poll();
            return res;
        }


        private void dfs(GraphSCC graph, int i, boolean lead) {
            graph.vertexVisit.set(i);//mark i explored
            TIntArrayList arcs = graph.adjacencyListIn[i];
            if (lead) {
                leader++;
//                log.info("leader(" + i + ")=" + leader + " s=" + s);
            }
            if (arcs != null) {
                if (lead) arcs.sort();
                for (int j = 0; j < arcs.size(); j++) {
                    int arc = arcs.get(j);
                    log.info("arc=" + arc);
                    if (!lead) {
                        if (graph.adjacencyListOut[arc] == null) graph.adjacencyListOut[arc] = new TIntArrayList();
                        graph.adjacencyListOut[arc].add(i);
                    }
                    if (!graph.vertexVisit.get(arc)) {
                        dfs(graph, arc, lead);
                    }
                }
            }
            t++;
            f[i] = t;
//            log.info("f(" + i + ")=" + t);
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
            graph.adjacencyListOut =  new TIntArrayList[0];
        }

    }
}
