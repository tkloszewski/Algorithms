package com.smart.tkl.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class VariationsUtils {

    public static void main(String[] args) {
        int[] t = new int[]{1, 2, 3};
       // printVariationWithRepetition(t, 3);
        
        int[] signs  = {'+', '-', '*', '/'};
    
        List<int[]> variations = generateVariationWithRepetition(signs, 3);
        System.out.println("Variations size: " + variations.size());
        for(int[] variation : variations) {
            char[] chars = new char[variation.length];
            for(int i = 0; i < variation.length; i++) {
                chars[i] = (char)variation[i];
            }
            System.out.println(Arrays.toString(chars));
        }
    }

    public static void printVariation(int[] t, int k) {
        variation(t, k ,k);
    }
    
    public static List<int[]> generateVariationWithRepetition(int[] t) {        
        return generateVariationWithRepetition(t, t.length);
    }
    
    public static List<int[]> generateVariationWithRepetition(int[] t, int length) {
        List<int[]> variations = new ArrayList<>();
        generateVariationWithRepetition(t, length, variations::add);
        return variations;
    }
    
    public static void generateVariationWithRepetition(int[] t, Consumer<int[]> consumer) {
        generateVariationWithRepetition(t, t.length, consumer);        
    }
    
    public static void generateVariationWithRepetition(int[] t, int length, Consumer<int[]> consumer) {
        int[] values = Arrays.copyOf(t, t.length);
        variationWithRepetition(t, values, length, length, consumer);
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
    
    private static void variationWithRepetition(int[] t, int[] values, int k, int elementsLeft, Consumer<int[]> consumer) {
        int pos = k - elementsLeft;
        if(elementsLeft == 1) {
            for (int value : values) {
                t[pos] = value;
                int[] variation = Arrays.copyOf(t, pos + 1);
                consumer.accept(variation);
            }
        }
        else {
            for (int value : values) {
                t[pos] = value;
                variationWithRepetition(t, values, k, elementsLeft - 1, consumer);
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
