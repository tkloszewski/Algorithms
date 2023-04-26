package com.smart.tkl.lib.combinatorics;

public class CombinationPrinter implements CombinationListener<Object> {

    @Override
    public void combinationGenerated(Combination<Object> combination) {
        System.out.println(combination);
    }
}
