package com.smart.tkl.lib.graph.standard;

import com.smart.tkl.lib.graph.standard.sort.BFSTopologicalSorting;
import com.smart.tkl.lib.graph.standard.sort.DAGSortResult;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DAGRelaxationPathFinder implements ShortestPathFinder {

    private final BigDecimal MAX = BigDecimal.valueOf(Double.MAX_VALUE);
    private final DirectedGraph graph;

    private final Map<StandardVertex, SingleSourceShortestPathResult> cashedResult = new LinkedHashMap<>();
    private final List<StandardVertex> sorted;

    public DAGRelaxationPathFinder(DirectedGraph graph) {
        DAGSortResult dagSortResult = BFSTopologicalSorting.sort(graph);
        if(!dagSortResult.isDAG()) {
           throw new IllegalArgumentException("Provided graph is not Directed Acyclic Graph");
        }
        this.graph = graph;
        this.sorted = dagSortResult.getSorted();
    }

    @Override
    public StandardPath find(StandardVertex source, StandardVertex dest) {
        SingleSourceShortestPathResult sssResult = find(source);
        return sssResult.getPath(dest);
    }

    @Override
    public SingleSourceShortestPathResult find(StandardVertex source) {
        if(cashedResult.containsKey(source)) {
           return cashedResult.get(source);
        }
        int sourceIdx = sorted.indexOf(source);
        if(sourceIdx == -1) {
            throw new IllegalArgumentException("The vertex: " + source + " doesn't belong to the graph");
        }
        Map<StandardVertex, BigDecimal> costMap = new LinkedHashMap<>();
        Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();

        for(StandardVertex vertex : graph.getVertices()) {
            costMap.put(vertex, MAX);
        }

        costMap.put(source, BigDecimal.ZERO);

        for(int i = sourceIdx; i < sorted.size(); i++) {
            StandardVertex from = sorted.get(i);
            BigDecimal fromCost = costMap.get(from);
            for(StandardVertex adjVertex : graph.getAdjacentVertices(from)) {
                StandardEdge edge = graph.getEdge(from, adjVertex);
                BigDecimal currentCost = costMap.get(adjVertex);
                BigDecimal newCost = fromCost.add(edge.cost);
                if(currentCost.compareTo(newCost) > 0) {
                   costMap.put(adjVertex, newCost);
                   previousMap.put(adjVertex, from);
                }
            }
        }

        Map<StandardVertex, StandardPath> pathMap = new LinkedHashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            if(!vertex.equals(source)) {
                if(!previousMap.containsKey(vertex)) {
                    pathMap.put(vertex, StandardPath.INFINITY);
                }
                else {
                    pathMap.put(vertex, derivePath(vertex, costMap, previousMap));
                }
            }
        }

        pathMap.put(source, new StandardPath(List.of(source), BigDecimal.ZERO));

        var result = new SingleSourceShortestPathResult(source, pathMap);
        cashedResult.put(source, result);
        return result;
    }

    private StandardPath derivePath(StandardVertex vertex, Map<StandardVertex, BigDecimal> costMap, Map<StandardVertex, StandardVertex> previousMap) {
        LinkedList<StandardVertex> list = new LinkedList<>();
        StandardVertex previous = vertex;
        while (previous != null) {
            list.addFirst(previous);
            previous = previousMap.get(previous);
        }
        return new StandardPath(list, costMap.get(vertex));
    }
}
