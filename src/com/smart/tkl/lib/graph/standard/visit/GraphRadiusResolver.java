package com.smart.tkl.lib.graph.standard.visit;

import com.smart.tkl.lib.graph.standard.DirectedGraph;
import com.smart.tkl.lib.graph.standard.StandardVertex;

public class GraphRadiusResolver {

    private final DirectedGraph graph;

    public GraphRadiusResolver(DirectedGraph graph) {
        this.graph = graph;
    }

    public Integer resolveRadius() {
        int radius = 0;
        BFSVisitor bfs = new BFSVisitor(graph);
        for(StandardVertex vertex : graph.getVertices()) {
            BFSVisitorResult visitorResult = bfs.visitFrom(vertex);
            radius = Math.max(radius, visitorResult.getEccentricity());
        }
        return radius;
    }
}
