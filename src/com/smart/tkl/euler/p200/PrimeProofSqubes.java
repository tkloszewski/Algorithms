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

    private static boolean isPrimeProofSqube(Long sqube) {
        int lastDigit = (int)(sqube % 10);
        if(lastDigit % 2 == 0 || lastDigit == 5) {
            return checkLastDigit(sqube, lastDigit);
        }
        else {
            boolean isPrimeProofForLastDigit = checkLastDigit(sqube, lastDigit);
            if(!isPrimeProofForLastDigit) {
               return false;
            }
            long number = sqube / 10;
            long pow = 10;
            int pos = 1;
            while (number > 0) {
                int digit = (int)(number % 10);
                boolean isPrimeProofForPosition = checkDigitAtPosition(sqube, digit, pos, pow);
                if(!isPrimeProofForPosition) {
                    return false;
                }
                number = number / 10;
                pos++;
                pow *= 10;
            }
        }

        return true;
    }

    private static boolean checkLastDigit(long sqube, int lastDigit) {
        for(int checkDigit = 1; checkDigit <= 9; checkDigit += 2) {
            if (checkDigit != lastDigit) {
                long number = sqube + (checkDigit - lastDigit);
                if(Primes.isPrime(number)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkDigitAtPosition(long sqube, int digit, int pos, long pow) {
        int start = pos == 0 ? 1 : 0;
        for(int checkDigit = start; checkDigit <= 9; checkDigit++) {
            if(checkDigit != digit) {
                long number = sqube + (checkDigit - digit) * pow;
                if(Primes.isPrime(number)) {
                    return false;
                }
            }
        }

        return true;
    }
}
