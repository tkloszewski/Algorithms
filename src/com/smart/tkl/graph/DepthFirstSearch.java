package com.smart.tkl.graph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DepthFirstSearch {

    public List<GraphNode> search(GraphNode source) {
       Set<GraphNode> visited = new LinkedHashSet<>();
       visit(source, visited);
       return new ArrayList<>(visited);
    }

    private void visit(GraphNode node, Set<GraphNode> visited) {
        visited.add(node);
        for(GraphNode adjacent : node.getNeighbours().keySet()) {
            if(!visited.contains(adjacent)) {
               visit(adjacent, visited);
            }
        }
    }

}
