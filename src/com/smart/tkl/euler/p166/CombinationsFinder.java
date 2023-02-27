package com.smart.tkl.euler.p166;

import java.util.LinkedList;
import java.util.List;

public class CombinationsFinder {

    private final int sum;
    private final int maxDigit;

    private List<int[]> result;

    public CombinationsFinder(int sum, int maxDigit) {
        this.sum = sum;
        this.maxDigit = maxDigit;
    }

    public CombinationsFinder(int sum) {
        this(sum, 9);
    }

    public List<int[]> findCombinations() {
        this.result = new LinkedList<>();
        fillCombinations(this.sum, 0, new int[4]);
        return this.result;
    }

    private void fillCombinations(int sumLeft, int pos, int[] tab) {
        if(pos == 3) {
            if (sumLeft <= this.maxDigit) {
                tab[pos] = sumLeft;
                this.result.add(tab.clone());
            }
        }
        else {
            for(int i = 0; i <= this.maxDigit; i++) {
                if(sumLeft - i >= 0) {
                    tab[pos] = i;
                    fillCombinations(sumLeft - i,pos + 1, tab);
                }
                else {
                    break;
                }
            }
        }
    }
}
