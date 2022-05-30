package com.smart.tkl.graph.standard.cycle;

import com.smart.tkl.graph.standard.DirectedGraph;
import com.smart.tkl.graph.standard.StandardEdge;
import com.smart.tkl.graph.standard.StandardVertex;

import java.math.BigDecimal;
import java.util.*;

public class CycleDetector {

    private final DirectedGraph graph;
    private final Map<StandardVertex, Color> colorMap = new HashMap<>();
    private final Map<StandardVertex, StandardVertex> previousMap = new LinkedHashMap<>();
    private StandardVertex cycleStart;
    private StandardVertex cycleEnd;

    public CycleDetector(DirectedGraph graph) {
        this.graph = graph;
    }

    public Optional<GraphCycle> detectCycle() {
        for(StandardVertex vertex : graph.getVertices()) {
            colorMap.put(vertex, Color.WHITE);
        }
        for(StandardVertex vertex : graph.getVertices()) {
            if(colorMap.get(vertex).equals(Color.WHITE) && dfs(vertex)) {
               //found cycle
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
                return Optional.of(new GraphCycle(cycleVertices, cost));
            }
        }
        return Optional.empty();
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
