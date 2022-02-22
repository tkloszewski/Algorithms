package com.smart.tkl.tree.binary;

public class MaxBinaryHeap<V extends Comparable<V>> extends BinaryHeap<V> {

    public MaxBinaryHeap() {
        this.array = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    @SuppressWarnings("unchecked")
    protected void heapifyUp(int i) {
        int parent = getParent(i);
        while (parent >= 0 && ((V)(array[i])).compareTo((V)array[parent]) > 0) {
            swapItem(i, parent);
            i = parent;
            parent = getParent(parent);
        }
    }

    @SuppressWarnings("unchecked")
    protected void heapifyDown(int i) {
        int left = getLeft(i);
        int right = getRight(i);

        V item = (V)array[i];

        int largest = left != -1 ? ((V) array[left]).compareTo(item) > 0 ? left : i : i;
        largest = right != -1 ? ((V) array[right]).compareTo((V) array[largest]) > 0 ? right : largest : largest;

        if (largest != i) {
            swapItem(i, largest);
            heapifyDown(largest);
        }
    }
}
