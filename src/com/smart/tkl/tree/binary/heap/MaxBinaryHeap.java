package com.smart.tkl.tree.binary.heap;

public class MaxBinaryHeap<V extends Comparable<V>> extends BinaryHeap<V> {

    public MaxBinaryHeap(Class<V> clazz) {
        super(clazz);
        this.array = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    protected void heapifyUp(int i) {
        int parent = getParent(i);
        while (parent >= 0 && array[i].compareTo(array[parent]) > 0) {
            swapItem(i, parent);
            i = parent;
            parent = getParent(parent);
        }
    }

    protected void heapifyDown(int i) {
        int left = getLeft(i);
        int right = getRight(i);

        V item = array[i];

        int largest = left != -1 ? array[left].compareTo(item) > 0 ? left : i : i;
        largest = right != -1 ? array[right].compareTo(array[largest]) > 0 ? right : largest : largest;

        if (largest != i) {
            swapItem(i, largest);
            heapifyDown(largest);
        }
    }
}
