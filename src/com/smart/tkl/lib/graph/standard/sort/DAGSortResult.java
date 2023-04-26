package com.smart.tkl.lib.graph.standard.sort;

import com.smart.tkl.lib.graph.standard.StandardVertex;

import java.util.List;

public class DAGSortResult {

    private final boolean isDAG;
    private final List<StandardVertex> sorted;

    public DAGSortResult(List<StandardVertex> sorted) {
        this.sorted = sorted;
        this.isDAG = true;
    }

    public DAGSortResult(boolean isDAG, List<StandardVertex> sorted) {
        this.isDAG = isDAG;
        this.sorted = sorted;
    }

    public boolean isDAG() {
        return isDAG;
    }

    public List<StandardVertex> getSorted() {
        return sorted;
    }

    @Override
    public String toString() {
        return "DAGSortResult{" +
                "isDAG=" + isDAG +
                ", sorted=" + sorted +
                '}';
    }
}
