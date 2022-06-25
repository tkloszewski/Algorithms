package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.Objects;

public class StandardEdge {

    protected final StandardVertex from;
    protected final StandardVertex to;
    protected final BigDecimal cost;

    public StandardEdge(StandardVertex from, StandardVertex to, BigDecimal cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public StandardVertex getFrom() {
        return from;
    }

    public StandardVertex getTo() {
        return to;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardEdge edge = (StandardEdge) o;
        return from.equals(edge.from) &&
                to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "StandardEdge{" +
                "from=" + from +
                ", to=" + to +
                ", cost=" + cost +
                '}';
    }
}
