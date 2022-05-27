package com.smart.tkl.graph.standard;

import java.math.BigDecimal;

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
    public String toString() {
        return "StandardEdge{" +
                "from=" + from +
                ", to=" + to +
                ", cost=" + cost +
                '}';
    }
}
