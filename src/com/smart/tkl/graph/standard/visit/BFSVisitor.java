package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardVertex;

import java.util.*;

public class BFSVisitor implements GraphVisitor {

    private final DirectedGraph graph;

    public BFSVisitor(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public VisitorResult visitFrom(StandardVertex source) {
        if(!graph.containsVertex(source)) {
           return new VisitorResult(source, Map.of());
        }
        Set<StandardVertex> visited = new LinkedHashSet<>();
        Map<StandardVertex, Integer> distanceMap = new LinkedHashMap<>();
        distanceMap.put(source, 0);
        Queue<StandardVertex> queue = new LinkedList<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            StandardVertex current = queue.poll();
            int currentCost = distanceMap.get(current);
            for(StandardVertex vertex : graph.getAdjacentVertices(current)) {
                if (!visited.contains(vertex)) {
                    distanceMap.put(vertex, currentCost + 1);
                    queue.add(vertex);
                }
            }
            visited.add(current);
        }
        return new VisitorResult(source ,distanceMap);
    }
}
