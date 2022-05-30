package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.*;

public class BFShortestPathFinder implements ShortestPathFinder {

    private final DirectedGraph graph;
    private static final BigDecimal MAX = BigDecimal.valueOf(Double.MAX_VALUE);
    private static final BigDecimal MIN = BigDecimal.valueOf(Double.MIN_VALUE);


    public BFShortestPathFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public StandardPath find(StandardVertex source, StandardVertex dest) {
        Map<StandardVertex, BigDecimal> costMap = initCostMap(graph);
        Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();

        if(!graph.containsVertex(source) || !graph.containsVertex(dest)) {
            return StandardPath.NONE;
        }

        costMap.put(source, BigDecimal.ZERO);

        for(int  i = 0; i < graph.getVertices().size() - 1; i++) {
            for(StandardEdge edge : graph.getEdges()) {
                BigDecimal fromCost = costMap.get(edge.from);
                if(!MAX.equals(fromCost)) {
                    BigDecimal currentCost = costMap.get(edge.to);
                    BigDecimal newCost = fromCost.add(edge.getCost());
                    if(currentCost.compareTo(newCost) > 0) {
                        costMap.put(edge.to, newCost);
                        previousMap.put(edge.to, edge.from);
                    }
                }
            }
        }

        for(int  i = 0; i < graph.getVertices().size() - 1; i++) {
            for(StandardEdge edge : graph.getEdges()) {
                BigDecimal fromCost = costMap.get(edge.from);
                if(MIN.equals(fromCost)) {
                    costMap.put(edge.to, MIN);
                }
                else {
                    BigDecimal currentCost = costMap.get(edge.to);
                    BigDecimal newCost = fromCost.add(edge.getCost());
                    if(currentCost.compareTo(newCost) > 0) {
                        costMap.put(edge.to, MIN);
                    }
                }
            }
        }

        BigDecimal cost = costMap.get(dest);
        if(MIN.equals(cost)) {
           return StandardPath.NEGATIVE_INFINITY;
        }
        if(MAX.equals(cost)) {
            return StandardPath.NONE;
        }

        LinkedList<StandardVertex> path = new LinkedList<>();
        StandardVertex prevVertex = dest;
        while (prevVertex != null) {
            path.addFirst(prevVertex);
            prevVertex = previousMap.get(prevVertex);
        }

        return new StandardPath(path, cost);
    }

    private Map<StandardVertex, BigDecimal> initCostMap(DirectedGraph graph) {
        Map<StandardVertex, BigDecimal> costMap = new LinkedHashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            costMap.putIfAbsent(vertex, MAX);
        }
        return costMap;
    }
}
