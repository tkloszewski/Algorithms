package com.smart.tkl.euler.p241;

import com.smart.tkl.lib.utils.Fraction;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
Hemi perfect numbers:
[2, 24, 4320, 4680, 26208, 8910720, 17428320, 20427264, 91963648, 197064960, 8583644160, 10200236032, 21857648640,
 57575890944, 57629644800, 206166804480, 17116004505600, 1416963251404800, 15338300494970880, 75462255348480000,
 88898072401645056, 301183421949935616]
* */

public class HemiPerfectFinder {

    private final long limit;
    private final List<Long> hemiPerfectNumbers = new ArrayList<>();
    private final Map<Long, PrimeFactor> firstFactorCache = new HashMap<>();

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long limit = (long)Math.pow(10, 18);
        HemiPerfectFinder hemiPerfectFinder = new HemiPerfectFinder(limit);
        long sum = hemiPerfectFinder.findSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public HemiPerfectFinder(long limit) {
        this.limit = limit;
    }

    public long findSum() {
        long sum = 0;
        for(int k = 3; k <= 11; k += 2) {
            search(1, new Fraction(k, 2));
        }

        hemiPerfectNumbers.sort(Long::compare);

        for(long hemiPerfectNumber : hemiPerfectNumbers) {
            sum += hemiPerfectNumber;
        }

        return sum;
    }

    private void search(long number, Fraction fraction) {
        long p = fraction.getNumerator();
        long q = fraction.getDenominator();

        if(p < 0 || q < 0 || gcd(number, q) != 1) {
           return;
        }

        if(p == 1 && q == 1) {
           this.hemiPerfectNumbers.add(number);
           return;
        }
        else if(q == 1) {
            return;
        }

        PrimeFactor firstFactor = getFirstFactor(q);
        long primePower = firstFactor.value;
        long prime = firstFactor.prime;

        for(int pow = firstFactor.pow; ;pow++, primePower *= prime) {
            long newNumber = number * primePower;
            if(newNumber < 0 || newNumber > this.limit ) {
               return;
            }

            long divisorsSum = toDivisorsSum(prime, pow);
            if(divisorsSum < 0) {
               return;
            }
            Fraction newFraction = Fraction.multiply(fraction, new Fraction(primePower, divisorsSum));
            search(newNumber, newFraction);
        }
    }

    private long toDivisorsSum(long p, int pow) {
        return ((long) (Math.pow(p, pow + 1) - 1)) / (p - 1);
    }

    private PrimeFactor getFirstFactor(long n) {
        PrimeFactor firstFactor = firstFactorCache.get(n);
        if(firstFactor != null) {
           return firstFactor;
        }
        firstFactor = findFirstFactor(n);
        firstFactorCache.put(n, firstFactor);
        return firstFactor;
    }

    private PrimeFactor findFirstFactor(long n) {
        int pow = 0;
        long value = 1;
        while (n % 2 == 0) {
            n = n / 2;
            pow++;
            value *= 2;
        }

        if(pow > 0) {
            return new PrimeFactor(2, pow, value);
        }

        for(long i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                n = n / i ;
                pow++;
                value *= i;
            }
            if(pow > 0) {
                return new PrimeFactor(i, pow, value);
            }
        }

        return new PrimeFactor(n,1, n);
    }

    private long gcd(long a, long b) {
        if(b == 0) {
           return a;
        }
        return gcd(b, a % b);
    }

    private static class PrimeFactor {
        long prime;
        long value;
        int pow;

        public PrimeFactor(long prime, int pow, long value) {
            this.prime = prime;
            this.value = value;
            this.pow = pow;
        }

        @Override
        public String toString() {
            return "PrimeFactor{" +
                    "prime=" + prime +
                    ", value=" + value +
                    ", pow=" + pow +
                    '}';
        }
    }

}
