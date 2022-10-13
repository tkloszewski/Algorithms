package com.smart.tkl.combinatorics;

public class VariationUtilsTest {

    public static void main(String[] args) {
        int[] t = newRange(1, 5);
        int[] t1 = newRange(1, 9);

        System.out.println("Variations without repetitions 5/3:");
        VariationsUtils.printVariation(t, 3);

        System.out.println("\nVariations without repetitions 5/5(Permutation):");
        VariationsUtils.printVariation(t, 5);

        System.out.println("\nVariations without repetitions 5/1:");
        VariationsUtils.printVariation(t, 1);

        System.out.println("\nVariations with repetitions 5/2:");
        VariationsUtils.printVariationWithRepetition(t, 2);

        System.out.println("\nVariations without repetitions 3/3(Permutation):");
        t = newRange(1, 3);
        VariationsUtils.printVariation(t, t.length);
    }


    private static int[] newRange(int from, int to) {
        int[] t = new int[to - from + 1];
        for(int i = from, j=0; i<= to; i++) {
            t[j++] = i;
        }
        return t;
    }
}
