package com.smart.tkl.graph.standard;

import com.smart.tkl.graph.standard.cycle.CycleDetector;
import com.smart.tkl.graph.standard.cycle.GraphCycle;
import com.smart.tkl.graph.standard.visit.BFSVisitor;
import com.smart.tkl.graph.standard.visit.DFSVisitor;
import com.smart.tkl.graph.standard.visit.GraphVisitor;
import com.smart.tkl.graph.standard.visit.VisitorResult;

import java.util.List;
import java.util.Optional;

public class ShortestPathTester {

    private final static int[][] graph1 = {
            {0, 1, 5},
            {1, 2, 20},
            {2, 3, 10},
            {3, 10, -15},
            {10, 11, 15},
            {11, 12, 15},
            {12, 13, 30},
            {13, 2, 0},
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

    private final static Object[][] graph2 = {
            {"A", "B", 3},
            {"A", "C", 5},
            {"B", "G", 4},
            {"B", "D", 8},
            {"C", "D", 7},
            {"G", "F", 10},
            {"G", "D", 2},
            {"D", "F", 5},
            {"D", "E", 8},
            {"F", "E", 1}
    };

    private final static Object[][] graph3 = {
            {"s", "a", 8},
            {"s", "c", 7},
            {"a", "d", 0},
            {"b", "a", 0},
            {"b", "e", 3},
            {"c", "s", 1},
            {"c", "d", 4},
            {"d", "b", 1},
            {"d", "h", 2},
            {"f", "g", 0},
            {"g", "c", 0},
            {"g", "d", 1},
            {"h", "e", 1},
            {"h", "g", 2}
    };

    public static void main(String[] args) {
        testBF();
        //testDijkstra();
        testDijkstraAndReachability(graph3, "s");
    }


    private static void testDijkstraAndReachability(Object[][] graphData, String sourceId) {
        DirectedGraphBuilder graphBuilder = new DirectedGraphBuilder();
        for(Object[] edge : graphData) {
            graphBuilder.addEdge((String)edge[0], (String)edge[1], (Integer)edge[2]);
        }
        DirectedGraph graph = graphBuilder.build();
        StandardVertex source = new StandardVertex(sourceId);
        for(StandardVertex vertex : graph.getVertices()) {
            printShortestPath(graph, source, vertex);
        }
        BFSVisitor visitor = new BFSVisitor(graph);
        VisitorResult visitorResult = visitor.visitFrom(source);
        System.out.println("BFS result: " + visitorResult);
    }

    private static void printShortestPath(DirectedGraph graph, StandardVertex source, StandardVertex dest) {
        ShortestPathFinder pathFinder = new DijkstraShortestPathFinder(graph);
        StandardPath path = pathFinder.find(source, dest);
        System.out.printf("Shortest Dijkstra Path for graph2 between %s and %s is %s: \n", source.getId(), dest.getId(), path);

    }

    private static void testDijkstra() {
        DirectedGraphBuilder directedGraphBuilder = new DirectedGraphBuilder();
        for(int[] edge : graph1) {
            directedGraphBuilder.addEdge(edge[0], edge[1], edge[2]);
        }
        DirectedGraph graph = directedGraphBuilder.build();
        ShortestPathFinder pathFinder = new DijkstraShortestPathFinder(graph);
        StandardVertex source = new StandardVertex(0);
        StandardVertex dest = new StandardVertex(8);
        StandardPath path = pathFinder.find(source, dest);
        System.out.println("Shortest Dijkstra Path for graph1: " + path);

        directedGraphBuilder = new DirectedGraphBuilder();
        for(Object[] edge : graph2) {
            directedGraphBuilder.addEdge((String)edge[0], (String)edge[1], (Integer)edge[2]);
        }
        graph = directedGraphBuilder.build();
        pathFinder = new DijkstraShortestPathFinder(graph);
        path = pathFinder.find(new StandardVertex("A"), new StandardVertex("E"));
        System.out.println("Shortest Dijkstra Path for graph2: " + path);
    }

    private static void testBF() {
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

        path = pathFinder.find(new StandardVertex(9), source);
        System.out.println("Path to source: " + path);

        GraphVisitor visitor = new DFSVisitor(graph);
        VisitorResult visitorResult = visitor.visitFrom(source);
        System.out.println("Visitor result: " + visitorResult);

        CycleDetector cycleDetector = new CycleDetector(graph);
        Optional<GraphCycle> cycleOpt = cycleDetector.detectCycle();
        System.out.println("Cycle: " + cycleOpt);
    }
}
