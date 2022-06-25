package com.smart.tkl.graph.standard.connect;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardEdge;
import com.smart.tkl.graph.standard.StandardVertex;
import com.smart.tkl.graph.standard.visit.BFSVisitor;
import com.smart.tkl.graph.standard.visit.VisitorResult;

import java.util.LinkedHashSet;
import java.util.Set;

public class GraphConnectivityDetector {

    private final DirectedGraph graph;
    private final DirectedGraph doubleEdgedGraph;

    public GraphConnectivityDetector(DirectedGraph graph) {
        this.graph = graph;
        this.doubleEdgedGraph = toDoubledEdgeGraph(graph);
    }

    public ConnectivityResult checkConnectivity() {
        boolean weaklyConnected = isWeaklyConnected();
        boolean stronglyConnected = weaklyConnected ? isStronglyConnected() : false;
        return new ConnectivityResult(weaklyConnected, stronglyConnected);
    }

    public boolean isWeaklyConnected() {
        StandardVertex vertex = this.doubleEdgedGraph.getVertices().iterator().next();
        VisitorResult visitorResult = new BFSVisitor(this.doubleEdgedGraph).visitFrom(vertex);
        return visitorResult.getUnvisited().isEmpty();
    }

    private boolean isStronglyConnected() {
        BFSVisitor bfsVisitor = new BFSVisitor(graph);
        for(StandardVertex vertex : graph.getVertices()) {
            VisitorResult visitorResult = bfsVisitor.visitFrom(vertex);
            if(!visitorResult.getUnvisited().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private DirectedGraph toDoubledEdgeGraph(DirectedGraph graph) {
        Set<StandardEdge> doubleEdges = new LinkedHashSet<>();
        for(StandardEdge edge : graph.getEdges()) {
            doubleEdges.add(edge);
            StandardEdge reverted = new StandardEdge(edge.getTo(), edge.getFrom(), edge.getCost());
            if(!graph.containsEdge(reverted)) {
                doubleEdges.add(reverted);
            }
        }
        return new DirectedGraph(doubleEdges);
    }

}
