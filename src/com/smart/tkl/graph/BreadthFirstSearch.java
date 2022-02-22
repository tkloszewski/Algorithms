package com.smart.tkl.graph;

import java.util.*;

public class BreadthFirstSearch {

    public List<GraphNode> shortestPath(GraphNode source, GraphNode dest) {
        List<GraphNode> path = new LinkedList<>();
        SearchResult searchResult = search(source);

        if(!searchResult.getVisited().contains(dest)) {
            return path;
        }

        Map<GraphNode, GraphNode> previousMap = searchResult.getPreviousMap();
        GraphNode previous = previousMap.get(dest);
        path.add(dest);
        while (previous != null && previous != source) {
            path.add(0, previous);
            previous = previousMap.get(previous);
        }

        return path;
    }

    public SearchResult search(GraphNode source) {
        Set<GraphNode> visited = new HashSet<>();
        Map<GraphNode, Integer> distanceMap = new HashMap<>();
        Map<GraphNode, GraphNode> previousMap = new HashMap<>();

        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(source);
        distanceMap.put(source, 0);
        previousMap.put(source, null);

        while (!queue.isEmpty()) {
            GraphNode node = queue.poll();
            visited.add(node);

            int distance = distanceMap.get(node);

            for(GraphNode adjacent : node.getNeighbours().keySet()) {
                if(!visited.contains(adjacent)) {
                    distanceMap.put(adjacent, distance + 1);
                    previousMap.put(adjacent, node);
                    queue.add(adjacent);
                }
            }
        }

        return new SearchResult(source, previousMap, distanceMap, visited);
    }


}
