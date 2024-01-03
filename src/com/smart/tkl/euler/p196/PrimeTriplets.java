package com.smart.tkl.euler.p196;

import com.smart.tkl.lib.primes.PrimesRangeSieve;

public class PrimeTriplets {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum1 = sumOfPrimes(5678027);
        long sum2 = sumOfPrimes(7208785);
        long sum = sum1 + sum2;
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long sumOfPrimes(long row) {
        if(row <= 1) {
           return 0;
        }
        if(row == 2) {
           return 5;
        }

        long sum = 0;

        long low = firstElement(row - 2);
        long high = firstElement(row + 3) - 1;

        PrimesRangeSieve rangeSieve = new PrimesRangeSieve(low, high);
        long start = firstElement(row);
        long adjustedStart = adjustStartRange(start);
        long end = start + row - 1;
        long step = start % 6 == 1 ? 2 : 4;
        long k = adjustedStart - start + 1;

        for(long n = adjustedStart; n <= end; n += step, k++) {
            if(rangeSieve.isPrime(n)) {
                if(isPrimeTripletElement(n, k, row, rangeSieve)) {
                   sum += n;
                }
            }
            step = step == 2 ? 4 : 2;
        }

        return sum;
    }

    private static boolean isPrimeTripletElement(long n, long k, long row, PrimesRangeSieve sieve) {
        if(hasTwoPrimeNeighbours(n, k, row, sieve)) {
           return true;
        }

        long start = startRangeOfPrevRow(n, k, row);
        long end = endRangeOfPrevRow(n, k, row);

        for(long i = start; i <= end; i++) {
            if(sieve.isPrime(i)) {
               long ki = i - firstElement(row - 1) + 1;
               if(hasTwoPrimeNeighbours(i, ki, row - 1, sieve)) {
                  return true;
               }
            }
        }

        start = startRangeOfNextRow(n, k, row);
        end = endRangeOfNextRow(n, row);

        for(long i = start; i <= end; i++) {
            if(sieve.isPrime(i)) {
                long ki = i - firstElement(row + 1) + 1;
                if (hasTwoPrimeNeighbours(i, ki, row + 1, sieve)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasTwoPrimeNeighbours(long n, long k, long row, PrimesRangeSieve sieve) {
        long start = startRangeOfPrevRow(n, k, row);
        long end = endRangeOfPrevRow(n, k, row);
        boolean foundPrimeNeighbour = false;

        for(long i = start; i <= end; i++) {
            if(sieve.isPrime(i)) {
               if(foundPrimeNeighbour) {
                  return true;
               }
               foundPrimeNeighbour = true;
            }
        }


        start = startRangeOfNextRow(n, k, row);
        end = endRangeOfNextRow(n, row);

        for(long i = start; i <= end; i++) {
            if(sieve.isPrime(i)) {
                if(foundPrimeNeighbour) {
                    return true;
                }
                foundPrimeNeighbour = true;
            }
        }
        return false;
    }

    private static long startRangeOfPrevRow(long n, long k, long row) {
        if(k == 1) {
           return n - row + 1;
        }
        return n - row;
    }

    private static long endRangeOfPrevRow(long n, long k, long row) {
        if(k <= row - 2) {
            return n - row + 2;
        }
        if(k == row - 1) {
            return n - row + 1;
        }
        return n - row;
    }

    private static long startRangeOfNextRow(long n, long k, long row) {
        if(k == 1) {
            return n + row;
        }
        return n + row - 1;
    }

    private static long endRangeOfNextRow(long n, long row) {
        return n + row + 1;
    }

    private static long firstElement(long row) {
        return (row * row - row + 2) / 2;
    }

    private static long adjustStartRange(long range) {
        while (range % 6 != 1 && range % 6 != 5) {
            range++;
        }
        return range;
    }
}
