package com.smart.tkl.lib;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortingUtils {

    public static int[] mergeSort(int[] input) {
        mergeSort(input, 0, input.length - 1);
        return input;
    }

    public static List<Integer> mergeSort(List<Integer> list) {
        int[] input = toPrimitiveArray(list);
        mergeSort(input, 0, input.length - 1);
        return toList(input);
    }

    public static List<Integer> mergeSortIterative(List<Integer> list) {
        int[] input = toPrimitiveArray(list);
        mergeSortIterative(input);
        return toList(input);
    }

    public static List<Integer> insertionSort(List<Integer> list) {
        int[] sorted = insertionSort(toPrimitiveArray(list));
        return toList(sorted);
    }

    public static List<Integer> selectionSort(List<Integer> list) {
        int[] sorted = selectionSort(toPrimitiveArray(list));
        return toList(sorted);
    }

    public static List<Integer> bubbleSort(List<Integer> list) {
        int[] sorted = bubbleSort(toPrimitiveArray(list));
        return toList(sorted);
    }

    public static List<Integer> quickSort(List<Integer> list) {
        int[] sorted = doQuickSort(toPrimitiveArray(list), 0, list.size() - 1);
        return toList(sorted);
    }

    private static int[] doQuickSort(int[] t, int left, int right) {
        if(left >= right) {
           return t;
        }
        int pivot = t[right];
        int i = left - 1;
        for(int j = left ; j< right; j++) {
            if(t[j] <= pivot) {
                i++;
                swap(t, i, j);
            }
        }
        swap(t, i + 1, right);
        if(left < i) {
            doQuickSort(t, left, i);
        }
        if(right > i + 1) {
            doQuickSort(t, i + 1, right);
        }
        return t;
    }

    public static int[] bubbleSort(int[] t) {
        for(int i = 0; i < t.length; i++) {
            for(int j = i + 1; j < t.length; j++) {
                if(t[i] > t[j]) {
                   swap(t, i , j);
                }
            }
        }
        return t;
    }

    public static int[] selectionSort(int[] t) {
        for(int i = 0; i < t.length; i++) {
            int smallest = smallestIdx(t, i, t.length - 1);
            swap(t, i ,smallest);
        }
        return t;
    }

    public static int[] insertionSort(int[] t) {
        int[] sortedTable = new int[t.length];
        Arrays.fill(sortedTable, Integer.MAX_VALUE);
        for(int i = 0, j = 0; i < t.length; i++, j=0) {
            while(t[i] > sortedTable[j]) {
                j++;
            }
            for(int k = i; k > j; k--) {
                sortedTable[k] = sortedTable[k - 1];
            }
            sortedTable[j] = t[i];
        }
        return sortedTable;
    }

    private static int[] insertionSort(int[] t, int pos) {
        t = Arrays.copyOfRange(t, pos, t.length - 1);
        int[] sortedTable = new int[t.length - pos];
        Arrays.fill(sortedTable, Integer.MAX_VALUE);
        for(int i = pos, j = 0; i < t.length; i++, j=0) {
            while(t[i] > sortedTable[j]) {
                j++;
            }
            for(int k = i; k > j; k--) {
                sortedTable[k] = sortedTable[k - 1];
            }
            sortedTable[j] = t[i];
        }
        return sortedTable;
    }

    private static void mergeSort(int[] t, int left, int right) {
        if(left < right) {
            int middle = (left + right) / 2;
            mergeSort(t, left, middle);
            mergeSort(t, middle + 1, right);
            merge(t, left, middle, right);
        }
    }

    private static void mergeSortIterative(int[] t) {
        int len = 1;

        while(len <= t.length) {
            int left = 0;
            while(left < t.length) {
                int right = left + len - 1;
                if(right > t.length - 1) {
                   break;
                }
                merge(t, left, (left + right)/2, right);
                left = right + 1;
            }
            len = len * 2;
        }

        len = len/2;

        //merge leftover chunks of different 2^i lengths less than t.length/2
        if(len * 2 > t.length && len < t.length) {
            len = len / 2;
            for(int middle = len - 1; middle < t.length - 1 && len > 0;) {
                if(middle + len >= t.length) {
                   len = len / 2;
                }
                else {
                    merge(t, 0, middle, middle + len);
                    middle = middle + len;
                }
            }
        }
    }

    private static void merge(int[] t, int left, int middle, int right) {
        if(left >= right) {
           return;
        }

        int leftSize = middle - left + 1;
        int rightSize = right - middle;
        int[] leftT = new int[leftSize];
        int[] rightT = new int[rightSize];

        for(int i = 0; i< leftSize; i++) {
            leftT[i] = t[left + i];
        }
        for(int i = 0; i < rightSize; i++) {
            rightT[i] = t[middle + 1 + i];
        }

        int i = 0,j = 0;
        int pos = left;
        while(i < leftSize && j < rightSize) {
            if(leftT[i] < rightT[j]) {
                t[pos++] = leftT[i++];
            }
            else {
                t[pos++] = rightT[j++];
            }
        }
        while(i < leftSize) {
            t[pos++] = leftT[i++];
        }
        while(j < rightSize) {
            t[pos++] = rightT[j++];
        }
    }

    private static int smallestIdx(int[] t, int from, int to) {
        int min = Integer.MAX_VALUE;
        int result = from;
        for(int i = from; i <= to; i++) {
            if(t[i] < min) {
               min = t[i];
               result = i;
            }
        }
        return result;
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    private static List<Integer> toList(int[] input) {
       return Arrays.stream(input).boxed().collect(Collectors.toList());
    }

    private static int[] toPrimitiveArray(List<Integer> input) {
        return input.stream().mapToInt(Integer::intValue).toArray();
    }
}
