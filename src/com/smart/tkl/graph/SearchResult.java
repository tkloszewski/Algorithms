package com.smart.tkl.graph;

import java.util.Map;
import java.util.Set;

public class SearchResult {

    private GraphNode source;
    private Map<GraphNode, GraphNode> previousMap;
    private Map<GraphNode, Integer> distanceMap;
    private Set<GraphNode> visited;

    public SearchResult(GraphNode source, Map<GraphNode, GraphNode> previousMap, Map<GraphNode, Integer> distanceMap, Set<GraphNode> visited) {
        this.source = source;
        this.previousMap = previousMap;
        this.distanceMap = distanceMap;
        this.visited = visited;
    }

    public GraphNode getSource() {
        return source;
    }

    public Map<GraphNode, GraphNode> getPreviousMap() {
        return previousMap;
    }

    public Map<GraphNode, Integer> getDistanceMap() {
        return distanceMap;
    }

    public Set<GraphNode> getVisited() {
        return visited;
    }
}
