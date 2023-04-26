package com.smart.tkl.lib.tree.binary.heap.queue;

public interface ChangeablePriorityQueue<V extends Comparable<V>> {

    V deleteFirst();

    void insert(V item);

    V get(int i);

    int indexOf(V item);

    void updateValue(V oldValue, V newValue);

    boolean isEmpty();

    V get(V itemKey);
}
