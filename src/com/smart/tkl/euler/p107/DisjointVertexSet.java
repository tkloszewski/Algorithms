package com.smart.tkl.euler.p107;

import com.smart.tkl.lib.graph.standard.StandardVertex;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DisjointVertexSet {

    private final Map<StandardVertex, VertexNode> parentMap = new LinkedHashMap<>();

    public DisjointVertexSet(Set<StandardVertex> vertices) {
        for(StandardVertex vertex : vertices) {
            parentMap.put(vertex, new VertexNode(vertex));
        }
    }

    public boolean join(StandardVertex vertex1, StandardVertex vertex2) {
        VertexNode node1 = parentMap.get(vertex1);
        VertexNode node2 = parentMap.get(vertex2);
        VertexNode representative1 = findRepresentative(node1);
        VertexNode representative2 = findRepresentative(node2);

        if(representative1.vertex.equals(representative2.vertex)) {
           return false;
        }

        if(representative1.rank > representative2.rank) {
            representative2.parent = representative1;
        }
        else if(representative1.rank < representative2.rank){
            representative1.parent = representative2;
        }
        else {
            representative2.parent = representative1;
            representative1.rank++;
        }

        return true;
    }

    private VertexNode findRepresentative(VertexNode node) {
        VertexNode root = node;
        VertexNode parent = node.parent;
        if (parent != null) {
            while (parent != null) {
                root = parent;
                parent = parent.parent;
            }
            node.parent = root;
        }
        return root;
    }

    private static class VertexNode {
        StandardVertex vertex;
        VertexNode parent;
        int rank = 0;

        public VertexNode(StandardVertex vertex) {
            this(vertex, null);
        }

        public VertexNode(StandardVertex vertex, VertexNode parent) {
            this.vertex = vertex;
            this.parent = parent;
        }
    }
}
