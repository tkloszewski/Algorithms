package com.smart.tkl.lib.tree.binary;

import java.util.List;

public class BinaryTreeBuilder<V extends Comparable<V>> {


    public BiNode<V> build(List<V> list) {
        if(list.isEmpty()) {
            return null;
        }
        BiNode<V> root = new BiNode<>(list.get(0));
        for(int i = 1; i< list.size(); i++) {
            addNext(root, list.get(i));
        }
        return root;
    }

    private BiNode<V> addNext(BiNode<V> root, V next) {
        BiNode<V> curr = root;
        BiNode<V> prevCurr = root;

        BiNode<V> newNode = new BiNode<>(next);

        while(curr != null) {
            prevCurr = curr;
            if(next.compareTo(curr.getItem()) < 0) {
                curr = curr.getLeft();
            }
            else {
                curr = curr.getRight();
            }
        }
        if(prevCurr != null) {
            if (next.compareTo(prevCurr.getItem()) < 0) {
                prevCurr.setLeft(newNode);
            }
            else {
                prevCurr.setRight(newNode);
            }
        }

        return root;
    }
}
