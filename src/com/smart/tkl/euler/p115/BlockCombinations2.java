package com.smart.tkl.euler.p115;

import java.math.BigInteger;
import java.util.Arrays;

public class BlockCombinations2 {

    private final int minLength;
    private final long valueThreshold;

    public BlockCombinations2(int minLength, long valueThreshold) {
        this.minLength = minLength;
        this.valueThreshold = valueThreshold;
    }

    public static void main(String[] args) {
        long limit = (long)Math.pow(10, 6);
        long time1 = System.currentTimeMillis();
        BlockCombinations2 blockCombinations = new BlockCombinations2(50, limit);
        long rowLength = blockCombinations.findRowLength();
        long time2 = System.currentTimeMillis();
        System.out.println("Row length: " + rowLength);
        System.out.println("Solution found in ms: " + (time2 - time1));

       // long limit1 = (long)Math.pow(10, 18);
        long limit1 = 1000000000000000000L;
        long m = (long)Math.pow(10, 18);
        /*rowLength = findMaxRowLength(m, limit1);
        System.out.println("Row length: " + rowLength);*/

       // m = (long)Math.pow(10, 18);
        rowLength = search(m, limit1);
        System.out.println("Row length binary search: " + rowLength);



        /*1000000001414213561*/
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

    public static long findMaxRowLength(long m, long countLimit) {
        long n = m;
        while (true) {
            boolean exceeded = test(n, m, countLimit);
            if(exceeded) {
                return n;
            }
            n++;
        }
    }

    public static long search(long m, long countLimit) {
        return search(m, m, Long.MAX_VALUE, countLimit);
    }

    private static long search(long m, long left, long right, long countLimit) {
        if(left == right) {
            return left;
        }
        long middle = left + (right - left) / 2;
        boolean exceeded = test(middle, m, countLimit);
        if(exceeded) {
            return search(m, left, middle, countLimit);
        }
        else {
            return search(m, middle + 1, right, countLimit);
        }
    }

    private static boolean test(long n, long m, long limit) {
        long count = 0;
        long max = (n + 1) / (m + 1);

        for(int b = 0; b <= max; b++) {
            long partCount = combinations(1 + b - b * m + n, 1 - b - b * m + n, limit);
            if(partCount > limit) {
                return true;
            }
            count += partCount;
            if(count > limit) {
                return true;
            }
        }

        return false;
    }

    static long combinations(long n, long k, long limit) {
        if(k > n - k) {
            k = n - k;
        }

        BigInteger result = BigInteger.ONE;
        BigInteger bgLimit = BigInteger.valueOf(limit);

        for(long i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i));
            result = result.divide(BigInteger.valueOf(i + 1));
            if(result.compareTo(bgLimit) > 0) {
                return limit + 1;
            }
        }

        return result.longValue();
    }

}
