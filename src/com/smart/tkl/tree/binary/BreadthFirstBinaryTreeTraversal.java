package com.smart.tkl.tree.binary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstBinaryTreeTraversal<V extends Comparable<V>> implements BinaryTreeTraversal<V> {

    @Override
    public List<BiNode<V>> visitAll(BiNode<V> root) {
        List<BiNode<V>> result = new ArrayList<>();
        Queue<BiNode<V>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            BiNode<V> node = queue.poll();
            if(node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if(node.getRight() != null) {
                queue.add(node.getRight());
            }
            result.add(node);
        }

        return result;
    }
}
