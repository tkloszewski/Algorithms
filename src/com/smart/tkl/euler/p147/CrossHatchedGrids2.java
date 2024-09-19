package com.smart.tkl.euler.p147;

import com.smart.tkl.lib.utils.ModuloUtils;

public class CrossHatchedGrids2 {

    private static final long MOD = 1000000007;
    private static final long INV_36 = ModuloUtils.modInv(36, MOD);
    private static final long INV_60 = ModuloUtils.modInv(60, MOD);


    public static void main(String[] args) {
        System.out.println(countAll(3, 2));
        System.out.println(countAll(7, 2));
        System.out.println(countAll(20, 10));
        System.out.println(countAll(10, 20));
        System.out.println(countAll(40, 40));
        System.out.println(countAll(47, 43));
        System.out.println(countAll(50, 50));
        System.out.println(countAll(100, 100));
        System.out.println(countAll(1000, 1000));

        for(long m = 1; m <= 10; m++) {
            for(long n = 1; n <= 10; n++) {
                long rectCount1 = countAllRegular(m, n);
                long rectCount2 = countAllRegularMod(m, n);
                long diagCount1 = countAllDiagonals(m, n);
                long diagCount2 = countAllDiagonalsMod(m, n);

                System.out.println("Rect count1: " + rectCount1);
                System.out.println("Rect count2: " + rectCount2);
                System.out.println("Diag count1: " + diagCount1);
                System.out.println("Diag count2: " + diagCount2);

                if(rectCount1 != rectCount2 || diagCount1 != diagCount2) {
                   System.out.println("DIFF!!!");
                   return;
                }
            }
        }
    }

    public static long countAll(long m, long n) {
        return countAllRegular(m, n) + countAllDiagonals(m ,n);
    }

    public static long countAllRegularMod(long m, long n) {
        return countAllRegular(m, n, MOD, INV_36);
    }

    public static long countAllDiagonalsMod(long m, long n) {
        return countAllDiagonals(m, n, MOD, INV_60);
    }

    public static long countAllRegular(long m, long n, long mod, long modInv) {
        long m3 = multiply(m, 3, mod);
        long m2 = multiply(m, 2, mod);
        long t1 = (m3 + 3 * m2 + 2 * m) % mod;

        long n3 = multiply(n ,3, mod);
        long n2 = multiply(n, 2, mod);

        long t2 = (n3 + 3 * n2 + 2 * n) % mod;


        return (((t1 * t2) % mod) * modInv) % mod;
    }

    public static long countAllDiagonals(long m, long n, long mod, long modInv) {
        if(m < n) {
            long temp = m;
            m = n;
            n = temp;
        }

        long n6 = multiply(n, 6, mod);
        long n5 = multiply(n, 5, mod);
        long n4 = multiply(n, 4, mod);
        long n3 = multiply(n, 3, mod);
        long n2 = multiply(n, 2, mod);
        long m2 = multiply(m, 2, mod);

        long t1 = (2 * n6  + 10 * ((n4 * m2) % mod)) % mod;
        long t2 = (2 * n5  + 20 * ((n3 * m2) % mod)) % mod;
        long t3 = (10 * ((n3 * m) % mod) + 5 * ((n2 * m2) % mod)) % mod;
        long tPos = (t1 + t2 + t3 + 3 * n2) % mod;

        long tNeg = (8 * ((n5 * m) % mod) + 10 * ((n4 * m) % mod) + 5 * n4
                    + 5 * ((n2 * m) % mod) + 5 * ((n * m2) % mod) + 17 * ((n * m) % mod)
                    + 2 * n) % mod;

        long t = tPos - tNeg;
        t = adjust(t, mod);

        return (t * modInv) % mod;
    }

    public static long countAllRegular(long m, long n) {
        long t1 = m * m * m + 3 * m * m + 2 * m;
        long t2 = n * n * n + 3 * n * n + 2 * n;
        return (t1 * t2) / 36;
    }

    public static long countAllDiagonals(long m, long n) {
        if(m < n) {
            long temp = m;
            m = n;
            n = temp;
        }

        long n6 = n * n * n * n * n * n;
        long n5 = n * n * n * n * n;
        long n4 = n * n * n * n;
        long n3 = n * n * n;
        long n2 = n * n;
        long m2 = m * m;

        long t1 = 2 * n6 - 8 * n5 * m + 10 * n4 * m2;
        long t2 = 2 * n5 - 10 * n4 * m + 20 * n3 * m2;
        long t3 = -5 * n4 + 10 * n3 * m + 5 * n2 * m2;
        long t4 = -5 * n2 * m - 5 * n * m2;
        long t5 = 3 * n2 - 17 * n * m - 2 * n;

        return (t1 + t2 + t3 + t4 + t5) / 60;
    }

    private static long countDiagonal(long m, long n) {
        if(n > m) {
            long temp = m;
            m = n;
            n = temp;
        }
        return (n * ((4 * n * n  - 1) * (2 * m - n) - 3)) / 6;
    }

    private static long multiply(long n, int times, long mod) {
        long product = 1;
        for(int i = 0; i < times; i++) {
            product = (product * n) % mod;
        }
        return product;
    }

    private static long adjust(long n, long mod) {
        return n >= 0 ? n : n + mod;
    }

}
