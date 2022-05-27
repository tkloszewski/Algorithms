package com.smart.tkl.graph.standard;

import java.util.*;

public class DirectedGraph {

    private final List<StandardEdge> edges;
    private final Set<StandardVertex> vertices = new LinkedHashSet<>();
    private final Map<StandardVertex, List<StandardEdge>> adjacencyMap = new HashMap<>();

    public DirectedGraph(List<StandardEdge> edges) {
        this.edges = new LinkedList<>(edges);
        for(StandardEdge edge : edges) {
            vertices.add(edge.from);
            vertices.add(edge.to);
            if(!adjacencyMap.containsKey(edge.from)) {
                List<StandardEdge> outgoingEdges = new LinkedList<>();
                outgoingEdges.add(edge);
                adjacencyMap.put(edge.from, outgoingEdges);
            }
            else {
                adjacencyMap.get(edge.from).add(edge);
            }
        }
    }

    public boolean containsVertex(StandardVertex vertex) {
        return vertices.contains(vertex);
    }

    public List<StandardEdge> getEdges() {
        return edges;
    }

    public Set<StandardVertex> getVertices() {
        return vertices;
    }

    public Map<StandardVertex, List<StandardEdge>> getAdjacencyMap() {
        return adjacencyMap;
    }
}
