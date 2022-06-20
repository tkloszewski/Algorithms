package com.smart.tkl.tree.binary.heap.queue;

import com.smart.tkl.tree.binary.heap.MinBinaryHeap;

public class MinChangeablePriorityQueue<V extends Comparable<V>> extends MinBinaryHeap<V> implements ChangeablePriorityQueue<V> {

    public MinChangeablePriorityQueue(Class<V> clazz) {
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
          heapifyDown(index);
        }
        else {
          heapifyUp(index);
        }
    }
}
