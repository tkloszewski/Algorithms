package com.smart.tkl.graph.triangle;

import java.util.*;

public class TrianglePathFinder {

    private final TriangleNode top;
    Map<TriangleNode, CostEntry> costMap;

    public TrianglePathFinder(TriangleNode top) {
        this.top = top;
    }

    public long findBiggestCostPath() {
        this.costMap = initCostMap();
        fillCostMap(costMap);
        Map.Entry<TriangleNode, CostEntry> biggestCostEntry = findBiggestCostEntry(this.costMap);
        List<TriangleNode> path = new LinkedList<>();
        path.add(biggestCostEntry.getKey());
        CostEntry costEntry = biggestCostEntry.getValue();
        while(costEntry != null && costEntry.parent != null) {
            path.add(costEntry.parent);
            costEntry = this.costMap.get(costEntry.parent);
        }

        printPath(path);

        return biggestCostEntry.getValue().cost;
    }

    private Map<TriangleNode, CostEntry> initCostMap() {
        Map<TriangleNode, CostEntry> costMap = new LinkedHashMap<>();
        Set<TriangleNode> allNodes = getAllNodes(this.top);
        for(TriangleNode node : allNodes) {
            if(!node.equals(this.top)) {
                costMap.put(node, new CostEntry(Integer.MIN_VALUE));
            }
        }
        costMap.put(this.top, new CostEntry(this.top.getValue()));
        return costMap;
    }

    private Map<TriangleNode, CostEntry> fillCostMap(Map<TriangleNode, CostEntry> costMap) {
        TriangleNode currentNode = top;

        int i = 0;
        while (currentNode != null) {
            CostEntry costEntry = costMap.get(currentNode);
            long cost = costEntry.cost;
            for(TriangleNode n : currentNode.getAdjacent()) {
                long newCost = cost + n.getValue();
                CostEntry nEntry = costMap.get(n);
                if(newCost > nEntry.cost) {
                    costMap.put(n, new CostEntry(newCost, currentNode));
                }
            }

            costEntry.processed = true;
            currentNode = findBiggestUnprocessedCostNode(costMap);
        }

        return costMap;
    }

    private Map.Entry<TriangleNode, CostEntry> findBiggestCostEntry(Map<TriangleNode, CostEntry> costMap) {
        long maxCost = Integer.MIN_VALUE;
        Map.Entry<TriangleNode, CostEntry> biggestCostEntry = null;
        for(Map.Entry<TriangleNode, CostEntry> entry : costMap.entrySet()) {
            if(entry.getValue().cost > maxCost) {
                maxCost = entry.getValue().cost;
                biggestCostEntry = entry;
            }
        }
        return biggestCostEntry;
    }

    private TriangleNode findBiggestUnprocessedCostNode(Map<TriangleNode, CostEntry> costMap) {
        long maxCost = Integer.MIN_VALUE;
        TriangleNode biggestCostNode = null;
        for(Map.Entry<TriangleNode, CostEntry> entry : costMap.entrySet()) {
            if(!entry.getValue().processed && entry.getValue().cost > maxCost) {
                maxCost = entry.getValue().cost;
                biggestCostNode = entry.getKey();
            }
        }
        return biggestCostNode;
    }

    private Set<TriangleNode> getAllNodes(TriangleNode start) {
        Set<TriangleNode> result = new LinkedHashSet<>();

        Queue<TriangleNode> queue = new LinkedList<>();
        TriangleNode node = start;

        while (node != null) {
            if (!result.contains(node)) {
                queue.addAll(node.getAdjacent());
                result.add(node);
            }
            node = queue.poll();
        }

        return result;
    }

    private void printPath(List<TriangleNode> path) {
        StringBuilder out = new StringBuilder();
        for(int i = 0; i <= path.size() - 2; i++) {
            out.append(path.get(i)).append(" => ");
        }
        out.append(path.get(path.size() - 1));
        System.out.println(out);
    }

    private static class CostEntry {
        long cost;
        TriangleNode parent;
        boolean processed;

        public CostEntry(long cost) {
            this.cost = cost;
        }

        public CostEntry(long cost, TriangleNode parent) {
            this.cost = cost;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "" + cost +
                    ", " + parent +
                    ", " + processed +
                    '}';
        }
    }
}
