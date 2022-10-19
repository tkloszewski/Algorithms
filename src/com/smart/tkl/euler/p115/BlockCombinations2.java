package com.smart.tkl.euler.p115;

import java.util.Arrays;

public class BlockCombinations2 {

   private final int minLength;
   private final long valueThreshold;

    public BlockCombinations2(int minLength, long valueThreshold) {
        this.minLength = minLength;
        this.valueThreshold = valueThreshold;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        BlockCombinations2 blockCombinations = new BlockCombinations2(50, 1000000);
        int rowLength = blockCombinations.findRowLength();
        long time2 = System.currentTimeMillis();
        System.out.println("Row length: " + rowLength);
        System.out.println("Solution found in ms: " + (time2 - time1));
    }

    public int findRowLength() {
        int length = 2 * minLength;
        long[] solutions = new long[length + 1];
        for(int i = 0; i <= minLength - 1; i++) {
            solutions[i] = 1;
        }
        solutions[minLength] = 2;
        int startPos = minLength + 1;

        while (true) {
            for(int pos = startPos; pos <= length; pos++) {
                solutions[pos] += solutions[pos - 1];
                for(int tileLength = minLength; tileLength <= pos - 1; tileLength++) {
                    solutions[pos] += solutions[pos - tileLength - 1];
                }
                solutions[pos]++;
                if(solutions[pos] > valueThreshold) {
                   return pos;
                }
            }

            startPos = length + 1;
            length *= 2;
            solutions = Arrays.copyOf(solutions, length + 1);
        }
    }
}
