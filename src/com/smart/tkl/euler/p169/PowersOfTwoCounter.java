package com.smart.tkl.euler.p169;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PowersOfTwoCounter {

    private static final BigInteger LONG_THRESHOLD = BigInteger.valueOf(Long.MAX_VALUE);

    private static final Map<BigInteger, Long> bigMemo = new HashMap<>();
    private static final Map<Long, Long> smallMemo = new HashMap<>();

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        BigInteger n = BigInteger.TEN.pow(25);
        long count = count(n);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long count(BigInteger n) {
        return f(n.add(BigInteger.ONE));
    }

    public static long f(BigInteger n) {
        if(bigMemo.containsKey(n)) {
           return bigMemo.get(n);
        }
        if(LONG_THRESHOLD.compareTo(n) >= 0) {
            return f(n.longValue());
        }
        else {
            BigInteger mod = n.remainder(BigInteger.TWO);
            BigInteger divided = n.divide(BigInteger.TWO);
            long result = BigInteger.ZERO.equals(mod) ? f(divided) : f(divided) + f(divided.add(BigInteger.ONE));
            bigMemo.put(n, result);
            return result;
        }
    }

    public static long f(long n) {
        if(n == 0) {
            return 0;
        }
        if(n == 1) {
            return 1;
        }
        if(smallMemo.containsKey(n)) {
           return smallMemo.get(n);
        }
        long result = n % 2 == 0 ? f(n / 2) : f(n / 2) + f(n / 2 + 1);
        smallMemo.put(n, result);
        return result;
    }

    public static long[] generateSequence(int n) {
        long[] seq = new long[n + 1];
        seq[0] = 0;
        seq[1] = 1;
        for(int i = n; i >= 2; i--) {
            if(seq[i] == 0) {
                fillSequence(seq, i);
            }
        }
        return seq;
    }

    private static void fillSequence(long[] seq, int n) {
        if(n == 0 || seq[n] != 0) {
            return;
        }
        int k = n / 2;
        if(n % 2 == 0) {
            fillSequence(seq, k);
            seq[n] = seq[k];
        }
        else {
            fillSequence(seq, k);
            fillSequence(seq, k + 1);
            seq[n] = seq[k] + seq[k + 1];
        }
    }

}
