package com.smart.tkl.lib.graph.standard;

import java.math.BigDecimal;
import java.util.*;

public class ConnectedGraphBuilder {

    private final List<StandardEdge> edges = new LinkedList<>();
    private final List<Set<StandardVertex>> connectedSets = new ArrayList<>();

    public ConnectedGraphBuilder addEdge(String vertexId1, String vertexId2, int cost) {
        return addEdge(new StandardEdge(new StandardVertex(vertexId1), new StandardVertex(vertexId2), BigDecimal.valueOf(cost)));
    }

    public ConnectedGraphBuilder addEdge(int vertexId1, int vertexId2, int cost) {
        return addEdge(vertexId1, vertexId2, BigDecimal.valueOf(cost));
    }

    public ConnectedGraphBuilder addEdge(int vertexId1, int vertexId2, BigDecimal cost) {
        return addEdge(new StandardEdge(new StandardVertex(vertexId1), new StandardVertex(vertexId2), cost));
    }

    public ConnectedGraphBuilder addEdge(StandardEdge edge) {
       return performAddEdge(edge);
    }

    public DirectedGraph build() {
        if(connectedSets.size() == 1) {
          return new DirectedGraph(this.edges);
        }
        throw new IllegalStateException("Cannot build connected graph. There are " + connectedSets.size() + " disconnected vertices sets");
    }

    private ConnectedGraphBuilder performAddEdge(StandardEdge edge) {
        edges.add(edge);

        Set<StandardVertex> newVerticesSet = Set.of(edge.from, edge.to);

        boolean addNewSet = true;
        for(int i = 0; i < connectedSets.size(); i++) {
            Set<StandardVertex> connectedSet1 = connectedSets.get(i);
            if(!connectedSet1.contains(edge.from) && !connectedSet1.contains(edge.to)) {
                continue;
            }

            addNewSet = false;

            //no new vertices, add just single edge
            if(connectedSet1.contains(edge.from) && connectedSet1.contains(edge.to)) {
                connectedSet1.addAll(newVerticesSet);
                break;
            }

            Set<StandardVertex> nextConnectedSet = null;

            for(int j = i + 1; j < connectedSets.size(); j++) {
                Set<StandardVertex> connectedSet2 = connectedSets.get(i);
                if(connectedSet2.contains(edge.from) || connectedSet2.contains(edge.to)) {
                    nextConnectedSet = connectedSet2;
                    break;
                }
            }

            if(nextConnectedSet != null) {
                connectedSet1.addAll(nextConnectedSet);
                connectedSets.remove(nextConnectedSet);
            }
            else {
               connectedSet1.addAll(newVerticesSet);
            }

            break;
        }

        if(addNewSet) {
            connectedSets.add(new LinkedHashSet<>(newVerticesSet));
        }

        return this;
    }
}
