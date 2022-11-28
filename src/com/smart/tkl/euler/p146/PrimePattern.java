package com.smart.tkl.euler.p146;

import com.smart.tkl.primes.Primes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrimePattern {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();

        long limit = 150000000;

        List<Long> numbers = new ArrayList<>();
        Set<Long> notAllowedMod13 = Set.of(0L, 2L, 5L, 6L, 7L, 8L, 11L);
        Set<Long> notAllowedMod17 = Set.of(0L, 1L, 2L, 5L, 12L, 15L, 16L);

        for(long n = 10; n < limit; n += 10) {
            if(n % 3 == 0) {
               continue;
            }
            long mod7 = n % 7;
            if(mod7 != 3 && mod7 != 4) {
               continue;
            }
            long mod13 = n % 13;
            if(notAllowedMod13.contains(mod13)) {
               continue;
            }
            long mod17 = n % 17;
            if(notAllowedMod17.contains(mod17)) {
               continue;
            }
            long square = n * n;
            if(Primes.isPrime(square + 1) && Primes.isPrime(square + 3)
                    && Primes.isPrime(square + 7) && Primes.isPrime(square + 9)
                    && !Primes.isPrime(square + 11) && Primes.isPrime(square + 13)
                    && !Primes.isPrime(square + 17) && !Primes.isPrime(square + 19)
                    && !Primes.isPrime(square + 21) && !Primes.isPrime(square + 23)
                    && Primes.isPrime(square + 27)) {

                numbers.add(n);
            }
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Numbers: " + numbers);
        System.out.println("Sum: " + numbers.stream().reduce(0L, Long::sum));
        System.out.println("Solution took in ms: " + (time2 - time1));
    }
}
