package com.smart.tkl.lib.graph.standard;

import java.util.LinkedHashSet;
import java.util.Set;

public class UndirectedGraph {

    private final Set<UndirectedEdge> edges;
    private final Set<StandardVertex> vertices;

    public UndirectedGraph(Set<UndirectedEdge> edges) {
        this.edges = edges;
        this.vertices = new LinkedHashSet<>();
        for(UndirectedEdge edge : edges) {
            this.vertices.add(edge.getVertex1());
            this.vertices.add(edge.getVertex2());
        }
    }

    public Set<UndirectedEdge> getEdges() {
        return edges;
    }

    public Set<StandardVertex> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "UndirectedGraph{" +
                "edges=" + edges +
                '}';
    }
}
