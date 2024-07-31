package com.smart.tkl.lib.utils.diophantine;

import com.smart.tkl.lib.utils.LongPoint;
import com.smart.tkl.lib.utils.XYPair;
import com.smart.tkl.lib.utils.continuedfraction.ContinuedFraction;
import com.smart.tkl.lib.utils.continuedfraction.ContinuedFractionUtils;
import com.smart.tkl.lib.utils.continuedfraction.SquareRootPeriodicFractionGenerator;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PellsEquationSolver {

    public static void main(String[] args) {
        List<Long> values = List.of(96L);
        for(long value : values) {
            XYPair sol = leastPositivePellsSolution(value);
            LongPoint longPoint = solve(value);

            System.out.println("Solution: " + sol);
            System.out.println("Long point: " + longPoint);
        }

        XYPair solution = leastPositivePellsSolution(1011);
        System.out.println("Solution: " + solution);

        System.out.println(solve(1011));
        System.out.println(solve(5));
    }

    public static LongPoint solve(long d) {
        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(d);
        ContinuedFraction periodicFraction = generator.generate();
        List<Long> sequence = periodicFraction.getCoefficients();

        if(sequence.isEmpty()) {
           return new LongPoint(1, 0);
        }

        long p = periodicFraction.getBase(), q = 1;
        int index = 0;
        long[] pConvergents = new long[]{1, p};
        long[] qConvergents = new long[]{0, q};
        while (!solutionSatisfied(p, q, d)) {
            long a = sequence.get(index % sequence.size());
            p = pConvergents[1] * a + pConvergents[0];
            q = qConvergents[1] * a + qConvergents[0];

            pConvergents[0] = pConvergents[1];
            qConvergents[0] = qConvergents[1];
            pConvergents[1] = p;
            qConvergents[1] = q;
            index = (index + 1) % sequence.size();
        }

        return new LongPoint(p ,q);
    }

    /*value = 0 (mod 4)*/
    public static XYPair leastPositivePellsFourSolution(long value) {
        XYPair baseSolution = leastPositivePellsSolution(value / 4);
        return new XYPair(baseSolution.getX().multiply(BigInteger.TWO), baseSolution.getY());
    }

    public static Optional<XYPair> leastPositivePellsFourSolution(long value, BigInteger limit) {
        return leastPositivePellsSolution(value / 4, limit)
                .map(solution -> new XYPair(solution.getX().multiply(BigInteger.TWO), solution.getY()));
    }

    public static XYPair leastPositivePellsSolution(long value) {
        return leastPositivePellsSolution(value, null).orElseThrow();
    }

    public static Optional<XYPair> leastPositivePellsSolution(long value, BigInteger limit) {
        double exactSqrt = Math.sqrt(value);
        long m = 0, d = 1;
        long a0 = (long)exactSqrt;

        BigInteger numerator = BigInteger.valueOf(a0);
        BigInteger denominator = BigInteger.ONE;
        BigInteger prevNumerator = BigInteger.ONE;
        BigInteger prevDenominator = BigInteger.ZERO;

        Map<Surd, Integer> surdMap = new HashMap<>();
        Surd surd = new Surd(m, d, a0);
        surdMap.put(surd, 0);

        int periodIndex = 0;
        surd = nextSurd(surd, value, a0);

        List<BigInteger> coefficients = new ArrayList<>();

        while (!surdMap.containsKey(surd)) {
            surdMap.put(surd, periodIndex);

            BigInteger coefficientBI = BigInteger.valueOf(surd.coefficient);
            coefficients.add(coefficientBI);

            BigInteger nextNumerator = coefficientBI.multiply(numerator).add(prevNumerator);
            BigInteger nextDenominator = coefficientBI.multiply(denominator).add(prevDenominator);

            prevNumerator = numerator;
            prevDenominator = denominator;
            numerator = nextNumerator;
            denominator = nextDenominator;

            if (limit != null) {
                if(prevNumerator.compareTo(limit) > 0 || prevDenominator.compareTo(limit) > 0) {
                  //System.out.println("Limit exceeded: " + prevNumerator + "/" + prevDenominator);
                  return Optional.empty();
                }
            }

            surd = nextSurd(surd, value, a0);
            periodIndex++;
        }

        int periodSize = coefficients.size();

        if(periodSize % 2 == 0) {
           return Optional.of(new XYPair(prevNumerator, prevDenominator));
        }

        //Get convergent at 2m - 1 where m is period size
        for(int i = 0; i < periodSize - 1; i++) {
            BigInteger coefficientBI = coefficients.get(i);
            BigInteger nextNumerator = coefficientBI.multiply(numerator).add(prevNumerator);
            BigInteger nextDenominator = coefficientBI.multiply(denominator).add(prevDenominator);

            prevNumerator = numerator;
            prevDenominator = denominator;
            numerator = nextNumerator;
            denominator = nextDenominator;
        }

        return Optional.of(new XYPair(numerator, denominator));
    }

    private static boolean solutionSatisfied(long x, long y, long d) {
        return x * x - d * y * y == 1;
    }

    private static Surd nextSurd(Surd surd, long value, long sqrt) {
        long m = surd.coefficient * surd.d - surd.m;
        long d = (value - m * m) / surd.d;
        long coefficient = ContinuedFractionUtils.coefficient(m, sqrt, d);
        return new Surd(m, d, coefficient);
    }

    private static class Surd {
        long m;
        long d;
        long coefficient;

        public Surd(long m, long d, long coefficient) {
            this.m = m;
            this.d = d;
            this.coefficient = coefficient;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Surd that = (Surd) o;
            return m == that.m && d == that.d;
        }

        @Override
        public int hashCode() {
            return Objects.hash(m, d);
        }
    }

}
