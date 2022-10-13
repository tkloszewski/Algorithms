package com.smart.tkl.combinatorics;

public class CombinationGeneratorFactory {

    public static void main(String[] args) {
        CombinationGenerator<Integer> combinationGenerator = newNaturalNumbersGenerator(6,4);
        combinationGenerator.generate();
    }

    public static CombinationGenerator<Integer> newNaturalNumbersGenerator(int n, int k) {
        return new CombinationGenerator<>(newNaturalRange(n), k, new CombinationPrinter());
    }

    private static Integer[] newNaturalRange(int length) {
        return newRange(1, length);
    }

    private static Integer[] newRange(int from, int to) {
        Integer[] t = new Integer[to - from + 1];
        for(int i = from, j=0; i<= to; i++) {
            t[j++] = i;
        }
        return t;
    }
}
