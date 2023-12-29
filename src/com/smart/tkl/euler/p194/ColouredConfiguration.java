package com.smart.tkl.euler.p194;

import com.smart.tkl.lib.utils.MathUtils;

public class ColouredConfiguration {

    private final int a;
    private final int b;
    private final int c;
    private final int mod;

    public ColouredConfiguration(int a, int b, int c, int mod) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.mod = mod;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int a = 25;
        int b = 75;
        int c = 1984;
        int mod = 100000000;
        ColouredConfiguration colouredConfiguration = new ColouredConfiguration(a, b, c, mod);
        long count = colouredConfiguration.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long[][] memo = new long[a + b + 1][a + b + 1];

        long result = countCombinations(a + b, b, mod, memo);

        if(a > 0) {
            long gluedFactor = aColoringsGlued(c);
            result = moduloMultiply(mod, result, gluedFactor, c, c - 1);
            for(int k = 1; k < a; k++) {
                result = (result * gluedFactor) % mod;
            }
        }

        if(b > 0) {
            long gluedFactor = bColoringsGlued(c);

            if(a == 0) {
                result = moduloMultiply(mod, result, gluedFactor, c, c - 1);
                for(int k = 1; k < b; k++) {
                    result = (result * gluedFactor) % mod;
                }
            }
            else {
                for(int k = 0; k < b; k++) {
                    result = (result * gluedFactor) % mod;
                }
            }
        }

        return result;
    }
    /*
     * Chromatic polynomial c * (c - 1) * (c - 2) * (c^4 - 7 * c^3 + 20 * c^2 - 29 * c + 19) divided by c * (c-1)
     * */
    private long aColoringsGlued(long c) {
        long term1 = (c - 2) % mod;
        long termPow2 = MathUtils.moduloPower(c, 2, mod);
        long termPow3 = (termPow2 * c) % mod;
        long termPow4 = (termPow3 * c) % mod;
        long term2 = (termPow4 - 7 * termPow3 + 20 * termPow2 - 29 * c + 19) % mod;
        return (term1 * term2) % mod;
    }

    /*
     * Chromatic polynomial c * (c - 1) * (c - 2)^3 * (c^2 - 2 * c + 3) divided by c * (c-1)
     *  */
    private long bColoringsGlued(long c) {
        long term1 = MathUtils.moduloPower(c - 2, 3, mod);
        long term2 = (c * c - 2 * c + 3) % mod;
        return (term1 * term2) % mod;
    }

    private static long countCombinations(int n, int k, int mod, long[][] memo) {
        if(memo[n][k] != 0) {
            return memo[n][k];
        }
        long result;
        if(k == 0 || k == n) {
            result = 1;
        }
        else if(k == 1 || k == n - 1) {
            result = n;
        }
        else {
            result = countCombinations(n - 1, k - 1, mod, memo) +
                    countCombinations(n - 1, k, mod, memo);
        }
        result = result % mod;
        memo[n][k] = result;
        memo[n][n - k] = result;
        return result;
    }

    private static long moduloMultiply(int mod, long ...args) {
        long result = 1;
        for(long arg : args) {
            result = (result * arg) % mod;
        }
        return result;
    }
}
