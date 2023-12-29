package com.smart.tkl.lib.graph.coloring;

public class ColoredGraph {

    private int vertices;
    private int edges;
    private Node[][] adj;
    private Node endRow;

    private ColoredGraph(int vertices, int edges, Node[][] adj, Node endRow) {
        this.vertices = vertices;
        this.edges = edges;
        this.adj = adj;
        this.endRow = endRow;
    }

    private Node[][] initAdjacencyMatrix(int[][] adj) {
        int verticesCount = adj.length;
        Node[][] result = new Node[adj.length][adj.length];

        Node prevRow = null;
        for(int vertex1 = 0; vertex1 < verticesCount; vertex1++) {
            Node node = new Node(vertex1);
            Node current = node;
            for(int vertex2 = vertex1 + 1; vertex2 < verticesCount; vertex2++) {
                if(adj[vertex1][vertex2] == 1) {
                    Node newNode = new Node(vertex2);
                    newNode.prevCol = current;
                    current.nextCol = newNode;
                    current = newNode;
                    edges++;
                }
            }
            result[vertex1][vertex1] = node;
            node.prevRow = prevRow;
            if (prevRow != null) {
                prevRow.nextRow = node;
            }
            prevRow = node;
        }

        return result;
    }

    private ColoredGraph removeEdge(int from, int to) {
        Node node = adj[from][to];
        node.prevCol = node.nextCol;
        adj[from][to] = null;
        return new ColoredGraph(vertices, edges - 1, adj, endRow);
    }

    private ColoredGraph contract(int from, int to) {
        Node node = adj[from][to];
        Node current = adj[0][0];
        while (current != null) {
            if(current.vertex != from && adj[current.vertex][from] == null && adj[current.vertex][to] != null) {
                Node newNode = new Node(from);
                for(int i = from + 1; i < adj.length; i++) {
                    Node toJoin = adj[current.vertex][i];
                    if(toJoin != null) {
                       Node toJoinPrev = toJoin.prevCol;
                       if(toJoinPrev != null) {
                          toJoinPrev.nextCol = newNode;
                          newNode.prevCol = toJoinPrev;
                       }
                       newNode.nextCol = toJoin;
                       toJoin.prevCol = newNode;
                       break;
                    }
                }
                Node toRemove = adj[current.vertex][to];
                if(toRemove.prevCol != null) {
                   toRemove.prevCol.nextCol = toRemove.nextCol;
                }
                if(toRemove.nextCol != null) {
                   toRemove.nextCol.prevCol = toRemove.prevCol;
                }
                adj[current.vertex][to] = null;
                adj[current.vertex][from] = newNode;
            }
            current = current.nextRow;
        }
        return new ColoredGraph(vertices - 1, edges - 1, adj, endRow);
    }

    private boolean isTree() {
        Node node = endRow;
        while (node.prevRow != null) {
            int vertex = node.vertex;
            int parents = 0;
            Node currentRow = node.prevRow;
            while (currentRow != null) {
                if(adj[currentRow.vertex][vertex] != null && ++parents > 1) {
                    return false;
                }
                currentRow = currentRow.prevRow;
            }
            node = node.prevRow;
        }
        return true;
    }

    private static class Node {
        int vertex;
        Node nextCol;
        Node prevCol;
        Node nextRow;
        Node prevRow;

        public Node(int vertex) {
            this.vertex = vertex;
        }
    }
}
