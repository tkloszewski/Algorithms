package com.smart.tkl.graph.standard.sort;

import com.smart.tkl.graph.standard.ConnectedGraphBuilder;
import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardVertex;

import java.util.*;

public class BFSTopologicalSorting {

    private final static int[][] graphData = {
            {5, 0, 1},
            {5, 2, 1},
            {4, 0, 1},
            {4, 1, 1},
            {2, 3, 1},
            {3, 1, 1}
    };

    private final static int[][] graphData2 = {
            {4, 1, 1},
            {4, 2, 1},
            {4, 3, 1},
            {2, 0, 1},
            {2, 1, 1},
            {0, 3, 1},
            {0, 5, 1}
    };

    private final static int[][] graphData3 = {
            {4, 1, 1},
            {4, 2, 1},
            {4, 3, 1},
            {2, 1, 1},
            {0, 3, 1},
            {0, 5, 1}
    };

    public static void main(String[] args) {
        DirectedGraph graph = buildGraph(graphData);
        DAGSortResult dagSortResult = sort(graph);
        System.out.println("Sort result for graphData1: " + dagSortResult);
        graph = buildGraph(graphData2);
        dagSortResult = sort(graph);
        System.out.println("Sort result for graphData2: " + dagSortResult);
        graph = buildGraph(graphData3);
        dagSortResult = sort(graph);
        System.out.println("Sort result for graphData3: " + dagSortResult);
    }

    public static DAGSortResult sort(DirectedGraph graph) {
        final Map<StandardVertex, Integer> inDegreeMap = new HashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            inDegreeMap.put(vertex, 0);
        }
        for(StandardVertex vertex : graph.getVertices()) {
            for(StandardVertex adjVertex : graph.getAdjacentVertices(vertex)) {
                inDegreeMap.put(adjVertex, inDegreeMap.get(adjVertex) + 1);
            }
        }
        Queue<StandardVertex> queue = new LinkedList<>();
        for(Map.Entry<StandardVertex, Integer> entry : inDegreeMap.entrySet()) {
            if(entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<StandardVertex> sorted = new LinkedList<>();
        while (!queue.isEmpty()) {
            StandardVertex head = queue.poll();
            sorted.add(head);
            for(StandardVertex adjVertex : graph.getAdjacentVertices(head)) {
                Integer inDegreeCnt = inDegreeMap.get(adjVertex);
                inDegreeMap.put(adjVertex, inDegreeCnt - 1);
                if(inDegreeCnt - 1 == 0) {
                    queue.add(adjVertex);
                }
            }
        }

        if(sorted.size() != graph.getVertices().size()) {
            return new DAGSortResult(false, List.of());
        }
        return new DAGSortResult(sorted);
    }

    private static DirectedGraph buildGraph(int[][] graphTable) {
        ConnectedGraphBuilder directedGraphBuilder = new ConnectedGraphBuilder();
        for(int[] edge : graphTable) {
            directedGraphBuilder.addEdge(edge[0], edge[1], edge[2]);
        }
        return directedGraphBuilder.build();
    }
}
