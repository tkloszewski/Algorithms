package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class DirectedGraphBuilder {

    private final Set<StandardEdge> edges = new LinkedHashSet<>();

    public DirectedGraphBuilder addEdge(String vertexId1, String vertexId2, int cost) {
        return addEdge(new StandardEdge(new StandardVertex(vertexId1), new StandardVertex(vertexId2), BigDecimal.valueOf(cost)));
    }

    public DirectedGraphBuilder addEdge(int vertexId1, int vertexId2, int cost) {
        return addEdge(vertexId1, vertexId2, BigDecimal.valueOf(cost));
    }

    public DirectedGraphBuilder addEdge(int vertexId1, int vertexId2, BigDecimal cost) {
        return addEdge(new StandardEdge(new StandardVertex(vertexId1), new StandardVertex(vertexId2), cost));
    }

    public DirectedGraphBuilder addEdge(StandardEdge edge) {
        return performAddEdge(edge);
    }

    public DirectedGraph build() {
        return new DirectedGraph(new ArrayList<>(edges));
    }

    private DirectedGraphBuilder performAddEdge(StandardEdge edge) {
        edges.add(edge);
        return this;
    }
}
