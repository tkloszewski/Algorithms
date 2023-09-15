package com.smart.tkl.lib;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BrentFactorization {

    private static final List<Long> PRIMES = List.of(2L, 3L, 5L, 7L);

    public static void main(String[] args) {
        System.out.println(factor(169));

        long n = 551093930956115711L;
        long time1 = System.currentTimeMillis();
        List<PrimeFactor> primeFactors1 = MathUtils.listPrimeFactors(n);
        long time2 = System.currentTimeMillis();
        System.out.println("Prime factors1: " + primeFactors1);
        System.out.println("Time in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        List<PrimeFactor> primeFactors2 = listPrimeFactors(n);
        time2 = System.currentTimeMillis();
        System.out.println("Prime factors2: " + primeFactors2);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static List<PrimeFactor> listPrimeFactors(long n) {
        if(n <= 1) {
           return List.of();
        }
        if(Primes.isPrime(n)) {
           return List.of(new PrimeFactor(n, 1));
        }

        List<PrimeFactor> result = new ArrayList<>();
        LinkedList<Long> factors = new LinkedList<>();

        long factor = factor(n);
        factors.add(n / factor);
        factors.add(factor);

        do {
           long m = factors.removeLast();
           if(m == 1) {
              continue;
           }
           if(Primes.isPrime(m)) {
               int pow = 1;
               Iterator<Long> iterator = factors.iterator();
               int i = 0;
               while (iterator.hasNext()) {
                   long k = iterator.next();
                   if(k % m == 0) {
                       while (k % m == 0) {
                           k = k / m;
                           pow++;
                       }
                       factors.set(i, k);
                   }
                   i++;
               }
               result.add(new PrimeFactor(m, pow));
           }
           else {
              factor = m < 100 ? smallFactor(m) : factor(m);
              factors.add(factor);
              factors.add(m / factor);
           }
        } while (!factors.isEmpty());

        return result;
    }

    static long factor(long n) {
        long x0 = 2;
        long g = 1;
        long xs = 0, y = 0;

        int m = 128;
        long c = 1;
        while (g == n || g == 1) {
            long x = x0;
            long q = 1;
            long l = 1;
            g = 1;

            while (g == 1) {
                y = x;
                for(int i = 1; i < l; i++) {
                    x = f(x, c, n);
                }
                int k = 0;
                while (k < l && g == 1) {
                    xs = x;
                    for(int i = 0; i < m && i < l - k; i++) {
                        x = f(x, c, n);
                        q = modMul(q, Math.abs(x - y), n);
                    }
                    g = MathUtils.GCD(n, q);
                    k += m;
                }
                l *= 2;
            }
            if (g == n) {
                do {
                   xs = f(xs, c, n);
                   g = MathUtils.GCD(n, Math.abs(xs - y));
                } while (g == 1);
            }
            x0++;
        }
        return g;
    }

    static long smallFactor(long n) {
        for(long prime : PRIMES) {
            if(n % prime == 0) {
               return prime;
            }
        }
        return 1;
    }

    static long f(long x, long c, long n) {
        return (modMul(x, x, n) + c) % n;
    }

    static long modMul(long a, long b, long n) {
       long result = 0;
       while (b > 0) {
           if((b & 1) != 0) {
              result = (result + a) % n;
           }
           a = (a + a) % n;
           b >>= 1;
       }
       return result;
    }
}
