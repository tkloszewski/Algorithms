package com.smart.tkl.euler.p146;

import com.smart.tkl.primes.Primes;

import java.util.ArrayList;
import java.util.List;

public class PrimePattern {

    public static void main(String[] args) {
        long limit = 150000000;
        long sum  = 0;

        List<Long> numbers = new ArrayList<>();

        long time1 = System.currentTimeMillis();
        for(long n = 10; n < limit; n += 10) {
            if(n % 3 == 0) {
               continue;
            }
            long mod7 = n % 7;
            if(mod7 == 0 || mod7 == 1 || mod7 == 6) {
               continue;
            }
            long mod13 = n % 13;
            if(mod13 == 0 || mod13 == 5 || mod13 == 8) {
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
