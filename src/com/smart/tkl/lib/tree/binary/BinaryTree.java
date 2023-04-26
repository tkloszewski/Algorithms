package com.smart.tkl.lib.tree.binary;

import java.util.List;

public interface BinaryTree<V extends Comparable<V>> {

    BiNode<V> getRoot();

    BiNode<V> insert(V item);

    void insertAfter(BiNode<V> node, BiNode<V> newNode);

    void insertBefore(BiNode<V> node, BiNode<V> newNode);

    void delete(BiNode<V> node);

    void delete(V item);

    List<BiNode<V>> getAll();

    List<BiNode<V>> getAll(TraversalOrder order);

    BiNode<V> getSuccessor(BiNode<V> node);

    BiNode<V> getPredecessor(BiNode<V> node);

    BiNode<V> find(V key);

    BiNode<V> findAt(int i);

    BiNode<V> getFirst();

    BiNode<V> getLast();

    boolean isEmpty();

    int size();

    int getMaxLevel();
}
