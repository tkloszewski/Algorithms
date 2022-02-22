package com.smart.tkl.graph;

import java.util.LinkedHashMap;
import java.util.Map;

public class GraphNode {

    private String name;

    private Map<GraphNode, Integer> neighbours = new LinkedHashMap<>();

    public GraphNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<GraphNode, Integer> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<GraphNode, Integer> neighbours) {
        this.neighbours = neighbours;
    }


}
