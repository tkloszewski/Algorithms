package com.smart.tkl.lib.utils;

import java.util.HashMap;
import java.util.Map;

public class ModDiscreteLog {

    public static void main(String[] args) {
      long x = solveForCoprime(3, 6, 7);
      System.out.println("Solution for coprime: " + x);
      x = solve(3, 6, 21);
      System.out.println("Generic solution: " + x);
      x = solve(4, 2, 10);
      System.out.println("Generic solution: " + x);
    }

    public static long solve(long a, long b, long m) {
        a = a % m;
        b = b % m;

        if(a == 0) {
            return b == 0 ? 1 : -1;
        }

        long k = 1, toAdd = 0, g;
        while ((g = MathUtils.GCD(a, m)) > 1) {
            if(b == k) {
               return toAdd;
            }
            if(b % g != 0) {
               return -1;
            }
            b = b / g;
            m = m / g;
            k = (k * (a / g)) % m;
            toAdd++;
        }


        long n = (long)Math.sqrt(m) + 1;

        long an = 1;
        for(int i = 0; i < n; i++) {
            an = (an * a) % m;
        }

        Map<Long, Long> map = new HashMap<>();
        for(long q = 0, aq = 1; q <= n; q++) {
            long val = (aq * b) % m;
            map.put(val, q);
            aq = (aq * a) % m;
        }

        long ap = an;
        for(long p = 1; p <= n; p++) {
            long val = (k * ap) % m;
            if(map.containsKey(val)) {
                long q = map.get(val);
                return n * p - q + toAdd;
            }
            ap = (ap * an) % m;
        }

        return -1;
    }


    public static long solveForCoprime(long a, long b, long m) {
        if(MathUtils.GCD(a, m) != 1) {
           throw new IllegalArgumentException("a and m must coprime");
        }
        a = a % m;
        b = b % m;

        if(a == 0) {
           return b == 0 ? 1 : -1;
        }
        if(b == 1) {
           long divCount = 1;
           for(PrimeFactor primeFactor : MathUtils.listPrimeFactors(m)) {
               divCount *= (primeFactor.getPow() + 1);
           }
           return m + 1 - divCount;
        }

        long n = (long)Math.sqrt(m) + 1;
        long an = 1;
        for(int i = 0; i < n; i++) {
            an = (an * a) % m;
        }

        Map<Long, Long> map = new HashMap<>();
        long aq = 1;
        for(long q = 0; q <= n; q++) {
            long val = (aq * b) % m;
            map.put(val, q);
            aq = (aq * a) % m;
        }

        long ap = an;
        for(long p = 1; p <= n; p++) {
            if(map.containsKey(ap)) {
               long q = map.get(ap);
               return n * p - q;
            }
            ap = (ap * an) % m;
        }

        return -1;
    }

}
