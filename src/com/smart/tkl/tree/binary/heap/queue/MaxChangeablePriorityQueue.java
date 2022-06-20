package com.smart.tkl.tree.binary.heap.queue;

import com.smart.tkl.tree.binary.heap.MaxBinaryHeap;

public class MaxChangeablePriorityQueue<V extends Comparable<V>> extends MaxBinaryHeap<V> implements ChangeablePriorityQueue<V> {

    public MaxChangeablePriorityQueue(Class<V> clazz) {
        super(clazz);
    }

    @Override
    public void updateValue(V oldValue, V newValue) {
        int index = indexOf(oldValue);
        if(index == -1) {
            return;
        }
        int compare = newValue.compareTo(oldValue);
        if(compare == 0) {
            return;
        }
        array[index] = newValue;
        if(compare > 0) {
            heapifyUp(index);
        }
        else {
            heapifyDown(index);
        }
    }
}
