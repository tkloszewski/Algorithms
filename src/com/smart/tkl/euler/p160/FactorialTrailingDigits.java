package com.smart.tkl.euler.p160;

import com.smart.tkl.lib.utils.MathUtils;

public class FactorialTrailingDigits {

    private final long limit;

    public FactorialTrailingDigits(long limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
       long time1 = System.currentTimeMillis();
       long limit = 1000000000000L;
       FactorialTrailingDigits factorialTrailingDigits = new FactorialTrailingDigits(limit);
       long f = factorialTrailingDigits.f();
       long time2 = System.currentTimeMillis();
       System.out.println("Result: " + f);
       System.out.println("Time in ms: " + (time2 - time1));
    }

    /*
    * (!2 ^ !5)! => 3 * 7 * 9 * 11 * 13 * 17 * 19 * 21 * 23 * 27 * 29 * 31 * ... * 91 * 93 * 97 * 99
    * rf = remaining factors
    * rf(2) => 3 * 7 * 9 * 11 * 13 * 17 * 19 * 21 * 23 * 27 * 29 * 31 * 33 * 37 * 39 * 41 * 43 * 47 * 49
    * rf(4) => 3 * 7 * 9 * 11 * 13 * 17 * 19
    * rf(8) => 3 * 7 * 9 * 11
    * rf(16) => 3
    * rf(32) => 3
    * rf(64) => 1
    * rf(5) => 3 * 7 * 9 * 11 * 13 * 17 * 19
    * rf(25) => 3
    * rf(10) => 3 * 7 * 9
    * rf(50) => 1
    * rf(20) => 3
    * rf(100) => 1
    * remaining 2 powers => (100/2 + 100/4 + 100/8 + 100/16 + 100/32 + 100/64) - (100/5 + 100/25) = 91 - 24 = 67
    * f(100) = (3 * 7 * 9 * 11 * ... 97 * 99) * 2^67 * rf(2) * rf(4) * rf(8) * rf(16) * rf(32) * rf(64) * rf(5) *
    *  * rf(25) * rf(10) * rf(50) * rf(20) * rf(100)
    * */
    public long f() {
        long result = 1;

        long mod = 100000;
        long endIdx = Math.min(this.limit, mod);
        long remainingPow = this.limit / mod;

        long numOfTrailingZeroes = getTrailingZeroes();
        long remainingPow2 = getPowerOf(2) - numOfTrailingZeroes;

        long[] remainingFactors = new long[(int)endIdx + 1];
        remainingFactors[0] = 1;
        remainingFactors[1] = 1;
        for(int i = 2; i <= endIdx; i++) {
            if(i % 2 != 0 && i % 5 != 0) {
                remainingFactors[i] = MathUtils.moduloMultiply(remainingFactors[i - 1], i, mod);
            }
            else {
                remainingFactors[i] = remainingFactors[i - 1];
            }
        }

        result *= remainingFactors[(int)endIdx];

        if(remainingPow > 0) {
           long remainingFactorsModPower = MathUtils.moduloPower(remainingFactors[(int) endIdx], remainingPow, mod);
           result = MathUtils.moduloMultiply(result, remainingFactorsModPower, mod);
        }

        long remainingModPow2 = MathUtils.moduloPower(2, remainingPow2, mod);
        result = MathUtils.moduloMultiply(result, remainingModPow2, mod);

        long maxPow2 = log(2, this.limit);
        long maxPow5 = log(5, this.limit);

        long pow2 = 1;
        for(int i = 0; i <= maxPow2; i++) {
            long pow5 = 1;
            for(int j = 0; j <= maxPow5 && pow2 * pow5 <= this.limit; j++) {
                long num = pow2 * pow5;
                if(num != 1) {
                   int idx = (int)((this.limit / num) % mod);
                   result = MathUtils.moduloMultiply(result, remainingFactors[idx], mod);
                }
                pow5 *= 5;
            }
            pow2 *= 2;
        }

        return result;
    }

    private long getTrailingZeroes() {
        return getPowerOf(5);
    }

    private long getPowerOf(long num) {
        long count = 0, pow = num;
        while (this.limit / pow > 0) {
            count += this.limit / pow;
            pow *= num;
        }
        return count;
    }

    private static long log(int base, long n) {
        long pow = 0;
        long num = base;
        while (num <= n) {
            num *= base;
            pow++;
        }
        return pow;
    }


}
