package com.smart.tkl.tree.binary.heap;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class BinaryHeap<V extends Comparable<V>> {

    protected V[] array;
    protected Map<V, Integer> indexMap = new HashMap<>();
    protected int size = 0;

    protected static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    protected static final int DEFAULT_CAPACITY = 10;
    protected final V[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;

    public BinaryHeap(Class<V> clazz) {
        DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = (V[])Array.newInstance(clazz, 0);
    }

    public V getRoot() {
        return array != null && array.length > 0 ? array[0] : null;
    }

    public V deleteFirst() {
        V item = (V)array[0];
        swapItem(0, size - 1);
        array[--size] = null;
        indexMap.remove(item);
        heapifyDown(0);
        return item;
    }


    public void insert(V item) {
        insertLast(item);
        heapifyUp(size - 1);
    }

    public boolean isEmpty() {
        return array == null || size == 0;
    }

    public V get(int i) {
        return array[i];
    }

    public int indexOf(V item) {
        return indexMap.getOrDefault(item, -1);
    }

    public V get(V itemKey) {
        int index = indexOf(itemKey);
        return index != -1 ? array[index] : null;
    }

    protected abstract void heapifyUp(int i);

    protected abstract void heapifyDown(int i);

    protected void insertLast(V item) {
        ensureExplicitCapacity(calculateCapacity(array, size + 1));
        array[size++] = item;
        indexMap.put(item, size - 1);
    }

    protected int getParent(int i) {
        if(i == 0) {
            return -1;
        }
        int offset = i % 2 == 1 ? 1 : 2;
        return (i - offset) >> 1;
    }

    protected int getRight(int i) {
        int rightIdx = 2 * i + 2;
        return rightIdx < size ? rightIdx : -1;
    }

    protected int getLeft(int i) {
        int leftIdx = (i << 1) + 1;
        return leftIdx < size ? leftIdx : -1;
    }

    protected void swapItem(int i, int j) {
        V temp = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = temp;
        indexMap.put(this.array[i], i);
        indexMap.put(this.array[j], j);
    }

    protected void ensureExplicitCapacity(int minCapacity) {
        if (minCapacity - array.length > 0)
            grow(minCapacity);
    }

    protected void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = array.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        array = Arrays.copyOf(array, newCapacity);
    }

    protected int calculateCapacity(V[] elementData, int minCapacity) {
        if (elementData == DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    protected static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }
}
