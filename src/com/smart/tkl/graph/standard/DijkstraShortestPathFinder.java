package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.*;

public class DijkstraShortestPathFinder implements ShortestPathFinder {

    private final BigDecimal MAX = BigDecimal.valueOf(Double.MAX_VALUE);
    private final DirectedGraph graph;

    public DijkstraShortestPathFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public StandardPath find(StandardVertex source, StandardVertex dest) {
        if(source.equals(dest)) {
           return new StandardPath(List.of(), BigDecimal.ZERO);
        }
        if(!graph.containsVertex(source) || !graph.containsVertex(dest)) {
            return StandardPath.NONE;
        }
        Map<StandardVertex, BigDecimal> costMap = new LinkedHashMap<>();
        Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();

        for(StandardVertex vertex : graph.getVertices()) {
            costMap.put(vertex, MAX);
        }
        costMap.put(source, BigDecimal.ZERO);

        StandardVertex current = source;
        Set<StandardVertex> processed = new HashSet<>();
        processed.add(source);

        while (true) {
            for(StandardVertex vertex : graph.getAdjacentVertices(current)) {
                StandardEdge edge = graph.getEdge(current, vertex);
                BigDecimal currentCost = costMap.get(current);
                BigDecimal newCost = currentCost.add(edge.cost);
                if(newCost.compareTo(costMap.get(vertex)) < 0) {
                   costMap.put(vertex, newCost);
                   previousMap.put(vertex, current);
                }
            }
            Optional<StandardVertex> nextMinCostVertex = findMinCostVertex(processed, costMap);
            if(nextMinCostVertex.isEmpty()) {
               break;
            }
            current = nextMinCostVertex.get();
            processed.add(current);
        }

        StandardVertex prev = previousMap.get(dest);
        if(prev == null) {
           return StandardPath.INFINITY;
        }
        LinkedList<StandardVertex> pathVertices = new LinkedList<>();
        pathVertices.addFirst(dest);
        while (prev != null) {
            pathVertices.addFirst(prev);
            prev = previousMap.get(prev);
        }

        return new StandardPath(pathVertices, costMap.get(dest));
    }

    private Optional<StandardVertex> findMinCostVertex(Set<StandardVertex> processed, Map<StandardVertex, BigDecimal> costMap) {
        StandardVertex minCostVertex = null;
        BigDecimal minCost = MAX;
        for(StandardVertex vertex : costMap.keySet()) {
            if(!processed.contains(vertex)) {
               BigDecimal cost = costMap.get(vertex);
               if(cost.compareTo(minCost) < 0) {
                  minCost = cost;
                  minCostVertex = vertex;
               }
            }
        }
        return Optional.ofNullable(minCostVertex);
    }
}
