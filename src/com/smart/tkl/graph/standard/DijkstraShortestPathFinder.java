package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.*;

public class DijkstraShortestPathFinder implements ShortestPathFinder {

    private final BigDecimal MAX = BigDecimal.valueOf(Double.MAX_VALUE);
    private final DirectedGraph graph;

    private final Map<StandardVertex, SingleSourceShortestPathResult> cashedResult = new LinkedHashMap<>();

    public DijkstraShortestPathFinder(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public SingleSourceShortestPathResult find(StandardVertex source) {
        if(cashedResult.containsKey(source)) {
           return cashedResult.get(source);
        }

        Map<StandardVertex, CostEntry> costTable = generateCostTable(source);
        Map<StandardVertex, StandardPath> pathMap = new LinkedHashMap<>();

        for(StandardVertex vertex : graph.getVertices()) {
            CostEntry costEntry = costTable.get(vertex);
            if(costEntry.previous == null) {
               pathMap.put(vertex, StandardPath.INFINITY);
            }
            else {
               pathMap.put(vertex, derivePath(vertex, costTable));
            }
        }

        pathMap.put(source, new StandardPath(List.of(source), BigDecimal.ZERO));

        SingleSourceShortestPathResult result = new SingleSourceShortestPathResult(source, pathMap);
        cashedResult.put(source, result);
        return result;
    }

    @Override
    public StandardPath find(StandardVertex source, StandardVertex dest) {
        SingleSourceShortestPathResult sssPathResult = find(source);
        return sssPathResult.getPath(dest);
    }

    private StandardPath derivePath(StandardVertex dest, Map<StandardVertex, CostEntry> costTable) {
        LinkedList<StandardVertex> pathVertices = new LinkedList<>();
        CostEntry costEntry = costTable.get(dest);
        BigDecimal cost = costEntry.cost;
        pathVertices.addFirst(dest);
        while (costEntry.previous != null) {
            pathVertices.addFirst(costEntry.previous);
            costEntry = costTable.get(costEntry.previous);
        }
        return new StandardPath(pathVertices, cost);
    }

    private Map<StandardVertex, CostEntry> generateCostTable(StandardVertex source) {
        Map<StandardVertex, CostEntry> costMap = new LinkedHashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            costMap.put(vertex, new CostEntry(vertex, MAX));
        }
        CostEntry currentCostEntry = new CostEntry(source, BigDecimal.ZERO, true);
        costMap.put(source, currentCostEntry);

        while (true) {
            for(StandardVertex vertex : graph.getAdjacentVertices(currentCostEntry.vertex)) {
                StandardEdge edge = graph.getEdge(currentCostEntry.vertex, vertex);
                CostEntry vertexCostEntry = costMap.get(vertex);
                BigDecimal currentCost = currentCostEntry.cost;
                BigDecimal newCost = currentCost.add(edge.cost);
                if(newCost.compareTo(vertexCostEntry.cost) < 0) {
                    vertexCostEntry.cost = newCost;
                    vertexCostEntry.previous = currentCostEntry.vertex;
                }
            }
            Optional<CostEntry> nextMinCostEntry = findMinCostVertex(costMap);
            if(nextMinCostEntry.isEmpty()) {
                break;
            }
            currentCostEntry = nextMinCostEntry.get();
            currentCostEntry.processed = true;
        }


        return costMap;
    }

    private Optional<CostEntry> findMinCostVertex(Map<StandardVertex, CostEntry> costMap) {
        CostEntry minCostEntry = null;
        BigDecimal minCost = MAX;
        for(CostEntry costEntry : costMap.values()) {
            if(!costEntry.processed) {
                BigDecimal cost = costEntry.cost;
                if(cost.compareTo(minCost) < 0) {
                    minCost = cost;
                    minCostEntry = costEntry;
                }
            }
        }
        return Optional.ofNullable(minCostEntry);
    }

    private static class CostEntry {
        StandardVertex vertex;
        BigDecimal cost;
        StandardVertex previous;
        boolean processed;

        public CostEntry(StandardVertex vertex) {
            this.vertex = vertex;
        }

        public CostEntry(StandardVertex vertex, BigDecimal cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        public CostEntry(StandardVertex vertex, BigDecimal cost, boolean processed) {
            this.vertex = vertex;
            this.cost = cost;
            this.processed = processed;
        }

        @Override
        public String toString() {
            return "CostEntry{" +
                    "vertex=" + vertex +
                    ", cost=" + cost +
                    ", previous=" + previous +
                    ", processed=" + processed +
                    '}';
        }
    }
}
