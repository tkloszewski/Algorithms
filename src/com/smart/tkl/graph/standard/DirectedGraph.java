package com.smart.tkl.graph.standard;

import java.util.*;
import java.util.stream.Collectors;

public class DirectedGraph {

    private final List<StandardEdge> edges;
    private final Map<EdgeKey, StandardEdge> edgeMap;
    private final Set<StandardVertex> vertices = new LinkedHashSet<>();
    private final Map<StandardVertex, List<StandardEdge>> adjacencyMap = new HashMap<>();

    public DirectedGraph(Collection<StandardEdge> edges) {
        this.edgeMap = edges.stream().collect(Collectors.toMap(e -> new EdgeKey(e.from, e.to), e -> e, (e1, e2) -> e1, LinkedHashMap::new));
        this.edges = new LinkedList<>(edgeMap.values());
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

    public List<StandardVertex> getAdjacentVertices(StandardVertex vertex) {
        return Optional.ofNullable(adjacencyMap.get(vertex))
                .map(edges -> edges.stream().map(e -> e.to).collect(Collectors.toList()))
                .orElse(List.of());
    }

    public boolean containsVertex(StandardVertex vertex) {
        return vertices.contains(vertex);
    }

    public boolean containsEdge(StandardEdge edge) {
        return edgeMap.containsKey(new EdgeKey(edge.from, edge.to));
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

    public StandardEdge getEdge(StandardVertex from, StandardVertex to) {
        return edgeMap.get(new EdgeKey(from, to));
    }

    private static class EdgeKey {
        StandardVertex from;
        StandardVertex to;

        public EdgeKey(StandardVertex from, StandardVertex to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EdgeKey edgeKey = (EdgeKey) o;
            return Objects.equals(from, edgeKey.from) &&
                    Objects.equals(to, edgeKey.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
}
