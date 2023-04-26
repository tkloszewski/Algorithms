package com.smart.tkl.lib.graph.standard.visit;

import com.smart.tkl.lib.graph.standard.DirectedGraph;
import com.smart.tkl.lib.graph.standard.StandardVertex;

import java.util.*;
import java.util.stream.Collectors;

public class BFSVisitor implements GraphVisitor {

    private final DirectedGraph graph;

    public BFSVisitor(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public BFSVisitorResult visitFrom(StandardVertex source) {
        if(!graph.containsVertex(source)) {
           return new BFSVisitorResult(source, Map.of(), List.of(), 0, Map.of());
        }
        Set<StandardVertex> visited = new LinkedHashSet<>();
        Map<StandardVertex, Integer> distanceMap = new LinkedHashMap<>();
        Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();
        distanceMap.put(source, 0);
        Queue<StandardVertex> queue = new LinkedList<>();
        queue.add(source);

        int maxDistance = 0;

        while (!queue.isEmpty()) {
            StandardVertex current = queue.poll();
            int currentCost = distanceMap.get(current);
            for(StandardVertex vertex : graph.getAdjacentVertices(current)) {
                if (!visited.contains(vertex)) {
                    distanceMap.put(vertex, currentCost + 1);
                    previousMap.put(vertex, current);
                    queue.add(vertex);
                    maxDistance = Math.max(maxDistance, currentCost + 1);
                }
            }
            visited.add(current);
        }
        List<StandardVertex> unvisited = graph.getVertices()
                .stream()
                .filter(v -> !distanceMap.containsKey(v))
                .collect(Collectors.toList());

        return new BFSVisitorResult(source ,distanceMap, unvisited, maxDistance, previousMap);
    }
}
