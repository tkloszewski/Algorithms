package com.smart.tkl.lib.tree.binary;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTreeTraversal<V extends Comparable<V>> implements BinaryTreeTraversal<V> {

    @Override
    public List<BiNode<V>> visitAll(BiNode<V> root) {
        List<BiNode<V>> result = new ArrayList<>();
        doVisitAll(root, result);
        return result;
    }

    private void doVisitAll(BiNode<V> node, List<BiNode<V>> visited) {
        if(node.getLeft() != null) {
            doVisitAll(node.getLeft(), visited);
        }
        if(node.getRight() != null) {
            doVisitAll(node.getRight(), visited);
        }
        visited.add(node);
    }
}
