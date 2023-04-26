package com.smart.tkl.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algorithm {

    private static int permutationCount = 0;
    private static int invocationCount = 0;

    public static void main(String[] args) {
        int[] t = newRange(1, 100);

        System.out.println("Some value: " + someValue(84));

        System.out.println("Customized combinations...");
        long time2 = System.currentTimeMillis();
        List<int[]> combinations = generateCombinations2(t, 99);
        long duration2 = System.currentTimeMillis() - time2;
        System.out.println("Duration in miliseconds: " + duration2);
        printCombinations(combinations);

        int paths = countPathInRect(5, 5);
        System.out.println("Number of paths: " + paths);

    }

    private static int someValue(int i) {
        if(i >= 1000) {
           return i - 3;
        }
        return someValue(someValue(i + 5));
    }

    private static List<int[]> generateCombinations1(int[] input, int length) {
        invocationCount = 1;
        List<int[]> combinations = new ArrayList<>();
        int[] data = new int[length];
        addCombinations(combinations, input, data, 0, input.length - 1, 0);
        System.out.println("Invocations count: " + invocationCount);
        return combinations;
    }

    private static List<int[]> generateCombinations2(int[] input, int length) {
        invocationCount = 1;
        List<int[]> combinations = new ArrayList<>();
        int[] data = new int[length];
        addCombinations(combinations, input, data, length, 0, input.length - 1, 0);
        System.out.println("Invocations count: " + invocationCount);
        return combinations;
    }

    private static void addCombinations(List<int[]> combinations, int[] input, int[] data, int start, int end, int index) {
        if(index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        }
        else if(start <= end) {
            invocationCount += 2;
            data[index] = input[start];
            addCombinations(combinations, input, data, start + 1, end, index + 1);
            addCombinations(combinations, input, data, start + 1, end, index);
        }
    }

    private static void addCombinations(List<int[]> combinations, int[] input, int[] data, int elementsLeft, int start, int end, int index) {
        if(index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        }
        else if(end - start + 1 == elementsLeft) {
            int i = 0;
            while (i < elementsLeft) {
                data[index + i] = input[start + i];
                i++;
            }
            int[] combination = data.clone();
            combinations.add(combination);
        }
        else if(start <= end) {
            invocationCount += 2;
            data[index] = input[start];
            addCombinations(combinations, input, data, elementsLeft - 1,start + 1, end, index + 1);
            addCombinations(combinations, input, data, elementsLeft, start + 1, end, index);
        }
    }

    private static List<int[]> generateIterative(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[r];

        // initialize with lowest lexicographic combination
        for (int i = 0; i < r; i++) {
            combination[i] = i;
        }

        while (combination[r - 1] < n) {
            System.out.println(Arrays.toString(combination));
            combinations.add(combination.clone());

            // generate next combination in lexicographic order
            int t = r - 1;
            while (t != 0 && combination[t] == n - r + t) {
                t--;
            }
            combination[t]++;
            for (int i = t + 1; i < r; i++) {
                combination[i] = combination[i - 1] + 1;
            }
        }

        return combinations;
    }

    private static void printCombinations(List<int[]> combinations) {
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }

    private static void printPermutation(int[] t) {
        permutationCount = 0;
        permutation(t, 0);
        System.out.println("Permutation count: " + permutationCount);
    }

    private static void printVariation(int[] t, int k) {
        variation(t, k ,k);
    }

    private static void printVariationWithRepetition(int[] t, int k) {
        int[] values = Arrays.copyOf(t, t.length);
        variationWithRepetition(t, values, k, k);
    }

    private static void variation(int[] t, int k, int elementsLeft) {
        int pos = k - elementsLeft;
        if(elementsLeft == 1) {
            printTable(t, pos);
            for(int i = pos + 1; i< t.length ; i++) {
                swap(t, pos, i);
                printTable(t, pos);
                swap(t, pos, i);
            }
        }
        else {
            for(int i = pos; i< t.length ; i++) {
                swap(t, pos, i);
                variation(t, k , elementsLeft - 1);
                swap(t, pos, i);
            }
        }
    }

    private static void variationWithRepetition(int[] t, int[] values, int k, int elementsLeft) {
        int pos = k - elementsLeft;
        if(elementsLeft == 1) {
            for (int value : values) {
                t[pos] = value;
                printTable(t, pos);
            }
        }
        else {
            for (int value : values) {
                t[pos] = value;
                variationWithRepetition(t, values, k, elementsLeft - 1);
            }
        }
    }

    private static void permutation(int[] t, int pos) {
        if(t.length == 1) {
            printTable(t);
        }
        else if(pos == t.length - 2) {
            printTable(t);
            swap(t, pos, pos + 1);
            printTable(t);
            swap(t, pos, pos + 1);
            permutationCount += 2;
        }
        else {
            permutation(t, pos + 1);
            for(int i = pos + 1; i < t.length; i++) {
                swap(t, pos, i);
                permutation(t, pos + 1);
                swap(t, pos, i);
            }
        }
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    private static int[] newRange(int from, int to) {
        int[] t = new int[to - from + 1];
        for(int i = from, j=0; i<= to; i++) {
            t[j++] = i;
        }
        return t;
    }

    private static void printTable(int[] t) {
        printTable(t, t.length - 1);
    }

    private static void printTable(int[] t, int endIdx) {
        for(int i = 0; i <= endIdx; i++) {
            System.out.print(t[i] + " ");
        }
        System.out.print("\n");
    }

    private static int countPathInRect(int rows, int cols) {
        if(rows == 1) {
            return cols + 1;
        }
        if(cols == 1) {
            return rows + 1;
        }
        return countPathInRect(rows, cols - 1) + countPathInRect(rows - 1, cols);
    }

}
// 1 3 5 7 9
// 4 6
