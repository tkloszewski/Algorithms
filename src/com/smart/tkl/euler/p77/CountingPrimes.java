package com.smart.tkl.euler.p77;

import com.smart.tkl.utils.MathUtils;

import java.util.List;

public class CountingPrimes {

    private final int limit;

    public CountingPrimes(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        int numOfWays = 5000;
        CountingPrimes countingPrimes = new CountingPrimes(numOfWays);
        int value = countingPrimes.getValue();
        System.out.printf("The first value that can be written in over %d ways is: %d.", numOfWays, value);
    }

    public int getValue() {
        final int primeLimit = 1000;
        boolean[] primesSieve = MathUtils.primesSieve(primeLimit);

        int target = 2;

        while (target <= primeLimit) {
            long[] counter = new long[primeLimit + 1];
            counter[0] = 1;
            for(int i = 2; i < primesSieve.length && i <= target ; i++) {
                if(primesSieve[i]) {
                    for(int j = i; j <= target; j++) {
                        counter[j] += counter[j - i];
                    }
                }
            }
            if(counter[target] > limit) {
               break;
            }
            target++;
        }

        return target;
    }
}
