package com.smart.tkl.lib.tree.binary.heap;

public class MinRawBinaryHeap<V extends Comparable<V>> extends MinBinaryHeap<V> {

    private int largestIdx;

    public MinRawBinaryHeap(Class<V> clazz) {
        super(clazz);
    }

    @Override
    public V deleteFirst() {
        V item = (V)array[0];
        swapItem(0, size - 1);
        array[--size] = null;
        heapifyDown(0);
        return item;
    }

    @Override
    protected void insertLast(V item) {
        ensureExplicitCapacity(calculateCapacity(array, size + 1));
        array[size++] = item;
    }

    @Override
    public void insert(V item) {
        insertLast(item);
        heapifyUp(size - 1);

    }

    protected void swapItem(int i, int j) {
        V temp = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = temp;
    }
}
