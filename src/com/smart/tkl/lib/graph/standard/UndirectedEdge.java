package com.smart.tkl.lib.graph.standard;

import java.math.BigDecimal;

public class UndirectedEdge {

    private final StandardVertex vertex1;
    private final StandardVertex vertex2;
    private final BigDecimal weight;

    public UndirectedEdge(StandardVertex vertex1, StandardVertex vertex2, BigDecimal weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public StandardVertex getVertex1() {
        return vertex1;
    }

    public StandardVertex getVertex2() {
        return vertex2;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UndirectedEdge that = (UndirectedEdge) o;
        return (vertex1.equals(that.vertex1) &&
                vertex2.equals(that.vertex2)) ||
                (vertex2.equals(that.vertex1) &&
                        vertex1.equals(that.vertex2));
    }

    @Override
    public int hashCode() {
        return vertex1.hashCode() + vertex2.hashCode();
    }

    @Override
    public String toString() {
        return "{" +
                "vertex1=" + vertex1 +
                ", vertex2=" + vertex2 +
                ", weight=" + weight +
                '}';
    }
}
