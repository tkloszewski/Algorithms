package com.smart.tkl.euler.p70;

import com.smart.tkl.lib.utils.MathUtils;

import java.util.List;

import static com.smart.tkl.lib.utils.hash.HashGenerator.*;

public class TotientPermutation {

    public static void main(String[] args) {
        TotientPermutation totientPermutation = new TotientPermutation();
        System.out.println("Min totient permutation " + totientPermutation.findMinTotientPermutation());
    }

    public long findMinTotientPermutation() {
        int limit = 10000000;
        long result = -1;
        boolean[] primeSieve = MathUtils.primesSieve(10000);
        List<Long> primes = MathUtils.generatePrimesUpTo(10000, primeSieve);

        double minPhiRatio = Double.MAX_VALUE;

        for(int i = primes.size() - 1; i >= 1; i--) {
            long prime1 = primes.get(i);
            if(prime1 * primes.get(0) >= limit) {
                continue;
            }
            double phiRatio = getPhiRatio(prime1, primes.get(i - 1));
            if(phiRatio > minPhiRatio) {
               break;
            }

            for(int j = i - 1; j >= 0; j--) {
                long prime2 = primes.get(j);
                if(prime1 * prime2 < limit) {
                    phiRatio = getPhiRatio(prime1, prime2);
                    if(phiRatio > minPhiRatio) {
                       break;
                    }
                    if(toDigitHash(prime1 * prime2) == toDigitHash((prime1 - 1) * (prime2 - 1))) {
                        minPhiRatio = phiRatio;
                        result = prime1 * prime2;
                    }
                }
            }
        }

        return result;
    }

    private double getPhiRatio(long prime1, long prime2) {
        return ((double)prime1 / (double)(prime1 - 1)) * ((double)prime2 / (double)(prime2 - 1));
    }


}
