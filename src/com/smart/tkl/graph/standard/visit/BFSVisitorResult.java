package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.StandardVertex;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BFSVisitorResult extends VisitorResult {

    private final int eccentricity;
    private final Map<StandardVertex, StandardVertex> previousMap;

    public BFSVisitorResult(StandardVertex source, Map<StandardVertex, Integer> distanceMap, List<StandardVertex> unvisited,
                            int eccentricity, Map<StandardVertex, StandardVertex> previousMap) {
        super(source, distanceMap, unvisited);
        this.eccentricity = eccentricity;
        this.previousMap = previousMap;
    }

    public int getEccentricity() {
        return eccentricity;
    }

    public Map<StandardVertex, StandardVertex> getPreviousMap() {
        return previousMap;
    }
}
