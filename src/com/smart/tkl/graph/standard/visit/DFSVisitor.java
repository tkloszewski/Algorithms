package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardVertex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DFSVisitor implements GraphVisitor {

    private final DirectedGraph graph;

    public DFSVisitor(DirectedGraph graph) {
        this.graph = graph;
    }

    @Override
    public VisitorResult visitFrom(StandardVertex source) {
        Map<StandardVertex, Integer> distanceMap = new LinkedHashMap<>();
        distanceMap.put(source, 0);
        doVisit(distanceMap, source);
        List<StandardVertex> unvisited = graph.getVertices()
                .stream()
                .filter(v -> !distanceMap.containsKey(v))
                .collect(Collectors.toList());
        return new VisitorResult(source, distanceMap, unvisited);
    }

    private void doVisit(Map<StandardVertex, Integer> distanceMap, StandardVertex vertex) {
        int previousCost = distanceMap.get(vertex);

        for(StandardVertex adjVertex : graph.getAdjacentVertices(vertex)) {
            if(!distanceMap.containsKey(adjVertex)) {
                distanceMap.put(adjVertex, previousCost + 1);
                doVisit(distanceMap, adjVertex);
            }
        }
    }
}
