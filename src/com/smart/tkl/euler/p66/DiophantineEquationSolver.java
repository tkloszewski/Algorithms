package com.smart.tkl.euler.p66;

import com.smart.tkl.lib.utils.PeriodicFraction;
import com.smart.tkl.lib.utils.SquareRootPeriodicFractionGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class DiophantineEquationSolver {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int parameter = findParameterForLargestX(10000);
        long time2 = System.currentTimeMillis();
        System.out.println("Diophantine equation searched parameter: " + parameter + " Time in ms: " + (time2 - time1));
    }

    public static int findParameterForLargestX(int limit) {
        int foundParameter = -1;
        BigIntegerFraction largestNumeratorFraction = null;

        for(int parameter = 2; parameter <= limit; parameter++) {
            if(isSquare(parameter)) {
               continue;
            }
            BigIntegerFraction fraction = findFirstSolutionToEquation(parameter);

            if(largestNumeratorFraction == null || fraction.p.compareTo(largestNumeratorFraction.p) > 0) {
               largestNumeratorFraction = fraction;
               foundParameter = parameter;
            }
        }

        return foundParameter;
    }

    public static BigIntegerFraction findFirstSolutionToEquation(int parameter) {
        PeriodicFraction periodicFraction = new SquareRootPeriodicFractionGenerator(parameter).generate();
        List<Long> sequence = periodicFraction.getSequence();

        BigInteger[] previousP = new BigInteger[]{BigInteger.ONE, BigInteger.valueOf(periodicFraction.getBase())};
        BigInteger[] previousQ = new BigInteger[]{BigInteger.ZERO, BigInteger.ONE};

        int i = 0, seqSize = periodicFraction.getPeriodSize();
        BigInteger p = previousP[1];
        BigInteger q = previousQ[1];

        while (!equationSatisfied(p, q, parameter)) {
            BigInteger seqTerm = BigInteger.valueOf(sequence.get(i % seqSize));

            p = seqTerm.multiply(previousP[1]).add(previousP[0]);
            q = seqTerm.multiply(previousQ[1]).add(previousQ[0]);

            previousP[0] = previousP[1];
            previousP[1] = p;
            previousQ[0] = previousQ[1];
            previousQ[1] = q;

            i++;
        }

        return new BigIntegerFraction(p, q);
    }

    private static boolean equationSatisfied(BigInteger x, BigInteger y, int parameter) {
        return x.multiply(x).subtract(BigInteger.valueOf(parameter).multiply(y).multiply(y)).equals(BigInteger.ONE);
    }

    private static boolean isSquare(int parameter) {
        double squareRoot = Math.sqrt(parameter);
        return (int)squareRoot == squareRoot;
    }

    private static class BigIntegerFraction {
        BigInteger p;
        BigInteger q;

        public BigIntegerFraction(BigInteger p, BigInteger q) {
            this.p = p;
            this.q = q;
        }

        @Override
        public String toString() {
            return "p=" + p + ", q=" + q;
        }
    }

}
