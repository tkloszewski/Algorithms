package com.smart.tkl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SortingUtilsTest {

    public static void main(String[] args) {
        List<Integer> unsorted = toList(new int[]{8, 9, 7, 17, 19, 34, 5, 6, 1, 4, 3, 5, 6, 9, 7});
        List<Integer> mergeSorted = SortingUtils.mergeSort(unsorted);
        List<Integer> mergedSortedIterative = SortingUtils.mergeSortIterative(unsorted);
        List<Integer> insertionSorted = SortingUtils.insertionSort(unsorted);
        List<Integer> selectionSorted = SortingUtils.selectionSort(unsorted);
        List<Integer> bubbleSorted = SortingUtils.bubbleSort(unsorted);
        List<Integer> quickSorted = SortingUtils.quickSort(unsorted);

        System.out.println("Merge sorted...");
        System.out.println(mergeSorted.toString() + "\n");

        System.out.println("Merge sorted iterative...");
        System.out.println(mergedSortedIterative.toString() + "\n");

        System.out.println("Insertion sorted...");
        System.out.println(insertionSorted.toString() + "\n");

        System.out.println("Selection sorted...");
        System.out.println(selectionSorted.toString() + "\n");

        System.out.println("Bubble sorted...");
        System.out.println(bubbleSorted.toString() + "\n");

        System.out.println("Quick sorted...");
        System.out.println(quickSorted.toString() + "\n");
    }

    private static List<Integer> toList(int[] input) {
        return Arrays.stream(input).boxed().collect(Collectors.toList());
    }

    private static void printTable(int[] t) {
        for (int j : t) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
