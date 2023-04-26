package com.smart.tkl.lib.tree.binary.set;

import com.smart.tkl.lib.tree.binary.BiNode;
import com.smart.tkl.lib.tree.binary.BinarySearchTree;

import java.util.Iterator;

public class TreeIterator<V extends Comparable<V>> implements Iterator<V> {

    private BinarySearchTree<V> tree;
    private BiNode<V> next;

    public TreeIterator(BinarySearchTree<V> tree) {
        this.tree = tree;
        next = tree.getFirst();
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public V next() {
        if(next == null) {
           return null;
        }
        V result = next.getItem();
        next = tree.getSuccessor(next);
        return result;
    }
}
