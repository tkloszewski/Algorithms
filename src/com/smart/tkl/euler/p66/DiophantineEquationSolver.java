package com.smart.tkl.euler.p66;

import com.smart.tkl.lib.utils.PeriodicFraction;
import com.smart.tkl.lib.utils.SquareRootPeriodicFractionGenerator;

import java.math.BigDecimal;
import java.util.List;

public class DiophantineEquationSolver {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int parameter = findParameterForLargestX(1000);
        long time2 = System.currentTimeMillis();
        System.out.println("Diophantine equation searched parameter: " + parameter + " Time in ms: " + (time2 - time1));
    }

    public static int findParameterForLargestX(int limit) {
        int foundParameter = -1;
        BigDecimalFraction largestNumeratorFraction = null;

        for(int parameter = 2; parameter <= limit; parameter++) {
            if(isSquare(parameter)) {
               continue;
            }
            BigDecimalFraction fraction = findFirstSolutionToEquation(parameter);

            if(largestNumeratorFraction == null || fraction.p.compareTo(largestNumeratorFraction.p) > 0) {
               largestNumeratorFraction = fraction;
               foundParameter = parameter;
            }
        }

        return foundParameter;
    }

    public static BigDecimalFraction findFirstSolutionToEquation(int parameter) {
        PeriodicFraction periodicFraction = new SquareRootPeriodicFractionGenerator(parameter).generate();
        List<Integer> sequence = periodicFraction.getSequence();

        BigDecimal[] previousP = new BigDecimal[]{BigDecimal.ONE, BigDecimal.valueOf(periodicFraction.getBase())};
        BigDecimal[] previousQ = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE};

        int i = 0, seqSize = periodicFraction.getPeriodSize();
        BigDecimal p = previousP[1];
        BigDecimal q = previousQ[1];

        while (!equationSatisfied(p, q, parameter)) {
            BigDecimal seqTerm = BigDecimal.valueOf(sequence.get(i % seqSize));

            p = seqTerm.multiply(previousP[1]).add(previousP[0]);
            q = seqTerm.multiply(previousQ[1]).add(previousQ[0]);

            previousP[0] = previousP[1];
            previousP[1] = p;
            previousQ[0] = previousQ[1];
            previousQ[1] = q;

            i++;
        }

        return new BigDecimalFraction(p, q);
    }

    private static boolean equationSatisfied(BigDecimal x, BigDecimal y, int parameter) {
        return x.multiply(x).subtract(BigDecimal.valueOf(parameter).multiply(y).multiply(y)).equals(BigDecimal.ONE);
    }

    private static boolean isSquare(int parameter) {
        double squareRoot = Math.sqrt(parameter);
        return (int)squareRoot == squareRoot;
    }

    private static class BigDecimalFraction {
        BigDecimal p;
        BigDecimal q;

        public BigDecimalFraction(BigDecimal p, BigDecimal q) {
            this.p = p;
            this.q = q;
        }

        @Override
        public String toString() {
            return "p=" + p + ", q=" + q;
        }
    }

}
