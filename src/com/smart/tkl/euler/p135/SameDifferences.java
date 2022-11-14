package com.smart.tkl.euler.p135;

import java.util.ArrayList;
import java.util.List;

public class SameDifferences {

    private final int limit;
    private final int value;
    private int[] tab;

    public SameDifferences(int limit, int value) {
        this.limit = limit;
        this.value = value;
    }

    public int count() {
        int count = 0;
        tab = new int[limit + 1];
        for(int p = 1; p < limit; p++) {
            for(int q = 1; q * p <= limit; q++) {
                if((p + q) % 4 == 0 && (3 * p - q) > 0 && (3 * p - q) % 4 == 0) {
                    int n = p * q;
                    int prevValue = tab[n];
                    if(n < limit) {
                       tab[n]++;
                    }
                    if(tab[n] == this.value) {
                       count++;
                    }
                    else if(prevValue == this.value) {
                       count--;
                    }
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SameDifferences sameDifferences = new SameDifferences(1000000, 10);
        int count = sameDifferences.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Number of n values with 10 distinct solutions: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }
}
