package com.smart.tkl;

import java.util.Arrays;

public class VariationsUtils {

    public static void main(String[] args) {
        int t[] = new int[]{1, 2, 3};
        swap(t, 0, 2);
        printTable(t);
    }

    public static void printVariation(int[] t, int k) {
        variation(t, k ,k);
    }

    public static void printVariationWithRepetition(int[] t, int k) {
        int[] values = Arrays.copyOf(t, t.length);
        variationWithRepetition(t, values, k, k);
    }

    private static void variation(int[] t, int k, int elementsLeft) {
        int pos = k - elementsLeft;
        if(elementsLeft == 1) {
            for(int i = pos; i< t.length ; i++) {
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

    private static void swap(int[] t, int i, int j) {
        if(i == j){
            return;
        }
        t[i] = t[i] ^ t[j];
        t[j] = t[i] ^ t[j];
        t[i] = t[i] ^ t[j];
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
}
