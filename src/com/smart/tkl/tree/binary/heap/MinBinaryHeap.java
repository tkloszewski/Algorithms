package com.smart.tkl.tree.binary.heap;

public class MinBinaryHeap<V extends Comparable<V>> extends BinaryHeap<V> {

    public MinBinaryHeap(Class<V> clazz) {
        super(clazz);
        this.array = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    protected void heapifyUp(int i) {
        int parent = getParent(i);
        while (parent >= 0 && array[i].compareTo(array[parent]) < 0) {
            swapItem(i, parent);
            i = parent;
            parent = getParent(parent);
        }
    }

    protected void heapifyDown(int i) {
        int left = getLeft(i);
        int right = getRight(i);

        V item = array[i];
        int smallest = left != -1 ? array[left].compareTo(item) < 0 ? left : i : i;
        smallest = right != -1 ?  array[right].compareTo(array[smallest]) < 0 ? right : smallest : smallest;

        if (smallest != i) {
            swapItem(i, smallest);
            heapifyDown(smallest);
        }
    }
}
