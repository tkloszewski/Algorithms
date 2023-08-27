package com.smart.tkl.lib.utils;

import java.util.List;

public class PellsEquationSolver {

    public static void main(String[] args) {
        LongPoint solution = solve(999999999);
        System.out.println("Solution: " + solution);
    }

    public static LongPoint solve(long d) {
        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(d);
        PeriodicFraction periodicFraction = generator.generate();
        List<Long> sequence = periodicFraction.getSequence();

        if(sequence.size() == 0) {
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

    private static boolean solutionSatisfied(long x, long y, long d) {
        return x * x - d * y * y == 1;
    }



}
