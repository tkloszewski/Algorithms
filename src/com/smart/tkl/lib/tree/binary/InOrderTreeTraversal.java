package com.smart.tkl.lib.tree.binary;

import java.util.LinkedList;
import java.util.List;

public class InOrderTreeTraversal<V extends Comparable<V>> implements BinaryTreeTraversal<V> {

    @Override
    public List<BiNode<V>> visitAll(BiNode<V> node) {
        List<BiNode<V>> result = new LinkedList<>();
        doVisitAll(node, result);
        return result;
    }

    protected void doVisitAll(BiNode<V> currentNode, List<BiNode<V>> visited) {
        if(currentNode.getLeft() != null) {
            doVisitAll(currentNode.getLeft(), visited);
        }
        visited.add(currentNode);
        if(currentNode.getRight() != null) {
            doVisitAll(currentNode.getRight(), visited);
        }
    }
}
