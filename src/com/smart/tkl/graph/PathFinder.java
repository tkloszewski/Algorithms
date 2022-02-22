package com.smart.tkl.graph;

import com.smart.tkl.Stack;

import java.util.*;

public class PathFinder {

    private final GraphNode start;
    private final GraphNode end;

    public PathFinder(GraphNode start, GraphNode end) {
        this.start = start;
        this.end = end;
    }

    public PathResult findPath() {
        Set<GraphNode> allNodes = getAllNodes(this.start);
        if(!allNodes.contains(this.end)) {
            return null;
        }

        Map<GraphNode, PathEntry> costMap = initAndFillCostMap(allNodes);
        return getShortestPath(costMap);
    }

    private Map<GraphNode, PathEntry> initAndFillCostMap(Set<GraphNode> allNodes) {
        Map<GraphNode, PathEntry> costMap = initCostMap(allNodes);
        return fillCostMap(costMap);
    }

    private PathResult getShortestPath(Map<GraphNode, PathEntry> costMap) {
        List<GraphNode> pathNodes = new LinkedList<>();

        Stack<GraphNode> path = new Stack<>();
        GraphNode node = this.end;
        path.push(node);

        while(!node.equals(this.start)) {
            node = costMap.get(node).parent;
            path.push(node);
        }

        while (!path.isEmpty()) {
            pathNodes.add(path.pop());
        }

        PathResult pathResult = new PathResult(pathNodes);
        pathResult.setCost(costMap.get(this.end).cost);

        return pathResult;
    }

    private Map<GraphNode, PathEntry> initCostMap(Set<GraphNode> allNodes) {
        Map<GraphNode, PathEntry> costMap = new LinkedHashMap<>();

        for(GraphNode node : allNodes) {
            if(!node.equals(this.start)) {
                costMap.put(node, new PathEntry(Integer.MAX_VALUE));
            }
        }
        costMap.put(start, new PathEntry(0));
        return costMap;
    }

    private Map<GraphNode, PathEntry> fillCostMap(Map<GraphNode, PathEntry> costMap) {
        GraphNode currentNode = start;

        while (currentNode != null) {
            PathEntry pathEntry = costMap.get(currentNode);
            Integer cost = pathEntry.cost;
            Map<GraphNode, Integer> neighbours = currentNode.getNeighbours();
            for(GraphNode n : neighbours.keySet()) {
                Integer newCost = cost + neighbours.get(n);
                PathEntry nEntry = costMap.get(n);
                if(newCost < nEntry.cost) {
                    costMap.put(n, new PathEntry(newCost, currentNode));
                }
            }
            pathEntry.processed = true;
            currentNode = findLowestCostNode(costMap);
        }

        return costMap;
    }

    private GraphNode findLowestCostNode(Map<GraphNode, PathEntry> costsMap) {
        int minCost = Integer.MAX_VALUE;
        GraphNode lowestCostNode = null;
        for(Map.Entry<GraphNode, PathEntry> entry : costsMap.entrySet()) {
            if(!entry.getValue().processed && entry.getValue().cost < minCost) {
                minCost = entry.getValue().cost;
                lowestCostNode = entry.getKey();
            }
        }
        return lowestCostNode;
    }

    private Set<GraphNode> getAllNodes(GraphNode start) {
        Set<GraphNode> result = new LinkedHashSet<>();

        Queue<GraphNode> queue = new LinkedList<>();
        GraphNode node = start;

        while (node != null) {
            for(GraphNode n : node.getNeighbours().keySet()) {
                if(!result.contains(n)) {
                    queue.add(n);
                }
            }
            result.add(node);
            node = queue.poll();
        }

        return result;
    }

    private static class PathEntry {
        Integer cost;
        GraphNode parent;
        boolean processed;

        public PathEntry() {
        }

        public PathEntry(Integer cost) {
            this.cost = cost;
        }

        public PathEntry(Integer cost, GraphNode parent) {
            this.cost = cost;
            this.parent = parent;
        }
    }
}
