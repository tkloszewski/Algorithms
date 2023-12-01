package com.smart.tkl.euler.p183;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

public class MaximumProductOfParts {

    private final int N;

    public MaximumProductOfParts(int n) {
        N = n;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int N = 10000;
        MaximumProductOfParts maximumProductOfParts = new MaximumProductOfParts(N);
        long sum = maximumProductOfParts.sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sum() {
        long result = 0;

        Map<Long, Integer>[] primeFactors = new Map[N + 1];
        for(int n = 1; n <= N; n++) {
            primeFactors[n] = MathUtils.listPrimeFactors(n).stream()
                    .collect(Collectors.toMap(PrimeFactor::getFactor, PrimeFactor::getPow));
        }

        for(int n = 5; n <= N; n++) {
            double x = n / Math.E;
            int k = (int)x;

            BigInteger v1 = BigInteger.valueOf(k + 1).pow(k + 1);
            BigInteger v2 = BigInteger.valueOf(k).pow(k).multiply(BigInteger.valueOf(n));

            k = v1.compareTo(v2) > 0 ? k : k + 1;
            if(!isTerminatedDecimal(primeFactors[n], primeFactors[k])) {
                result += n;
            }
            else {
                result -= n;
            }
        }

        return result;
    }

    private static boolean isTerminatedDecimal(Map<Long, Integer> numeratorMap, Map<Long, Integer> denominatorMap) {
        for(Long factor : denominatorMap.keySet()) {
            if(factor != 2 && factor != 5) {
                if(!numeratorMap.containsKey(factor) || numeratorMap.get(factor) < denominatorMap.get(factor)) {
                    return false;
                }
            }
        }

        return true;
    }
}
