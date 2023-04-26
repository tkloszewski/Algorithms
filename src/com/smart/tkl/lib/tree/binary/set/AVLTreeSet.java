package com.smart.tkl.lib.tree.binary.set;

import com.smart.tkl.lib.tree.binary.BiNode;
import com.smart.tkl.lib.tree.binary.BinarySearchTree;
import com.smart.tkl.lib.tree.binary.TraversalOrder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AVLTreeSet<V extends Comparable<V>> implements Set<V> {

    private BinarySearchTree<V> tree;

    public AVLTreeSet() {
        tree = new BinarySearchTree<V>();
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.getRoot() == null;
    }

    @Override
    public boolean contains(Object o) {
        if(!(o instanceof Comparable)) {
            return false;
        }

        V key = (V) o;

        BiNode<V> node = tree.find(key);
        return node != null;
    }

    @Override
    public Iterator<V> iterator() {
        return new TreeIterator<>(tree);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        List<BiNode<V>> nodes = tree.getAll(TraversalOrder.IN_ORDER_DEPTH_FIRST);
        Object[] result = new Object[nodes.size()];
        int i = 0;
        for(BiNode<V> node : nodes) {
            result[i++] = node.getItem();
        }
        return (T[])result;
    }

    @Override
    public boolean add(V v) {
        BiNode<V> node = tree.find(v);
        if(node == null) {
           tree.insert(v);
        }
        return node == null;
    }

    @Override
    public boolean remove(Object o) {
        if(!(o instanceof Comparable)) {
            return false;
        }

        V key = (V) o;
        BiNode<V> node = tree.find(key);
        if(node == null) {
           return false;
        }
        tree.delete(key);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends V> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        tree = new BinarySearchTree<>();
    }
}
