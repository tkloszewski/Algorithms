package com.smart.tkl.graph.standard;

import java.util.List;

public class ShortestPathTester {

    private final static int[][] graph1 = {
            {0, 1, 5},
            {1, 2, 20},
            {2, 3, 10},
            {3, 2, -15},
            {2, 4, 75},
            {1, 6, 60},
            {1, 5, 30},
            {5, 4, 25},
            {4, 9, 100},
            {5, 8, 50},
            {5, 6, 5},
            {6, 7, -50},
            {7, 8, -10}
    };

    public static void main(String[] args) {
        testGraph1();
    }

    private static void testGraph1() {
        DirectedGraphBuilder directedGraphBuilder = new DirectedGraphBuilder();
        for(int[] edge : graph1) {
           directedGraphBuilder.addEdge(edge[0], edge[1], edge[2]);
        }
        DirectedGraph graph = directedGraphBuilder.build();
        ShortestPathFinder pathFinder = new BFShortestPathFinder(graph);

        StandardVertex source = new StandardVertex(0);
        StandardVertex dest1 = new StandardVertex(8);

        List<StandardVertex> negativeInfinityVertices = List.of(new StandardVertex(2), new StandardVertex(3),
                new StandardVertex(4), new StandardVertex(9));

        StandardPath path = pathFinder.find(source, dest1);
        System.out.println("BF shortest path between 0 and 8: " + path);

        for(StandardVertex negativeInfinityVertex : negativeInfinityVertices) {
           path = pathFinder.find(source, negativeInfinityVertex);
           System.out.println("Negative Infinity path: " + path);
        }

    }
}
