package com.smart.tkl.euler.p219;

public class MinHeap extends Heap {

    public MinHeap() {
        this.array = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }

    public MinHeap(int initialSize) {
        this.array = new Long[initialSize];
    }

    protected void heapifyUp(int i) {
        int parent = getParent(i);
        while (parent >= 0 && array[i] < array[parent]) {
            swapItem(i, parent);
            i = parent;
            parent = getParent(parent);
        }
    }

    protected void heapifyDown(int i) {
        int left = getLeft(i);
        int right = getRight(i);

        Long item = array[i];
        int smallest = left != -1 ? array[left] < item ? left : i : i;
        smallest = right != -1 ?  array[right] < array[smallest] ? right : smallest : smallest;

        if (smallest != i) {
            swapItem(i, smallest);
            heapifyDown(smallest);
        }
    }
}
