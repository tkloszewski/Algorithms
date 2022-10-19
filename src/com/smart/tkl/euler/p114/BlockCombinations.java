package com.smart.tkl.euler.p114;

import java.util.Arrays;

public class BlockCombinations {

    private final int length;

    public BlockCombinations(int length) {
        assert length >= 3;
        this.length = length;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        BlockCombinations blockCombinations = new BlockCombinations(50);
        long count = blockCombinations.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Number of combinations: " + count);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public long count() {
        long[] solutions = new long[this.length + 1];
        for(int i = 0; i <= 2; i++) {
            solutions[i] = 1;
        }
        solutions[3] = 2;
        for(int pos = 4; pos <= this.length; pos++) {
            solutions[pos] += solutions[pos - 1];
            for(int tileLength = 3; tileLength <= pos - 1; tileLength++) {
                solutions[pos] += solutions[pos - tileLength - 1];
            }
            solutions[pos]++;
        }

        return solutions[this.length];
    }
}
