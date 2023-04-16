package com.smart.tkl.euler.p211;

import com.smart.tkl.primes.PrimesSieve;
import java.util.List;

public class DivisorSquareSum {

    private final int limit;
    private final List<Long> primes;

    public DivisorSquareSum(int limit) {
        this.limit = limit;
        this.primes = PrimesSieve.generatePrimesUpTo(limit);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int limit = 64000000;

        DivisorSquareSum divisorSquareSum = new DivisorSquareSum(limit);
        long sum = divisorSquareSum.sum();

        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sum() {
        long result = 0;

        int maxSetSize = 0;
        long product = primes.get(0);

        for(int i = 1; i < this.primes.size() && product < this.limit; i++) {
            maxSetSize++;
            product *= this.primes.get(i);
        }
        System.out.println("Max set size: " + maxSetSize);

        for (int setSize = 2; setSize <= maxSetSize; setSize++) {
            result += sumDivisors(setSize, 0, 1, 1);
        }

        return result + 1;
    }

    private long sumDivisors(int left, int pos, long product, long divisorSquareSum) {
        long result = 0;
        if(left == 1) {
            for(int i = pos; i < this.primes.size(); i++) {
                long prime = this.primes.get(i);

                long number = product * prime;
                if(number > this.limit) {
                    break;
                }

                long finalDivSquareSum = (prime * prime + 1) * divisorSquareSum;
                int k = 1;
                while (number <= this.limit) {
                    double sqrt = Math.sqrt(finalDivSquareSum);
                    if(sqrt == (long)sqrt) {
                        //System.out.println("Number: " + number);
                        result += number;
                    }

                    number *= prime;
                    k++;
                    finalDivSquareSum = divisorSquareSum * toDivisorSquareSum(prime, k);
                }
            }
        }
        else {
            for(int i = pos; i <= this.primes.size() - left; i++) {
                long prime = this.primes.get(i);
                long nextElement = this.primes.get(i + 1);
                if(product * prime > this.limit || product * nextElement > this.limit) {
                    return result;
                }

                long newDivisorSquareSum = divisorSquareSum * (prime * prime + 1);
                long newProduct = product * prime;

                int k = 1;
                while (newProduct < this.limit) {
                    long sum = sumDivisors(left - 1, i + 1, newProduct, newDivisorSquareSum);
                    result += sum;
                    newProduct *= prime;
                    k++;
                    newDivisorSquareSum = divisorSquareSum * toDivisorSquareSum(prime, k);
                }
            }
        }
        return result;
    }

    private static long toDivisorSquareSum(long prime, int k) {
        if(k == 1) {
           return prime * prime + 1;
        }
        return ((long)Math.pow(prime, 2 * k + 2) - 1) / (prime * prime - 1);
    }

    private static long fastSumSieve(int limit) {

        int rootLimit = (int)Math.sqrt(limit);
        long[] sieve = new long[limit + 1];

        for(int i = 1; i <= rootLimit; i++) {
            for(int j = i + 1; i * j <= limit; j++) {
               sieve[i * j] += (long)i * i + (long)j * j;
            }
        }

        long result = 0;

        for(int i = 1; i < limit; i++) {
            double sqrt = Math.sqrt(sieve[i]);
            if(sqrt == (long)sqrt) {
               result += i;
            }
        }

        return result;
    }
}
