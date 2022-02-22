package com.smart.tkl;

import java.util.HashSet;
import java.util.Set;

public class CombinationGenerator<T> {

    private Set<Combination<T>> generatedCombinations = new HashSet<>();

    private T[] data;
    private int length;
    private final CombinationListener combinationListener;

    public CombinationGenerator(T[] data, int length, CombinationListener combinationListener) {
        this.data = data;
        this.length = length;
        this.combinationListener = combinationListener;
    }

    public static void main(String[] args) {
        Integer[] t = new Integer[] {1, 2 ,3, 4};
        CombinationGenerator<Integer> generator = new CombinationGenerator<>(t, 2, new CombinationPrinter());
        generator.generate();
    }
    public void generate() {
        doGenerate(this.data, this.length, this.length);
    }

    private void doGenerate(T[] t, int k, int elementsLeft) {
        int pos = k - elementsLeft;
        if(elementsLeft == 1) {
            notifyIfNotContains(t, pos);
            for(int i = pos + 1; i< t.length ; i++) {
                swap(t, pos, i);
                notifyIfNotContains(t, pos);
                swap(t, pos, i);
            }
        }
        else {
            for(int i = pos; i< t.length ; i++) {
                swap(t, pos, i);
                doGenerate(t, k , elementsLeft - 1);
                swap(t, pos, i);
            }
        }
    }

    private void notifyIfNotContains(T[] t, int pos) {
        Combination<T> combination = new Combination<T>(t, pos);
        if(putIfNotContains(combination)) {
           combinationListener.combinationGenerated(combination);
        }
    }

    private boolean putIfNotContains(Combination<T> combination) {
        if(generatedCombinations.contains(combination)) {
            return false;
        }
        return generatedCombinations.add(combination);
    }

    private static <T> void swap(T[] t, int i, int j) {
        T temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }
}
