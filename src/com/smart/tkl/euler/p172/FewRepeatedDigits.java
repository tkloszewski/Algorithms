package com.smart.tkl.euler.p172;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import com.smart.tkl.lib.utils.MathUtils;
import java.util.List;

public class FewRepeatedDigits {

    private final int size;
    private final long[] factorials;
    private final long[][] memo;

    public FewRepeatedDigits(int size) {
        this.size = size;
        this.factorials = new long[size + 1];
        this.memo = new long[11][11];
        initFactorials();
    }

    private void initFactorials() {
        factorials[0] = 1;
        for(int i = 1; i <= size; i++) {
            factorials[i] = (i * factorials[i -1]);
        }
    }

    public static void main(String[] args) {
       long time1 = System.currentTimeMillis();
       FewRepeatedDigits fewRepeatedDigits = new FewRepeatedDigits(18);
       long count = fewRepeatedDigits.count();
       long time2 = System.currentTimeMillis();
       System.out.println("count: " + count);
       System.out.println("Time in ms: " + (time2 - time1));
    }

    public long bruteForceCount() {
        long count = 0;
        long pow = (long)Math.pow(10, size);
        for(long i = pow / 10; i <= pow - 1; i++) {
            List<Integer> digits = MathUtils.getDigits(i);
            if(noMoreThan4SameDigits(digits)) {
               count++;
            }
        }
        return count;
    }

    public long count() {
        long count = 0;
        int limit = size - 1;
        for(int doubles = 0; doubles * 2 <= limit && doubles <= 10; doubles++) {
           for(int triples = 0; triples <= 10 - doubles && triples * 3 + doubles * 2 <= limit; triples++) {
               int left = limit - 2 * doubles - 3 * triples;
               if(left > 10 - doubles - triples) {
                  continue;
               }
              // System.out.println("Partial count for doubles: " + doubles + ", triples: " + triples);
               long partialCount = count(doubles, triples, left, limit);
             //  System.out.println("Partial count for doubles: " + doubles + ", triples: " + triples + " => " + partialCount);
               count += partialCount;
           }
        }
        return 9 * count;
    }

    private long count(int doubles, int triples, int left, int limit) {
        long combinations1 = CombinatoricsUtils.countCombinations(9, doubles, this.memo);
        long combinations2 = CombinatoricsUtils.countCombinations(9 - doubles, triples, this.memo);
        long combinations3 = CombinatoricsUtils.countCombinations(10 - doubles - triples, left, this.memo);
        long permutations = this.factorials[limit];
        for(int i = 1; i <= doubles; i++) {
            permutations = permutations / factorials[2];
        }
        for(int i = 1; i <= triples; i++) {
            permutations = permutations / factorials[3];
        }
        long term1 = combinations1 * combinations2 * combinations3 * permutations;
        long term2 = 0;
        if (doubles > 0) {
            long combinations4 = CombinatoricsUtils.countCombinations(9, doubles - 1, this.memo);
            long combinations5 = CombinatoricsUtils.countCombinations(10 - doubles, triples, this.memo);
            term2 = combinations4 * combinations5 * combinations3 * permutations;
        }
        return term1 + term2;
    }

    private boolean noMoreThan4SameDigits(List<Integer> digits) {
        int[] freq = new int[10];
        for(int digit : digits) {
            if(freq[digit] >= 3) {
               return false;
            }
            freq[digit]++;
        }
        return true;
    }
}
