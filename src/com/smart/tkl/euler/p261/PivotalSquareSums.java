package com.smart.tkl.euler.p261;

import com.smart.tkl.lib.utils.LongPoint;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.continuedfraction.PellsEquationSolver;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PivotalSquareSums {

    private final long limit;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long limit = 10000000000L;
        PivotalSquareSums pivotalSquareSums = new PivotalSquareSums(limit);
        long sum = pivotalSquareSums.sumAllPivots();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public PivotalSquareSums(long limit) {
        this.limit = limit;
    }

    public long sumAllPivots() {
        Set<Long> pivots = new TreeSet<>();

        int maxM = (-1 + (int)Math.sqrt(1 + 2 * this.limit)) / 2;
        int[] squareFreeFactors = new int[maxM + 2];
        int[] squareRootFactors = new int[maxM + 2];

        for(int m = 1; m <= maxM + 1; m++) {
            List<PrimeFactor> factors = MathUtils.listPrimeFactors(m);
            int squareFreePart = 1;
            int squareRootPart = 1;
            for(PrimeFactor primeFactor : factors) {
                int pow = primeFactor.getPow();
                long prime = primeFactor.getFactor();
                if(pow % 2 == 1) {
                    squareFreePart *= prime;
                }
                if(pow / 2 > 0) {
                    pow = pow / 2;
                    squareRootPart *= (long) Math.pow(prime, pow);
                }
            }
            squareFreeFactors[m] = squareFreePart;
            squareRootFactors[m] = squareRootPart;
        }

        for(int m = 1; m <= maxM; m++) {
            int u = squareFreeFactors[m];
            int v = squareFreeFactors[m + 1];
            int w = squareRootFactors[m];
            int z = squareRootFactors[m + 1];

            long d = (long)u * v;

            LongPoint solution = PellsEquationSolver.solve(d);

            long x1 = solution.getX();
            long y1 = solution.getY();
            long x = x1, y = y1;

            long product = (long)v * u * w * z;

            while (true) {
               long h = y * product;
               long doubleK = m * (x + 1) + h;
               if(doubleK > 2 * this.limit) {
                  break;
               }

               long doubleN = (m + 1) * (x - 1) + h;
               if(doubleN >= doubleK && doubleK % 2 == 0 && doubleN % 2 == 0) {
                  long k = doubleK / 2;
                  pivots.add(k);
               }
               long newX = x1 * x + d * y1 * y;
               long newY = x1 * y + y1 * x;
               x = newX;
               y = newY;
            }
        }

       // System.out.println("Pivots: " + pivots);

        long sum = 0;
        for(long pivot : pivots) {
            sum += pivot;
        }
        return sum;
    }
}
