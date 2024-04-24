package com.smart.tkl.euler.p100;

import com.smart.tkl.lib.utils.LinearSolution;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class ArrangedProbability2 {

    public static void main(String[] args) {
        System.out.println(MathUtils.listProperDivisors(810000));
        System.out.println(getDivisors(810, 1000));

        System.out.println(findSolution(1, 36, 6, 1));
    }


    private static Optional<Pair<Long>> findSolution(long P, long Q, long k, long limit) {
        Optional<Pair<Long>> result = Optional.empty();
        List<Long> divisors = getDivisors(P, Q);
        long number = P * (Q - P);

        for(long factor1 : divisors) {
            long factor2 = number / factor1;
            long sum = -factor1 + factor2;
            if(sum <= 0) {
               break;
            }
            if(sum % 2 != 0) {
               continue;
            }
            long X = sum / 2;
            if(X <= P * (2 * limit - 1)) {
               break;
            }
            if(X % P != 0) {
              continue;
            }
            long XP = X / P;
            if((XP + 1) % 2 != 0) {
                continue;
            }
            long y = (XP + 1) / 2;

            if((factor2 - X) % k != 0) {
               continue;
            }
            long Y = (factor2 - X) / k;
            if(Y % 2 == 0) {
               continue;
            }
            long x = (Y + 1) / 2;

            if(x < y) {
               result = Optional.of(Pair.of(x, y));
            }
        }
        return result;
    }

    private static List<Long> getDivisors(long P, long Q) {
        List<Long> divisors1 = MathUtils.listProperDivisors(P);
        List<Long> divisors2 = MathUtils.listProperDivisors(Q - P);
        Set<Long> products = new TreeSet<>();
        for(long div1 : divisors1) {
            for(long div2 : divisors2) {
                products.add(div1 * div2);
            }
        }
        return new ArrayList<>(products);
    }


}
