package com.smart.tkl.graph.standard.connect;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardEdge;
import com.smart.tkl.graph.standard.StandardVertex;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConnectedComponentsCounter {

    private final DirectedGraph graph;
    private final DirectedGraph originalGraph;
    private Set<StandardVertex> weaklyVisited;

    public ConnectedComponentsCounter(DirectedGraph graph) {
        this.originalGraph = graph;
        this.graph = toDoubledEdgeGraph(graph);
    }

    public int weaklyConnectedCount() {
        weaklyVisited = new LinkedHashSet<>();
        int connectedComponentsCount = 0;
        for(StandardVertex vertex : graph.getVertices()) {
            if(weaklyVisited.size() == graph.getVertices().size()) {
               break;
            }
            if(!weaklyVisited.contains(vertex)) {
               dfs(vertex);
               connectedComponentsCount++;
            }
        }
        return connectedComponentsCount;
    }

    private void dfs(StandardVertex source) {
        weaklyVisited.add(source);
        for(StandardVertex adjVertex : graph.getAdjacentVertices(source)) {
            if(!weaklyVisited.contains(adjVertex)) {
                dfs(adjVertex);
            }
        }
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
