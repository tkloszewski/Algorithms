package com.smart.tkl.euler.p107;

import com.smart.tkl.graph.standard.UndirectedEdge;

import java.math.BigDecimal;
import java.util.Set;

public class MinimalNetworkResult {
    private final Set<UndirectedEdge> edges;
    private final BigDecimal originalWeight;
    private final BigDecimal minimalWeight;

    public MinimalNetworkResult(Set<UndirectedEdge> edges, BigDecimal originalWeight, BigDecimal minimalWeight) {
        this.edges = edges;
        this.originalWeight = originalWeight;
        this.minimalWeight = minimalWeight;
    }

    public Set<UndirectedEdge> getEdges() {
        return edges;
    }

    public BigDecimal getOriginalWeight() {
        return originalWeight;
    }

    public BigDecimal getMinimalWeight() {
        return minimalWeight;
    }

    public BigDecimal getMaxSavings() {
        return originalWeight.subtract(minimalWeight);
    }

    @Override
    public String toString() {
        return "MinimalNetworkResult{" +
                "originalWeight=" + originalWeight +
                ", minimalWeight=" + minimalWeight +
                ", maxSaving=" + getMaxSavings() +
                ", edges=" + edges +

                '}';
    }
}
