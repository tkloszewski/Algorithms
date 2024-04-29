package com.smart.tkl.lib.utils.congruence;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.ModuloUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinearCongruenceSolver {

    public static void main(String[] args) {
        System.out.println(solve(9, 9, 27));
    }

    /*
    * Solves system of linear congruences with Chinese Reminder Theorem
    *
    * */
    public static long solveCongruencesForCoPrime(List<LinearCongruence> congruences) {
        long result = 0;
        long M = 1;
        for(LinearCongruence congruence : congruences) {
            M *= congruence.getM();
        }
        for(LinearCongruence congruence : congruences) {
            long mi = congruence.getM();
            long ai = congruence.getA();
            long Mi = M / mi;
            long inv = ModuloUtils.modInv(Mi, mi) % M;
            long m = MathUtils.moduloMultiply(ai, inv, M);
            m = MathUtils.moduloMultiply(m, Mi, M);
            result = (result + m) % M;
        }
        return result;
    }



    /*
    * Solves linear congruence a*x = b(mod m)
    * */
    public static List<Long> solve(long a, long b, long m) {
        a = a % m;
        b = b % m;
        if(b < 0) {
           b = m + b;
        }
        return solve(a, b, MathUtils.GCD(a, m), m);
    }

    public static Optional<Long> leastSolution(long a, long b, long m) {
        a = a % m;
        b = b % m;
        return leastSolution(a, b, MathUtils.GCD(a, m), m);
    }

    public static Optional<Long> leastSolution(long a, long b, long d, long m) {
        if (d != 1) {
            if(b % d != 0 || a % d != 0 || m % d != 0) {
                return Optional.empty();
            }
            a = a / d;
            b = b / d;
            m = m / d;
        }

        long aInv = ModuloUtils.modInv(a, m);
        long x = MathUtils.moduloMultiply(aInv, b, m);

        if(x < 0) {
            x = m + x;
        }

        return Optional.of(x);
    }

    /*
     * Solves linear congruence a*x = b(mod m) where d = gcd(a, m)
     * */
    public static List<Long> solve(long a, long b, long d, long m) {
        Optional<Long> solution = leastSolution(a, b, d, m);
        List<Long> result = new ArrayList<>((int)d);
        if (solution.isPresent()) {
            long x = solution.get();
            for(long i = 0; i < d; i++) {
                result.add((x + i * m / d) % m);
            }
        }
        return result;
    }

}
