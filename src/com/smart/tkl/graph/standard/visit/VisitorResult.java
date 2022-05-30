package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.StandardVertex;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VisitorResult {
    private final StandardVertex source;
    private final List<StandardVertex> visited;
    private final Map<StandardVertex, Integer> distanceMap;

    public VisitorResult(StandardVertex source, Map<StandardVertex, Integer> distanceMap) {
        this.source = source;
        this.distanceMap = distanceMap;
        this.visited = new LinkedList<>(distanceMap.keySet());
    }

    public Integer getDistance(StandardVertex vertex) {
        return distanceMap.get(vertex);
    }

    public StandardVertex getSource() {
        return source;
    }

    public List<StandardVertex> getVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return "{" +
                "source=" + source +
                ", distanceMap=" + distanceMap +
                '}';
    }
}
