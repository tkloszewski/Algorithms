package com.smart.tkl.euler.p129;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepUnitDivisibility2 {

    public static void main(String[] args) {
        System.out.println("Totients up to: " + Math.sqrt(413.0));
    }

    static long A(long n, long phi) {
        List<Long> divisors = listProperDivisors(phi);
        for(long div : divisors) {
            if(test(div, 9L * n)) {
                return div;
            }
        }
        return phi;
    }

    static boolean test(long div, long mod) {
        return BigInteger.TEN.modPow(BigInteger.valueOf(div), BigInteger.valueOf(mod)).longValue() == 1;
    }

    public static long phi(long n) {
        List<Long> primes = MathUtils.listPrimeFactors(n).stream()
                .map(PrimeFactor::getFactor)
                .collect(Collectors.toList());

        long result = n;
        for(long prime : primes) {
            result = result / prime;
            result = result * (prime - 1);
        }

        return result;
    }

    static List<Long> listProperDivisors(long n) {
        List<Long> result = new ArrayList<>();
        result.add(1L);


        int start = n % 2 == 0 ? 2 : 3;
        long sqrt = (long)Math.sqrt(n);
        long end = sqrt;
        if(sqrt * sqrt == n) {
            result.add(sqrt);
            end = sqrt - 1;
        }

        for(long i = start; i <= end; i++) {
            if(n % i == 0) {
                result.add(i);
                result.add(n/i);
            }
        }

        result.add(n);
        result.sort(Long::compareTo);
        return result;
    }
}
