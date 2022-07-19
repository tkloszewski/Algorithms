package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.Map;

public class WeightedGraphRadiusResolver {

    private final DirectedGraph graph;

    public WeightedGraphRadiusResolver(DirectedGraph graph) {
        this.graph = graph;
    }

    public WeightedGraphRadius resolveRadius() {
        ShortestPathFinder pathFinder = new DijkstraShortestPathFinder(this.graph);

        StandardPath radiusPath = StandardPath.INFINITY;
        for(StandardVertex source : graph.getVertices()) {
            SingleSourceShortestPathResult pathResult = pathFinder.find(source);
            StandardPath eccentricityPath = StandardPath.NEGATIVE_INFINITY;
            for(StandardVertex dest : graph.getVertices()) {
                StandardPath shortestPath = pathResult.getPath(dest);
                if(shortestPath.cost.compareTo(eccentricityPath.cost) > 0) {
                    eccentricityPath = shortestPath;
                }
            }
            if(radiusPath.cost.compareTo(eccentricityPath.cost) > 0) {
                radiusPath = eccentricityPath;
            }
        }

        return new WeightedGraphRadius(radiusPath.cost, radiusPath);
    }


}
