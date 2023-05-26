package com.smart.tkl.euler.p243;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Resilience {

    private final BigDecimal limitRatio;

    public Resilience(long p, long q) {
        this.limitRatio = toBigDecimal(p, q);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        Resilience resilience = new Resilience(15499, 94744);
        long denominator = resilience.findSmallest();
        long time2 = System.currentTimeMillis();
        System.out.println("Smallest denominator: " + denominator);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long findSmallest() {
        int primesSize = 100;
        while (true) {
            Optional<Long> smallest = findSmallest(primesSize);
            if(smallest.isPresent()) {
                return smallest.get();
            }
            primesSize = 10 * primesSize;
        }
    }

    private Optional<Long> findSmallest(int n) {
        List<Long> primes = PrimesSieve.generatePrimesUpTo(n);
        BigDecimal primesRatio = BigDecimal.ONE;

        long number = 1;
        List<Long> foundPrimes = new ArrayList<>();
        boolean found = false;
        for(Long prime : primes) {
            number *= prime;
            foundPrimes.add(prime);
            primesRatio = primesRatio.multiply(toBigDecimal(prime - 1, prime));
            if(primesRatio.compareTo(limitRatio) < 0) {
                found = true;
                break;
            }
        }

        if(found) {
            List<Long> sequence = new ArrayList<>();
            sequence.add(number);
            long smallest = number;
            while (true) {
                BigDecimal denominatorRatio = toBigDecimal(smallest, smallest - 1);
                if(denominatorRatio.multiply(primesRatio).compareTo(limitRatio) < 0) {
                    return Optional.of(smallest);
                }
                Long nextSmallest = null;
                for(long prime : foundPrimes) {
                    for(int i = 0; i < sequence.size() - 1; i++) {
                        long value = prime * sequence.get(i);
                        if(value > smallest) {
                            if(nextSmallest == null || value < nextSmallest) {
                                nextSmallest = value;
                                break;
                            }
                        }
                    }
                }
                if(nextSmallest != null) {
                    sequence.add(nextSmallest);
                    smallest = nextSmallest;
                }
                else {
                    sequence.add(2 * smallest);
                    smallest = 2 * smallest;
                }
            }
        }
        return Optional.empty();
    }

    private static List<Long> increasingOrderOf(List<Long> primes, int size) {
        List<Long> result = new ArrayList<>();
        long smallest = 1;
        for(long prime : primes) {
            smallest *= prime;
        }
        result.add(smallest);

        while (result.size() < size) {
            Long nextSmallest = null;
            for(long prime : primes) {
                for(int i = 0; i < result.size() - 1; i++) {
                    long value = prime * result.get(i);
                    if(value > smallest) {
                        if(nextSmallest == null || value < nextSmallest) {
                            nextSmallest = value;
                            break;
                        }
                    }
                }
            }
            if(nextSmallest != null) {
                result.add(nextSmallest);
                smallest = nextSmallest;
            }
            else {
                result.add(2 * smallest);
                smallest = 2 * smallest;
            }
        }
        System.out.println(result);
        return result;
    }

    private BigDecimal toBigDecimal(long p, long q) {
        return BigDecimal.valueOf(p).
                divide(BigDecimal.valueOf(q), MathContext.DECIMAL128);
    }
}
