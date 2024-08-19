package com.smart.tkl.euler.p140;

import com.smart.tkl.lib.linear.MatrixUtils;
import java.util.Arrays;

public class ModifiedFibGoldenNuggets2 {

    /*
    * Generating function A(x) = (x + 3x^2) / (1 - x - x^2)
    * Rational x for A(x) is golden nugget: 2/5, 1/2, 7/12. 3/5, 19/31, 8/13, 50/81 ...
    * Golden nuggets: 2, 5, 21, 42, 152, 296, 1050...
    * Recurrence relation of golden nuggets: An = 7An-2 - An-4 + 7 where first 4 are given by 0,0,2,5.
    * Recurrence relation of the sum of golden nuggets: Sn = 7 * Sn-2 - Sn-4 + 7 * (n-1)
    * or: 5 * Sn + 7 * (n-1) = 6 * (An + An-1) - An-2 - An-3
    * */

    private static final long[][] MATRIX = new long[][] {
            {7, -1,  1},
            {1,  0,  0},
            {0,  0,  1}
    };

    private static final long[][] INITIAL_ODD = new long[][] {
            {2},
            {0},
            {7}
    };


    private static final long[][] INITIAL_EVEN = new long[][] {
            {5},
            {0},
            {7}
    };

    private static final long[][] MATRIX_FIB = new long[][] {
            {0, 1},
            {1, 1}
    };

    private static final long[][] INITIAL_FIB = new long[][] {
            {1},
            {1}
    };

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 9) + 7;
        long inv5 = 400000003;

        System.out.println("Inv5: " + inv5);

        System.out.println(Arrays.toString(getGoldenNuggets(3, mod)));
        System.out.println(Arrays.toString(getGoldenNuggets(4, mod)));
        System.out.println(Arrays.toString(getGoldenNuggets(5, mod)));
        System.out.println(Arrays.toString(getGoldenNuggets(6, mod)));
        System.out.println(Arrays.toString(getGoldenNuggets(7, mod)));

        System.out.println(sumOfGoldenNuggets(2, mod, inv5));
        System.out.println(sumOfGoldenNuggets(3, mod, inv5));
        System.out.println(sumOfGoldenNuggets(4, mod, inv5));
        System.out.println(sumOfGoldenNuggets(5, mod, inv5));
        System.out.println(sumOfGoldenNuggets(6, mod, inv5));
        System.out.println(sumOfGoldenNuggets(7, mod, inv5));




        System.out.println(sumOfGoldenNuggets2(3, mod, inv5));
        System.out.println(sumOfGoldenNuggets2(5, mod, inv5));
        System.out.println(sumOfGoldenNuggets2(7, mod, inv5));

        System.out.println(sum(1, 2, mod, inv5));
        System.out.println(sum(20, 20, mod, inv5));


        long r = (long)Math.pow(10, 18);
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 40000; i++) {
            sum(r, r, mod, inv5);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        for(int i = 0; i < 40000; i++) {
            sum2(r, r, mod, inv5);
        }
        time2 = System.currentTimeMillis();
        System.out.println("Time 2 in ms: " + (time2 - time1));
    }

    public static long sum(long l, long r, long mod, long modInv5) {
        long sum1 = sumOfGoldenNuggets(r, mod, modInv5);
        long sum2 = sumOfGoldenNuggets(l - 1, mod, modInv5);

        long sum = (sum1 - sum2) % mod;
        if(sum < 0) {
           sum += mod;
        }
        return sum;
    }

    public static long sum2(long l, long r, long mod, long modInv5) {
        long sum1 = sumOfGoldenNuggets2(r, mod, modInv5);
        long sum2 = sumOfGoldenNuggets2(l - 1, mod, modInv5);

        long sum = (sum1 - sum2) % mod;
        if(sum < 0) {
            sum += mod;
        }
        return sum;
    }

    private static long sumOfGoldenNuggets(long n, long mod, long modInv5) {
        if(n < 1) {
           return 0;
        }
        if(n == 1) {
           return 2;
        }
        if(n == 2) {
           return 7;
        }
        long[] goldenNuggets1 = getGoldenNuggets(n, mod);
        long[] goldenNuggets2 = getGoldenNuggets(n - 1, mod);

        long un = goldenNuggets1[0];
        long un1 = goldenNuggets2[0];
        long un2 = goldenNuggets1[1];
        long un3 = goldenNuggets2[1];

        long f1 = (6 * (un + un1) - un2 - un3) % mod;
        long f2 = (7 * (n - 1)) % mod;
        long f = (f1 - f2) % mod;
        if(f < 0) {
           f += mod;
        }

        return (modInv5 * f) % mod;
    }

    private static long sumOfGoldenNuggets2(long n, long mod, long modInv5) {
        if(n % 2 == 0) {
           long fibNum = findFibNumber(2 * n + 2, mod)[0];
           long result = (7 * (fibNum  - n - 1)) % mod;
           if(result < 0) {
              result += mod;
           }
           return (result * modInv5) % mod;
        }
        else {
            long sum = sumOfGoldenNuggets2(n - 1, mod, modInv5);
            long[] fibNumbers = findFibNumber(n + 1, mod);
            long fib1 = fibNumbers[0];
            long fib2 = fibNumbers[1];
            long result = (sum + (3 * (fib1 * fib1) % mod) % mod) % mod;
            result = (result - (fib1 * fib2) % mod) % mod;
            if(result < 0) {
                result += mod;
            }
            return result;
        }
    }

    private static long[] findFibNumber(long n, long mod) {
        long[][] poweredMatrix = MatrixUtils.pow(MATRIX_FIB, n - 2, mod);
        long[][] solution = MatrixUtils.multiply(poweredMatrix, INITIAL_FIB, mod);
        return new long[]{solution[1][0], solution[0][0]};
    }

    /*
    Golden nuggets: 2, 5, 21, 42, 152, 296, 1050...
    * */
    private static long[] getGoldenNuggets(long n, long mod) {
        if(n < 1) {
           return new long[]{0, 0};
        }
        if(n == 1) {
           return new long[]{2, 0};
        }
        if(n == 2) {
           return new long[]{5, 0};
        }
        if(n % 2 == 0) {
            long pow = n / 2 - 1;
            long[][] poweredMatrix = MatrixUtils.pow(MATRIX, pow, mod);
            long[][] solution = MatrixUtils.multiply(poweredMatrix, INITIAL_EVEN, mod);
            return new long[]{solution[0][0], solution[1][0]};
        }
        else {
            long pow = n / 2;
            long[][] poweredMatrix = MatrixUtils.pow(MATRIX, pow, mod);
            long[][] solution = MatrixUtils.multiply(poweredMatrix, INITIAL_ODD, mod);
            return new long[]{solution[0][0], solution[1][0]};
        }
    }
}
