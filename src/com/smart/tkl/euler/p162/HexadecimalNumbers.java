package com.smart.tkl.euler.p162;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class HexadecimalNumbers {

    private final int limit;
    private final long[][] memo;

    public HexadecimalNumbers(int limit) {
        this.limit = limit;
        this.memo = new long[limit + 1][limit + 1];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int n = 16;
        HexadecimalNumbers hexadecimalNumbers = new HexadecimalNumbers(n);
        long totalCount = hexadecimalNumbers.totalCount();
        BigInteger totalFastCount = superFastCount(n);
        System.out.println("HEX: " + totalFastCount.toString(16).toUpperCase());
        long time2 = System.currentTimeMillis();
        System.out.println("Total count: " + totalCount);
        System.out.println("Total super fast count: " + totalFastCount);
        System.out.println("Hexadecimal format : " + String.format("%x", totalCount).toUpperCase());
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long totalCount() {
        long count = 4;

        for(int n = 4; n <= limit; n++) {
            long threeValuesCount = 13 * fastCount(n - 1, 3);
            long twoValuesCount = 2 * fastCount(n - 1, 2);

            count += threeValuesCount;
            count += twoValuesCount;
        }

        return count;
    }

    private long fastCount(int n, int valuesCount) {
        long count = 0;

        for(int length = valuesCount; length <= n; length++) {
            int k = n - length;

            List<int[]> frequencies = new LinkedList<>();
            fillFrequencies(length, 0, new int[valuesCount], frequencies);

            long numOfCombinations = CombinatoricsUtils.countCombinations(n, length, this.memo);
            long permutationsCount = MathUtils.factorial(length);

            for(int[] freqTab : frequencies) {
               long permutations = permutationsCount;
               for(int freq : freqTab) {
                   long denominator = MathUtils.factorial(freq);
                   permutations = permutations / denominator;
               }
               long stringCombinations = numOfCombinations * permutations;
               count += (long)Math.pow(16 - valuesCount, k) * stringCombinations;
            }

        }
        return count;
    }

    public long superFastCount() {
        int n = this.limit;

        long result = 0;

        result += 256 * (((long)Math.pow(16, n - 2) - 1)) ;
        result -= 9675 * (((long)Math.pow(15, n - 2)) / 14);
        result += 8036 * (((long)Math.pow(14, n - 2)) / 13);
        result -= 2197 * (((long)Math.pow(13, n - 2)) / 12);

        return result;
    }

    public static BigInteger superFastCount(int n) {
        BigInteger term1 = BigInteger.valueOf(16).pow(n - 2).subtract(BigInteger.ONE).multiply(BigInteger.valueOf(256));
        BigInteger term2 = BigInteger.valueOf(15).pow(n - 2).divide(BigInteger.valueOf(14)).multiply(BigInteger.valueOf(9675));
        BigInteger term3 = BigInteger.valueOf(14).pow(n - 2).divide(BigInteger.valueOf(13)).multiply(BigInteger.valueOf(8036));
        BigInteger term4 = BigInteger.valueOf(13).pow(n - 2).divide(BigInteger.valueOf(12)).multiply(BigInteger.valueOf(2197));

       /* result += 256 * (((long)Math.pow(16, n - 2) - 1)) ;
        result -= 9675 * (((long)Math.pow(15, n - 2)) / 14);
        result += 8036 * (((long)Math.pow(14, n - 2)) / 13);
        result -= 2197 * (((long)Math.pow(13, n - 2)) / 12);*/

        return term1.subtract(term2).add(term3).subtract(term4);

    }

    private static void fillFrequencies(int sumLeft, int pos, int[] tab, List<int[]> sums) {
        if(pos == tab.length - 1) {
           if(sumLeft > 0) {
              tab[pos] = sumLeft;
              sums.add(tab.clone());
           }
        }
        else {
            for(int value = 1; value <= sumLeft; value++) {
                tab[pos] = value;
                fillFrequencies(sumLeft - value, pos + 1, tab, sums);
            }
        }
    }
}
