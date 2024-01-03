package com.smart.tkl.lib.primes;

public class PrimesRangeSieve {

    private final long low;
    private final long high;
    private final boolean[] sieve;

    public PrimesRangeSieve(long low, long high) {
        assert high >= low;
        this.low = low;
        this.high = high;
        this.sieve = createSieve();
    }

    public boolean isPrime(long n) {
        return !sieve[(int)(n - low)];
    }

    private boolean[] createSieve() {
        boolean[] result = new boolean[(int)(high - low + 1)];
        int sqrtLimit = (int)Math.sqrt(high);
        PrimesSieve primesSieve = new PrimesSieve(sqrtLimit);

        int step = 1;
        for(int i = 2; i <= sqrtLimit; i += step) {
            if(primesSieve.isPrime(i)) {
                long lower = i;
                long k = low / i;
                if(k > 1) {
                    if(low % i == 0) {
                        lower = low;
                    }
                    else {
                        lower = k * i + i;
                    }
                }
                for(long j = lower; j <= high; j += i) {
                    result[(int)(j - low)] = true;
                }
            }

            if(i >= 5) {
               if(i == 5) {
                  step = 2;
               }
               else {
                  step = step == 2 ? 4 : 2;
               }
            }
        }

        return result;
    }
}
