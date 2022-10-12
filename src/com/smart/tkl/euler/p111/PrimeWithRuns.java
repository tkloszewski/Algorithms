package com.smart.tkl.euler.p111;

import com.smart.tkl.utils.MathUtils;

import java.util.*;

public class PrimeWithRuns {

    private final int digitsCount;
    private final Map<Integer, Long> sameDigitValueMap = new LinkedHashMap<>();

    public PrimeWithRuns(int digitsCount) {
        this.digitsCount = digitsCount;
        fillSameDigitValueMap();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PrimeWithRuns primeWithRuns = new PrimeWithRuns(10);
        Long totalSum = primeWithRuns.getSumOfAllPrimes();
        long time2 = System.currentTimeMillis();
        System.out.println("Total sum: " + totalSum);
        System.out.println("Total time in ms: " + (time2 - time1));
    }

    public Long getSumOfAllPrimes() {
        long result = 0;
        for(int digit = 0; digit <= 9; digit++) {
            List<Long> primes = getMaxDigitPrimes(digit);
            Long sum = toSum(primes);
            result += sum;
        }

        return result;
    }

    public List<Long> getMaxDigitPrimes(int digit) {
        if (digit != 0) {
            for(int repeatedDigitCount = this.digitsCount - 1; repeatedDigitCount >= 2; repeatedDigitCount--) {
                List<Long> prime = getPrimes(0, digit, this.digitsCount - 1, this.digitsCount - repeatedDigitCount);
                if(!prime.isEmpty()) {
                   return prime;
                }
            }
        }
        else {
            List<Long> primesForZero = new ArrayList<>();
            for(int repeatedDigitCount = this.digitsCount - 2; repeatedDigitCount >= 2; repeatedDigitCount--) {
                fillPrimesForZero(0, this.digitsCount - 1, this.digitsCount - repeatedDigitCount, primesForZero);
                if(!primesForZero.isEmpty()) {
                    return primesForZero;
                }
            }
        }
        return List.of();
    }

    private List<Long> getPrimes(long currentValue, int repeatedDigit, int currentPos, int distinctDigitsLeft) {
        List<Long> result = new ArrayList<>();
        for(int pos = currentPos; pos >= 0; pos--) {
            int startDigit = pos < this.digitsCount -1 ? 0 : 1;
            long sameDigitPower = (long)Math.pow(10, pos + 1);
            long sameDigitPart = repeatedDigit * sameDigitValueMap.get(currentPos - pos) * sameDigitPower;

            for(int digit = startDigit; digit <= 9; digit++) {
                if(digit == repeatedDigit) {
                    continue;
                }
                long digitPart = digit * (long)Math.pow(10, pos);
                long newValue = currentValue + sameDigitPart + digitPart;
                if(distinctDigitsLeft == 1) {
                   long lastPartValue = repeatedDigit * sameDigitValueMap.get(pos);
                   long calculatedValue = newValue + lastPartValue;
                   if(MathUtils.isPrime(calculatedValue)) {
                      result.add(calculatedValue);
                   }
                }
                else {
                   List<Long> primes = getPrimes(newValue, repeatedDigit, pos - 1, distinctDigitsLeft - 1);
                   if(primes.size() > 0) {
                      result.addAll(primes);
                   }
                }
            }
        }

        return result;
    }

    private void fillPrimesForZero(long currentValue, int currentPos, int distinctDigitsLeft, List<Long> primes) {
        if (currentPos == this.digitsCount - 1) {
            for(int firstDigit = 1; firstDigit <= 9; firstDigit++) {
                long firstPartValue = firstDigit * (long)Math.pow(10, this.digitsCount - 1);
                for(int lastDigit = 1; lastDigit <= 9; lastDigit++) {
                    fillPrimesForZero(firstPartValue + lastDigit, 1, distinctDigitsLeft - 2, primes);
                }
            }
        }
        else {
            if(distinctDigitsLeft == 0) {
               if(MathUtils.isPrime(currentValue)) {
                  primes.add(currentValue);
               }
            }
            for (int pos = currentPos; pos >= 1; pos--) {
                for(int digit = 1; digit <= 9; digit++) {
                    long digitPart = digit * (long)Math.pow(10, pos);
                    fillPrimesForZero(currentValue + digitPart, pos - 1, distinctDigitsLeft - 1, primes);
                }
            }
        }
    }

    private void fillSameDigitValueMap() {
        sameDigitValueMap.put(0, 0L);
        long value = 1;
        for(int i = 1; i < this.digitsCount; i++) {
            sameDigitValueMap.put(i, value);
            value  = value * 10 + 1;
        }
    }

    private Long toSum(List<Long> primes) {
        return primes.stream().reduce(0L, Long::sum);
    }
}
