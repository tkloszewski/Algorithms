package com.smart.tkl.graph.standard;

import com.smart.tkl.graph.standard.connect.GraphConnectivityDetector;

public class ConnectedGraphTester {

    private final static Object[][] graph1 = {
            {"A", "B", 3},
            {"A", "C", 5},
            {"B", "G", 4},
            {"B", "D", 8},
            {"C", "D", 7},
            {"G", "F", 10},
            {"G", "D", 2},
            {"D", "F", 5},
            {"D", "E", 8},
            {"F", "E", 1},
            {"H", "I", 1},
            {"I", "K", 2},
            {"K", "A", 0}
    };

    private final static Object[][] stronglyConnectedGraph = {
            {"A", "B", 1},
            {"A", "D", 1},
            {"B", "A", 1},
            {"B", "C", 1},
            {"D", "C", 1},
            {"C", "B", 1}
    };

    public static void main(String[] args) {
        testConnection(graph1);
        testConnection(stronglyConnectedGraph);
    }

    private static void testConnection(Object[][] graphData) {
        DirectedGraphBuilder graphBuilder = new DirectedGraphBuilder();
        for(Object[] edge : graphData) {
            graphBuilder.addEdge((String)edge[0], (String)edge[1], (Integer)edge[2]);
        }
        DirectedGraph graph = graphBuilder.build();
        GraphConnectivityDetector connectedGraphDetector = new GraphConnectivityDetector(graph);
        System.out.println("Connectivity: " + connectedGraphDetector.checkConnectivity());
    }
}
