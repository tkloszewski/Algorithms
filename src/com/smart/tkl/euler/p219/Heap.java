package com.smart.tkl.euler.p219;

import java.util.Arrays;

public abstract class Heap {

    protected Long[] array;
    protected int size = 0;

    protected static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    protected static final int DEFAULT_CAPACITY = 10;
    protected final Long[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = new Long[DEFAULT_CAPACITY];

    public Long getRoot() {
        return array != null && array.length > 0 ? array[0] : null;
    }

    public Long deleteFirst() {
        Long item = array[0];
        swapItem(0, size - 1);
        array[--size] = null;
        heapifyDown(0);
        return item;
    }

    public int size() {
        return size;
    }

    public void insert(Long item) {
        insertLast(item);
        heapifyUp(size - 1);
    }

    public boolean isEmpty() {
        return array == null || size == 0;
    }

    public Long get(int i) {
        return array[i];
    }

    protected abstract void heapifyUp(int i);

    protected abstract void heapifyDown(int i);

    protected void insertLast(long item) {
        ensureExplicitCapacity(calculateCapacity(array, size + 1));
        array[size++] = item;
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
        long temp = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = temp;
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

    protected int calculateCapacity(Long[] elementData, int minCapacity) {
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
