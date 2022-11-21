package com.smart.tkl.euler.p141;

import com.smart.tkl.utils.MathUtils;

import java.util.Set;
import java.util.TreeSet;

public class SquareProgressiveNumber {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum = getSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public static long getSum() {
        long limit = 1000000000000L;
        long aLimit = 10000;

        Set<Long> progressiveSquares = new TreeSet<>();

        for(long a = 1; a <= aLimit; a++) {
            for(long b = 1; b < a; b++) {
                if(a * a * a * b + b * b > limit) {
                   break;
                }
                if(MathUtils.GCD(a, b) != 1) {
                  continue;
                }
                for(long m = 1;;m++) {
                    long n = a * a * a * m * m * b + m * b * b;
                    if(n > limit) {
                       break;
                    }
                    if(isSquare(n)) {
                       progressiveSquares.add(n);
                    }
                }
            }
        }

        long sum = 0;
        for(long progressiveSquare : progressiveSquares) {
            sum += progressiveSquare;
        }

        return sum;
    }

    private static boolean isSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (long)sqrt;
    }

}
