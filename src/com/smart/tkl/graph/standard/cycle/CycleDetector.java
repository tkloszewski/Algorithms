package com.smart.tkl.graph.standard.cycle;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardEdge;
import com.smart.tkl.graph.standard.StandardVertex;

import java.math.BigDecimal;
import java.util.*;

public class CycleDetector {

    private final DirectedGraph graph;
    private Map<StandardVertex, Color> colorMap;
    private Map<StandardVertex, StandardVertex> previousMap;
    private StandardVertex cycleStart;
    private StandardVertex cycleEnd;

    public CycleDetector(DirectedGraph graph) {
        this.graph = graph;
    }

    public Optional<GraphCycle> detectNegativeCycle() {
        initMaps();
        for(StandardVertex vertex : graph.getVertices()) {
            if(colorMap.get(vertex).equals(Color.WHITE) && dfs(vertex)) {
                //found cycle
                GraphCycle cycle = getCycleFromReachableVertex(vertex);
                if(cycle.getCost().compareTo(BigDecimal.ZERO) < 0) {
                    return Optional.of(cycle);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<GraphCycle> detectCycle() {
        initMaps();
        for(StandardVertex vertex : graph.getVertices()) {
            if(colorMap.get(vertex).equals(Color.WHITE) && dfs(vertex)) {
               //found cycle
                return Optional.of(getCycleFromReachableVertex(vertex));
            }
        }
        return Optional.empty();
    }

    private void initMaps() {
        colorMap = new HashMap<>();
        previousMap = new LinkedHashMap<>();
        for(StandardVertex vertex : graph.getVertices()) {
            colorMap.put(vertex, Color.WHITE);
        }
    }

    private GraphCycle getCycleFromReachableVertex(StandardVertex vertex) {
        LinkedList<StandardVertex> cycleVertices = new LinkedList<>();

        StandardVertex current = cycleEnd;
        BigDecimal cost = BigDecimal.ZERO;

        while (!current.equals(cycleStart)) {
            cycleVertices.addFirst(current);
            StandardVertex prev = previousMap.get(current);
            StandardEdge edge = graph.getEdge(prev, current);
            cost = cost.add(edge.getCost());
            current = prev;
        }
        StandardEdge edge = graph.getEdge(cycleEnd, cycleStart);
        cost = cost.add(edge.getCost());

        cycleVertices.addFirst(cycleStart);
        return new GraphCycle(cycleVertices, cost);
    }

    private boolean dfs(StandardVertex vertex) {
        colorMap.put(vertex, Color.GRAY);
        for(StandardVertex outgoingVertex : graph.getAdjacentVertices(vertex)) {
            previousMap.put(outgoingVertex, vertex);

            Color color = colorMap.get(outgoingVertex);

            if(color.equals(Color.WHITE) && dfs(outgoingVertex)) {
               return true;
            }
            if(color.equals(Color.GRAY)) {
               cycleStart = outgoingVertex;
               cycleEnd = vertex;
               return true;
            }
        }
        colorMap.put(vertex, Color.BLACK);
        return false;
    }

    private enum Color {
        WHITE,
        GRAY,
        BLACK
    }
}
