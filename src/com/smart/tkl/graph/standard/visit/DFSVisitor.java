package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardVertex;

import java.util.LinkedHashMap;
import java.util.Map;

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
        return new VisitorResult(source, distanceMap);
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
