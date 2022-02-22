package com.smart.tkl;

public class CombinationPrinter implements CombinationListener<Object> {

    @Override
    public void combinationGenerated(Combination<Object> combination) {
        System.out.println(combination);
    }
}
