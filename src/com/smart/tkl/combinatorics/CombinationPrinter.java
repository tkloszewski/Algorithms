package com.smart.tkl.combinatorics;

public class CombinationPrinter implements CombinationListener<Object> {

    @Override
    public void combinationGenerated(Combination<Object> combination) {
        System.out.println(combination);
    }
}
