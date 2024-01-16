package com.smart.tkl.euler.p200;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.tree.binary.heap.MinBinaryHeap;
import java.util.List;

public class PrimeProofSqubes {

    private static final long HARD_LIMIT = (long)Math.pow(10, 15);

    private final String searchString;
    private final int position;

    public PrimeProofSqubes(String searchString, int count) {
        this.searchString = searchString;
        this.position = count;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PrimeProofSqubes primeProofSqubes = new PrimeProofSqubes("200", 200);
        long sqube = primeProofSqubes.findSqube();
        long time2 = System.currentTimeMillis();
        System.out.println("Found sqube: " + sqube);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long findSqube() {
        int limit = (int)(Math.sqrt(5) * Math.pow(10, 7) * 0.5);
        List<Long> primes = PrimesSieve.generatePrimesUpTo(limit);
        MinBinaryHeap<Long> priorityQueue = new MinBinaryHeap<>(Long.class);

        for(int i = 0; i < primes.size(); i++) {
            long p = primes.get(i);
            for(int j = 0; j < primes.size(); j++) {
                if(j == i) {
                    continue;
                }
                long q = primes.get(j);
                if(p * q >= Math.sqrt((double) HARD_LIMIT / q)) {
                    break;
                }
                long sqube = p * p * q * q * q;
                if(String.valueOf(sqube).contains(searchString)) {
                    priorityQueue.insert(sqube);
                }
            }
        }

        int foundCount = 0;

        while (true) {
            Long sqube = priorityQueue.deleteFirst();
            if(isPrimeProofSqube(sqube)) {
               foundCount++;
               if(foundCount == position) {
                  return sqube;
               }
            }
        }
    }

    private boolean isPrimeProofSqube(Long sqube) {
        String sequence = sqube.toString();
        char[] digits = sequence.toCharArray();
        int lastDigit = digits[digits.length - 1] - '0';
        if(lastDigit % 2 == 0 || lastDigit == 5) {
           return checkLastDigit(digits, lastDigit);
        }
        else {
           boolean isPrimeProofForLastDigit = checkLastDigit(digits, lastDigit);
           if(!isPrimeProofForLastDigit) {
              return false;
           }
           for(int i = digits.length - 2; i >= 0; i--) {
               boolean isPrimeProofForPosition = checkDigitAtPosition(digits, i);
               if(!isPrimeProofForPosition) {
                  return false;
               }
           }
        }
        return true;
    }

    private boolean checkLastDigit(char[] digits, int lastDigit) {
        for(int digit = 1; digit <= 9; digit += 2) {
            if (digit != lastDigit) {
                digits[digits.length - 1] = (char)('0' + digit);
                long number = Long.parseLong(new String(digits));
                if(Primes.isPrime(number)) {
                    return false;
                }
            }
        }
        digits[digits.length - 1] = (char)('0' + lastDigit);
        return true;
    }

    private boolean checkDigitAtPosition(char[] digits, int pos) {
        int replacedDigit = digits[pos];
        int start = pos == 0 ? 1 : 0;
        for(int digit = start; digit <= 9; digit ++) {
            if (digit != replacedDigit) {
                digits[pos] = (char)('0' + digit);
                long number = Long.parseLong(new String(digits));
               // System.out.println("Checking number: " + number);
                if(Primes.isPrime(number)) {
                    return false;
                }
            }
        }
        digits[pos] = (char)replacedDigit;
        return true;
    }
}
