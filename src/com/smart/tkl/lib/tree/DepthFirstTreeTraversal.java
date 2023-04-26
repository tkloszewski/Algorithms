package com.smart.tkl.lib.tree;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstTreeTraversal implements TreeTraversal {

    @Override
    public <V extends Comparable<V>> List<Node<V>> visitAll(Node<V> root) {
        List<Node<V>> visited = new ArrayList<>();
        visitAll(root, visited);
        return visited;
    }

    private <V extends Comparable<V>> void visitAll(Node<V> current, List<Node<V>> visited) {
        visited.add(current);
        if(current.getChildren() != null) {
           for(Node<V> child : current.getChildren()) {
               visitAll(child, visited);
           }
        }
    }
}
