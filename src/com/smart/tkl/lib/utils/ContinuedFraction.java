package com.smart.tkl.lib.utils;

import static com.smart.tkl.lib.utils.MathUtils.toFraction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContinuedFraction {

    public static void main(String[] args) {
        System.out.println(solveLinearEquation(5, 3, 13));
        System.out.println("Convergents: " + toConvergentList(127, 1293));
        System.out.println(compareContinuedFractions(toContinuedFractions(12, 13), toContinuedFractions(14, 13)));
        System.out.println("To fraction sqrt(2): " + toContinuedFractions(toFraction(Math.sqrt(2))));
        System.out.println("Continued fractions: " + toContinuedFractions(43, 19));
        System.out.println("From continued fraction: " + fromContinuedFractions(toContinuedFractions(43, 19)));
    }

    public static LinearSolution solveLinearEquation(long a, long b, long c) {
        long gcd = MathUtils.GCD(a, b);
        if(c % gcd != 0) {
           return LinearSolution.emptySolution();
        }
        List<Fraction> convergents = toConvergentList(a, b);
        Fraction beforeLastConvergent = convergents.size() == 1 ?
                new Fraction(0, 1) : convergents.get(convergents.size() - 2);

        long p = beforeLastConvergent.getNumerator();
        long q = beforeLastConvergent.getDenominator();

        int sign = convergents.size() % 2 == 0 ? 1 : -1;
        long x = (sign * c * q) / gcd;
        long y = (-sign) * (c * p) / gcd;

        return LinearSolution.solution(x, y, gcd);
    }

    public static Fraction fromContinuedFractions(List<Long> fractions) {
        List<Fraction> convergentList = toConvergentList(fractions);
        return convergentList.get(convergentList.size() - 1);
    }

    public static List<Fraction> toConvergentList(long p, long q) {
        return toConvergentList(toContinuedFractions(p, q));
    }

    public static List<Fraction> toConvergentList(List<Long> fractions) {
        List<Fraction> result = new ArrayList<>();

        long[] previousP = new long[]{1, fractions.get(0)};
        long[] previousQ = new long[]{0, 1};
        long p = previousP[1];
        long q = previousQ[1];

        result.add(new Fraction(p, q, false));

        for(int i = 1; i < fractions.size(); i++) {
            p = fractions.get(i) * previousP[1] + previousP[0];
            q = fractions.get(i) * previousQ[1] + previousQ[0];
            previousP[0] = previousP[1];
            previousP[1] = p;
            previousQ[0] = previousQ[1];
            previousQ[1] = q;

            result.add(new Fraction(p, q));
        }
        return result;
    }

    public static BigDecimalFraction fromContinuedFractionsToBigDecimal(List<Long> fractions) {
        BigDecimal[] previousP = new BigDecimal[]{BigDecimal.ONE, BigDecimal.valueOf(fractions.get(0))};
        BigDecimal[] previousQ = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE};
        BigDecimal p = previousP[1];
        BigDecimal q = previousQ[1];

        for(int i = 1; i < fractions.size(); i++) {
            p = BigDecimal.valueOf(fractions.get(i)).multiply(previousP[1]).add(previousP[0]);
            q = BigDecimal.valueOf(fractions.get(i)).multiply(previousQ[1]).add(previousQ[0]);

            previousP[0] = previousP[1];
            previousP[1] = p;
            previousQ[0] = previousQ[1];
            previousQ[1] = q;
        }
        return new BigDecimalFraction(p, q);
    }

    public static List<Long> toContinuedFractions(Fraction f) {
        return toContinuedFractions(f.getNumerator(), f.getDenominator());
    }

    public static List<Long> toContinuedFractions(long p, long q) {
        List<Long> fractions = new ArrayList<>();
        long remainder;
        while((remainder = p % q) != 0) {
            fractions.add(p/q);
            p = q;
            q = remainder;
        }
        fractions.add(p/q);
        return fractions;
    }

    public static int compareContinuedFractions(List<Long> f1, List<Long> f2) {
        int minSize = Math.min(f1.size(), f2.size());

        int k = 0;
        while (k < minSize && f1.get(k) == (long)f2.get(k)) {
            k++;
        }
        if(k < minSize) {
            long a = f1.get(k);
            long b = f2.get(k);
            if(a > b) {
                return k % 2 == 0 ? 1 : -1;
            }
            else {
                return k % 2 == 1 ? 1 : -1;
            }
        }
        else {
            if(f1.size() == f2.size()) {
                return 0;
            }
            else if(f1.size() < f2.size()) {
                return k % 2 == 1 ? 1 : -1;
            }
            else {
                return k % 2 == 0 ? 1 : -1;
            }
        }
    }
}
