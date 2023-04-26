package com.smart.tkl.euler.p193;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.List;

public class SquareFreeNumbers {

    private final long limit;
    private final List<Long> primeFactors;

    public SquareFreeNumbers(long limit) {
        this.limit = limit;
        this.primeFactors = PrimesSieve.generatePrimesUpTo((int)Math.sqrt(limit));
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();

        long limit = (long) Math.pow(2, 50) - 1;
        SquareFreeNumbers squareFreeNumbers = new SquareFreeNumbers(limit);
        long count = squareFreeNumbers.count();

        long time2 = System.currentTimeMillis();

        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long divisorsCount = countDivisors();
        return this.limit - divisorsCount;
    }

    private long countDivisors() {
        long result = 0;

        int maxSquareFactors = 0;
        long product = this.primeFactors.get(0) * this.primeFactors.get(0);
        int k = 1;
        while (product <= this.limit) {
            maxSquareFactors++;
            long primeFactor = this.primeFactors.get(k++);
            product *= primeFactor * primeFactor;
        }

        boolean include = true;
        for(int setSize = 1; setSize <= maxSquareFactors; setSize++) {
            long count = countDivisors(setSize, 0, 1);
            if(include) {
                result += count;
            }
            else {
                result -= count;
            }
            include = !include;
        }

        return result;
    }

    private long countDivisors(int left, int pos, long product) {
        long result = 0;
        if(left == 1) {
           for(int i = pos; i < this.primeFactors.size(); i++) {
               long prime = this.primeFactors.get(i);
               long square = prime * prime;
               long count = this.limit / (square * product);
               if(count == 0) {
                  return result;
               }
               result += count;
           }
        }
        else {
            for(int i = pos; i <= this.primeFactors.size() - left; i++) {
                long nextElement = this.primeFactors.get(i + 1);
                if(product * nextElement * nextElement > this.limit) {
                   return result;
                }

                long prime = this.primeFactors.get(i);
                long square = prime * prime;

                long count = countDivisors(left - 1, i + 1, product * square);
                if(count == 0) {
                   return result;
                }
                result += count;
            }
        }
        return result;
    }

}
