package com.smart.tkl.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstTreeTraversal implements TreeTraversal {

    @Override
    public <V extends Comparable<V>> List<Node<V>> visitAll(Node<V> root) {
        List<Node<V>> visited = new ArrayList<>();

        Queue<Node<V>> queue = new LinkedList<>();
        Node<V> node = root;

        while (node != null) {
            visited.add(node);
            if(node.getChildren() != null) {
               queue.addAll(node.getChildren());
            }
            node = queue.poll();
        }

        return visited;
    }
}
