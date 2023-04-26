package com.smart.tkl.euler.p125;

import com.smart.tkl.lib.utils.MathUtils;

import java.math.BigDecimal;
import java.util.*;

public class PalindromicSums {

    private final long limit;
    private final long squareLimit;

    public PalindromicSums(long limit) {
        this.limit = limit;
        this.squareLimit = (long)Math.sqrt((double)limit / 2);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PalindromicSums palindromicSums = new PalindromicSums(100000000);
        BigDecimal sum = palindromicSums.sumOfPalindromicSquares();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum of palindromic squares: " + sum);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public BigDecimal sumOfPalindromicSquares() {
        Set<Long> palindromicNumbers = new TreeSet<>();
        for(long i = squareLimit; i >= 1; i--) {
            long sumOfSquares1 = sumOfSquares(i);
            for(long j = i - 2; j >= 0; j--) {
                long sumOfSquares2 = sumOfSquares(j);
                long sum = sumOfSquares1 - sumOfSquares2;
                if(sum < limit) {
                  if(MathUtils.isPalindrome(sum)) {
                     palindromicNumbers.add(sum);
                  }
                }
            }
        }

        BigDecimal result = BigDecimal.ZERO;
        for(Long palindromicNumber : palindromicNumbers) {
            result = result.add(BigDecimal.valueOf(palindromicNumber));
        }

        return result;
    }

    private static long sumOfSquares(long n) {
        long value = 2 * n * n * n + 3 * n * n + n;
        return value / 6;
    }
}
