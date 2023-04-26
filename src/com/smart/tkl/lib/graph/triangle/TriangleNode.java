package com.smart.tkl.lib.graph.triangle;

import java.util.ArrayList;
import java.util.List;

public class TriangleNode {

    private final int value;
    private List<TriangleNode> adjacent = new ArrayList<>(2);

    public TriangleNode(int value) {
        this.value = value;
    }

    public TriangleNode(int value, List<TriangleNode> adjacent) {
        this.value = value;
        this.adjacent = adjacent;
    }

    public int getValue() {
        return value;
    }

    public List<TriangleNode> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(TriangleNode triangleNode) {
        this.adjacent.add(triangleNode);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
