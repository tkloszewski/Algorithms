package com.smart.tkl.lib.utils;

import java.util.List;

public class PellsEquationSolver {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long d = (long)Math.pow(10, 14) + 1;
        LongPoint solution = solve(d);
        long time2 = System.currentTimeMillis();
        System.out.println("Solution: " + solution);
        System.out.println("Solution took in ms: " + (time2 - time1));
        solution = solve(3);
        System.out.println("Reduced solution: " + solution);

        int k = 0;
        boolean[] squares = new boolean[169];
        for(int i = 1; i < 169; i++) {
            int r = (i * i) % 169;
            squares[r] = true;
        }
        for(int i = 1; i < 169; i++) {
            if(squares[i]) {
               k++;
            }
        }
        System.out.println("Number of quadratic residues: " + k);
    }

    public static LongPoint solve(long d) {
        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(d);
        PeriodicFraction periodicFraction = generator.generate();
        List<Long> sequence = periodicFraction.getSequence();

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

    private static boolean solutionSatisfied(long x, long y, long d) {
        return x * x - d * y * y == 1;
    }



}
