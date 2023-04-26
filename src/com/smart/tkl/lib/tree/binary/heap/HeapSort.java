package com.smart.tkl.lib.tree.binary.heap;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {
        int[] unsorted = new int[]{1, 3, 5, 4, 9, 14, 19, 21, 24, 27, 35, 2};
        int[] sorted = sortDesc(unsorted);
        int[] sortedAsc = sortAsc(unsorted);
        System.out.println("Sorted desc: " + Arrays.toString(sorted));
        System.out.println("Sorted asc: " + Arrays.toString(sortedAsc));
    }

    public static int[] sortDesc(int[] tab) {
        MaxBinaryHeap<Integer> maxBinaryHeap = new MaxBinaryHeap<>(Integer.class);
        for(int item : tab) {
            maxBinaryHeap.insert(item);
        }
        int[] result = new int[tab.length];
        for(int i = 0; i < tab.length; i++) {
            result[i] = maxBinaryHeap.deleteFirst();
        }
        return result;
    }

    public static int[] sortAsc(int[] tab) {
        MinBinaryHeap<Integer> minBinaryHeap = new MinBinaryHeap<>(Integer.class);
        for(int item : tab) {
            minBinaryHeap.insert(item);
        }
        int[] result = new int[tab.length];
        for(int i = 0; i < tab.length; i++) {
            result[i] = minBinaryHeap.deleteFirst();
        }
        return result;
    }
}
