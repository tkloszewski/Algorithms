package com.smart.tkl.euler.p47;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DistinctPrimeFactors {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<Integer> values = solve(2000000, 2);
        long time2 = System.currentTimeMillis();
        System.out.println(values);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static List<Integer> solve(int N, int K) {
        List<Integer> result = new ArrayList<>();

        List<PrimeFactor>[] primeFactorsArray = new ArrayList[K];
        for(int i = 0; i < K; i++) {
            primeFactorsArray[i] = MathUtils.listPrimeFactors(14 + i);
        }

        if(hasDistinctPrimeFactors(primeFactorsArray, K, 0)) {
           result.add(14);
        }

        int pos = 0;
        for(int i = 14 + K; i <= N + K - 1; i ++) {
            List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(i);
            primeFactorsArray[pos] = primeFactors;
            if(hasDistinctPrimeFactors(primeFactorsArray, K, (pos + 1) % K)) {
               result.add(i - K + 1);
            }
            pos = (pos + 1) % K;
        }

        return result;
    }

    private static boolean hasDistinctPrimeFactors(List<PrimeFactor>[] primeFactorsArray, int K, int firstPos) {
        for(int i = 0; i < K - 1; i++) {
            List<PrimeFactor> primeFactors1 = primeFactorsArray[(firstPos + i) % K];
            if(primeFactors1.size() != K) {
                return false;
            }
            Set<Long> set1 = primeFactors1.stream().map(PrimeFactor::getFactor).collect(Collectors.toSet());


            List<PrimeFactor> primeFactors2 = primeFactorsArray[(firstPos + i + 1) % K];
            if(primeFactors2.size() != K) {
                return false;
            }
            Set<Long> set2 = primeFactors2.stream().map(PrimeFactor::getFactor).collect(Collectors.toSet());

            if(!Collections.disjoint(set1, set2)) {
               return false;
            }
        }
        return true;

    }
}
