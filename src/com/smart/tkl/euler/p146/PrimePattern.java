package com.smart.tkl.euler.p146;

import com.smart.tkl.primes.Primes;

import java.util.*;

public class PrimePattern {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();

        long limit = 150000000;

        List<Long> numbers = new ArrayList<>();

        Map<Long, Set<Long>> notAllowedMap = new LinkedHashMap<>();
        notAllowedMap.put(11L, Set.of(2L, 3L, 8L, 9L));
        notAllowedMap.put(13L, Set.of(0L, 2L, 5L, 6L, 7L, 8L, 11L));
        notAllowedMap.put(17L, Set.of(0L, 1L, 2L, 5L, 12L, 15L, 16L));
        notAllowedMap.put(19L, Set.of(4L, 5L, 14L, 15L));
        notAllowedMap.put(23L, Set.of(4L, 19L));
        notAllowedMap.put(29L, Set.of(4L, 7L, 12L, 14L, 15L, 17L, 22L, 25L));
        notAllowedMap.put(31L, Set.of(2L, 7L, 11L, 20L, 24L, 29L));


        outer:
        for(long n = 10; n < limit; n += 10) {
            if(n % 3 == 0) {
               continue;
            }
            long mod7 = n % 7;
            if(mod7 != 3 && mod7 != 4) {
               continue;
            }
            for(Long mod : notAllowedMap.keySet()) {
                long remainder = n % mod;
                if(notAllowedMap.get(mod).contains(remainder)) {
                   continue outer;
                }
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
