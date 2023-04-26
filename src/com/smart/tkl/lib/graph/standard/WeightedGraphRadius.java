package com.smart.tkl.lib.graph.standard;

import java.math.BigDecimal;

public class WeightedGraphRadius {

    private final BigDecimal radius;
    private final StandardPath path;

    public WeightedGraphRadius(BigDecimal radius, StandardPath path) {
        this.radius = radius;
        this.path = path;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public StandardPath getPath() {
        return path;
    }
}
