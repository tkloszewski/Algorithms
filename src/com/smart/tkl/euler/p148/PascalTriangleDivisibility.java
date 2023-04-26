package com.smart.tkl.euler.p148;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.List;

public class PascalTriangleDivisibility {

    public static void main(String[] args) {
        int numberOfRows = 1000000000;
        long time1 = System.currentTimeMillis();
        long count = fastCount(numberOfRows);
        long time2 = System.currentTimeMillis();

        System.out.println("Fast count: " + count);
        System.out.println("Fast count took in ms: " + (time2 - time1));
    }

    public static long fastCount(long nFirstRows) {
        long count = 0;

        List<Integer> base7Digits = MathUtils.toBase(nFirstRows, 7);

        long multiplier = 1;
        for(int i = base7Digits.size() - 1; i >= 0; i--) {
            int digit = base7Digits.get(i);
            if (digit > 0) {
                long term = (long)Math.pow(28, i) * sumFirst(digit) * multiplier;
                count += term;
            }
            multiplier *= (digit + 1);
        }

        return count;
    }

    public static long countOneByOne(long nFirstRows) {
        long count = 0;
        for(long n = 0; n < nFirstRows; n++) {
            List<Integer> base7Digits = MathUtils.toBase(n, 7);
            int rowCount = 1;
            for(int digit : base7Digits) {
                rowCount *= (digit + 1);
            }
            count += rowCount;
        }
        return count;
    }

    private static long sumFirst(int n) {
        return ((long) n * (n + 1)) / 2;
    }

}
