package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.*;

public class BFShortestPathFinder implements ShortestPathFinder {

    private final DirectedGraph graph;
    private static final BigDecimal MAX = BigDecimal.valueOf(Double.MAX_VALUE);
    private static final BigDecimal MIN = BigDecimal.valueOf(Double.MIN_VALUE);

    private final Map<StandardVertex, SingleSourceShortestPathResult> cachedPaths = new LinkedHashMap<>();

    public BFShortestPathFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public SingleSourceShortestPathResult find(StandardVertex source) {
        if(!graph.containsVertex(source)) {
           return null;
        }

        if(cachedPaths.containsKey(source)) {
           return cachedPaths.get(source);
        }

        Map<StandardVertex, BigDecimal> costMap = initCostMap(graph);
        Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();
        costMap.put(source, BigDecimal.ZERO);
        fillCostAndPreviousMap(costMap, previousMap);

        Map<StandardVertex, StandardPath> pathMap = new LinkedHashMap<>();

        for(StandardVertex vertex : graph.getVertices()) {
            BigDecimal cost = costMap.get(vertex);
            if(MIN.equals(cost)) {
                pathMap.put(vertex, StandardPath.NEGATIVE_INFINITY);
            }
            else if(MAX.equals(cost)) {
                pathMap.put(vertex, StandardPath.NONE);
            }
            else {
                LinkedList<StandardVertex> pathVertices = derivePath(previousMap, vertex);
                pathMap.put(vertex, new StandardPath(pathVertices, cost));
            }
        }

        SingleSourceShortestPathResult result = new SingleSourceShortestPathResult(source, pathMap);
        cachedPaths.put(source, result);
        return result;
    }

    @Override
    public StandardPath find(StandardVertex source, StandardVertex dest) {
        SingleSourceShortestPathResult sssPathResult = find(source);
        return sssPathResult.getPath(dest);
    }

    private LinkedList<StandardVertex> derivePath(Map<StandardVertex, StandardVertex> previousMap, StandardVertex dest) {
        LinkedList<StandardVertex> path = new LinkedList<>();
        StandardVertex prevVertex = dest;
        while (prevVertex != null) {
            path.addFirst(prevVertex);
            prevVertex = previousMap.get(prevVertex);
        }
        return path;
    }

    private void fillCostAndPreviousMap(Map<StandardVertex, BigDecimal> costMap, Map<StandardVertex, StandardVertex> previousMap) {
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
    }

    private Map<StandardVertex, BigDecimal> initCostMap(DirectedGraph graph) {
        Map<StandardVertex, BigDecimal> costMap = new LinkedHashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            costMap.putIfAbsent(vertex, MAX);
        }
        return costMap;
    }
}
