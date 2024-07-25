package com.smart.tkl.euler.p132;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.math.BigInteger;
import java.util.List;

public class LargeRepUnit2 {

    private static final int LIMIT = 10000000;

    private final List<Long> primes;

    public LargeRepUnit2() {
        primes = PrimesSieve.generatePrimesUpTo(LIMIT);
    }

    public static void main(String[] args) {
         LargeRepUnit2 largeRepUnit2 = new LargeRepUnit2();
         System.out.println(largeRepUnit2.getSumOfPrimes(10, 9, 40));
    }

    public long getSumOfPrimes(int a, int b, int k) {
        long sum = 0;
        int count = 0;
        if(a % 3 == 0) {
           sum += 3;
           count++;
        }

        if(count == k) {
           return sum;
        }

        BigInteger pow = BigInteger.valueOf(a).pow(b);

        for(long prime : primes) {
            if(prime < 7) {
               continue;
            }
            long phi = prime - 1;
            long r = pow.mod(BigInteger.valueOf(phi)).longValue();
            if(test(r, prime)) {
               sum += prime;
               if(++count == k) {
                  break;
               }
            }
        }

        return sum;
    }

    static boolean test(long r, long mod) {
        return BigInteger.TEN.modPow(BigInteger.valueOf(r), BigInteger.valueOf(mod)).longValue() == 1;
    }

    static long pow(long a, long b, long mod) {
        return BigInteger.valueOf(a).modPow(BigInteger.valueOf(b), BigInteger.valueOf(mod)).longValue();
    }
}
