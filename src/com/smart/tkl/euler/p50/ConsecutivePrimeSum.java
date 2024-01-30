package com.smart.tkl.euler.p50;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.List;

public class ConsecutivePrimeSum {

    private static PrimesSieve primesSieve;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        primesSieve = new PrimesSieve(10000000);
        List<PrimeChain> chains = getConsecutivePrimes(10000L);
        System.out.println(chains);

        long limit = 280;
        PrimeChain primeChain = search(chains, limit);
        System.out.println("Found chain: " + primeChain);
        System.out.println(chains.get(chains.size() - 1));
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static PrimeChain search(List<PrimeChain> chains, long limit) {
        return search(chains, limit, 0, chains.size() - 1);
    }

    private static PrimeChain search(List<PrimeChain> chains, long limit, int left, int right){
        if(left == right) {
           if(left > 0 && chains.get(left).sum > limit) {
              return chains.get(left - 1);
           }
           return chains.get(left);
        }
        else {
            int middle = (left + right) / 2;
            long leftValue = chains.get(left).sum;
            if(limit == leftValue) {
               return chains.get(left);
            }
            long rightValue = chains.get(right).sum;
            if(limit == rightValue) {
                return chains.get(right);
            }
            long middleValue = chains.get(middle).sum;
            if(limit == middleValue) {
                return chains.get(middle);
            }

            if(limit < middleValue) {
               return search(chains, limit, left, middle - 1);
            }
            else {
                if(left == right - 1) {
                   middle++;
                }
                return search(chains, limit, middle, right);
            }
        }
    }

    private static List<PrimeChain> getConsecutivePrimes(long limit) {
        List<PrimeChain> result = new ArrayList<>();
        result.add(new PrimeChain(1, 2));
        result.add(new PrimeChain(2, 5));

        long sum = 5;
        int longestSize = 2;
        int step = 0;

        List<Long> sums = new ArrayList<>(2000000);
        sums.add(0L);
        sums.add(2L);
        sums.add(5L);

        int[] checkedSizes = new int[33];

        for(long n = 5; n <= limit; n += step) {
            if(isPrime(n)) {
               sum += n;
               if(sum > limit) {
                  break;
               }
               sums.add(sum);
               outer:
               for(int i = 0; i < Math.min(33, sums.size()) && i + longestSize < sums.size(); i++) {
                   for(int j = Math.max(i + longestSize + 1, checkedSizes[i]); j < sums.size(); j++) {
                       long consecutiveSum = sums.get(j) - sums.get(i);
                       if(consecutiveSum > limit) {
                          break;
                       }
                       checkedSizes[i] = j;
                       if(((i == 0 && j % 2 == 0) || (j - i) % 2 == 1) && isPrime(consecutiveSum)) {
                          longestSize = j - i;
                          result.add(new PrimeChain(longestSize, consecutiveSum));
                          break outer;
                       }
                   }
               }
            }

            step = getStep(step, n);
        }

        return result;
    }

    private static int getStep(int step, long n) {
        if(n < 5) {
            return 1;
        }
        return step == 2 ? 4 : 2;
    }

    private static boolean isPrime(long n) {
        if(n <= 10000000) {
            return primesSieve.isPrime((int)n);
        }
        return Primes.isPrime(n);
    }

    private static class PrimeChain {
        int size;
        long sum;

        public PrimeChain(int size, long sum) {
            this.size = size;
            this.sum = sum;
        }

        @Override
        public String toString() {
            return "{" +
                    "size=" + size +
                    ", sum=" + sum +
                    '}';
        }
    }
}
