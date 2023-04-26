package com.smart.tkl.lib.graph.standard.cycle;

import com.smart.tkl.lib.graph.standard.StandardVertex;

import java.math.BigDecimal;
import java.util.List;

public class GraphCycle {

    private final List<StandardVertex> vertices;
    private final BigDecimal cost;

    public GraphCycle(List<StandardVertex> vertices, BigDecimal cost) {
        this.vertices = vertices;
        this.cost = cost;
    }

    public List<StandardVertex> getVertices() {
        return vertices;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "GraphCycle{" +
                "vertices=" + vertices +
                ", cost=" + cost +
                '}';
    }
}
