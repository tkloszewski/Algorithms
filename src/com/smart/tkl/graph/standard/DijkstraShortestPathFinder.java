package com.smart.tkl.graph.standard;

import com.smart.tkl.tree.binary.heap.queue.ChangeablePriorityQueue;
import com.smart.tkl.tree.binary.heap.queue.MinChangeablePriorityQueue;

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

        Map<StandardVertex, CostEntry> costTable = generateCostTableWithPriorityQueue(source);
        Map<StandardVertex, StandardPath> pathMap = new LinkedHashMap<>();

        for(StandardVertex vertex : graph.getVertices()) {
            CostEntry costEntry = costTable.get(vertex);
            if(costEntry == null || costEntry.previous == null) {
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

    private Map<StandardVertex, CostEntry> generateCostTableWithPriorityQueue(StandardVertex source) {
        Map<StandardVertex, CostEntry> result = new LinkedHashMap<>();
        CostEntry currentCostEntry = new CostEntry(source, BigDecimal.ZERO, true);
        result.put(source, currentCostEntry);

        ChangeablePriorityQueue<CostEntry> queue = new MinChangeablePriorityQueue<>(CostEntry.class);
        queue.insert(currentCostEntry);
        for(StandardVertex vertex : graph.getVertices()) {
            if (!vertex.equals(source)) {
                queue.insert(new CostEntry(vertex, MAX));
            }
        }
        while (!queue.isEmpty()) {
            for(StandardVertex vertex : graph.getAdjacentVertices(currentCostEntry.vertex)) {
                StandardEdge edge = graph.getEdge(currentCostEntry.vertex, vertex);
                CostEntry vertexCostEntry = queue.get(new CostEntry(vertex));
                if (vertexCostEntry != null) {
                    BigDecimal currentCost = currentCostEntry.cost;
                    BigDecimal newCost = currentCost.add(edge.cost);
                    if(newCost.compareTo(vertexCostEntry.cost) < 0) {
                        CostEntry updatedCostEntry = new CostEntry(vertex, newCost);
                        updatedCostEntry.previous = currentCostEntry.vertex;
                        queue.updateValue(vertexCostEntry, updatedCostEntry);
                    }
                }
            }
            currentCostEntry = queue.deleteFirst();
            currentCostEntry.processed = true;
            result.put(currentCostEntry.vertex, currentCostEntry);
        }

        return result;
    }

    private static class CostEntry implements Comparable<CostEntry> {
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
        public int compareTo(CostEntry o) {
            return this.cost.compareTo(o.cost);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CostEntry costEntry = (CostEntry) o;
            return vertex.equals(costEntry.vertex);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex);
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
