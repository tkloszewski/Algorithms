package com.smart.tkl.lib.utils.diophantine;

import com.smart.tkl.lib.utils.Divisors;
import com.smart.tkl.lib.utils.LongPoint;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.XYPair;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import com.smart.tkl.lib.utils.continuedfraction.ContinuedFraction;
import com.smart.tkl.lib.utils.continuedfraction.ConvergentsIterator;
import com.smart.tkl.lib.utils.continuedfraction.SquareRootPeriodicFractionGenerator;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HomogeneousSolver {

    public static void main(String[] args) {
        List<XYPair> solutions = solveReduced(304, 725, 432);
        System.out.println("Solutions: " + solutions);

        solutions = solve(8, 0, -3, -5);
        System.out.println("Solutions: " + solutions);

        long d = 23;
        LongPoint pellsSolution = PellsEquationSolver.solve(23);
        System.out.println("Pells solution 1: " + pellsSolution);

        solutions = solveReduced(1, 0, -d);
        System.out.println("Pells solution 2: " + solutions);
    }

    public static List<XYPair> solve(long a, long b, long c, long f) {
        Set<XYPair> uniqueSolutions = new LinkedHashSet<>();
        long delta = b * b - 4 * a * c;
        if(delta <= 0) {
           return List.of();
        }
        double sqrt = Math.sqrt(delta);
        if(sqrt == (long)sqrt) {
           return List.of();
        }
        long g = MathUtils.GCD(a, b, c);
        if(f % g != 0) {
           return List.of();
        }
        a = a / g;
        b = b / g;
        c = c / g;
        f = f / g;

        List<Long> squareDivisors = Divisors.listSquareDivisors(Math.abs(f));
        for(long divisor : squareDivisors) {
            long sqrtDiv = (long) Math.sqrt(divisor);
            List<XYPair> coPrimeCoefficientSolutions = solveForCoprimeCoefficients(a, b, c, f / divisor);
            if(!coPrimeCoefficientSolutions.isEmpty()) {
               BigInteger multiplier = BigInteger.valueOf(sqrtDiv);
               for(XYPair sol : coPrimeCoefficientSolutions) {
                   BigInteger x = sol.getX();
                   BigInteger y = sol.getY();
                   uniqueSolutions.add(new XYPair(x.multiply(multiplier), y.multiply(multiplier)));
               }
            }
        }

        return new ArrayList<>(uniqueSolutions);
    }

    private static List<XYPair> solveForCoprimeCoefficients(long a, long b, long c, long f) {
        List<Long> congruenceSolutions = QuadraticCongruenceSolver.solve(a, b, c, Math.abs(f));
        if(congruenceSolutions.isEmpty()) {
           return List.of();
        }

        List<XYPair> result = new ArrayList<>();

        for(long s : congruenceSolutions) {
            long newA = -(a * s * s + b * s + c) / f;
            long newB = 2 * a * s + b;
            long newC = - a * f;
            List<XYPair> basicSolutions = solveReduced(newA, newB, newC);
            if(!basicSolutions.isEmpty()) {
                BigInteger sBI = BigInteger.valueOf(s);
                BigInteger fBI = BigInteger.valueOf(f);
                for (XYPair basicSolution : basicSolutions) {
                    BigInteger x = basicSolution.getX().multiply(sBI).subtract(fBI.multiply(basicSolution.getY()));
                    BigInteger y = basicSolution.getX();
                    result.add(new XYPair(x, y));
                }
            }
        }

        return result;
    }



    /*
     * Solves diophantine equation: ax^2 + bxy + cy^2 = 1
     *
     * */
    public static List<XYPair> solveReduced(long a, long b, long c) {
        long delta = b * b - 4 * a * c;
        if(delta < 0) {
            return List.of();
        }
        double sqrt = Math.sqrt(delta);
        if(sqrt == (long)sqrt) {
            return List.of();
        }

        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(delta, -b, 2 * a);
        ContinuedFraction continuedFraction = generator.generate();
        ConvergentsIterator convergentsIterator = new ConvergentsIterator(continuedFraction);

        BigInteger aBI = BigInteger.valueOf(a);
        BigInteger bBI = BigInteger.valueOf(b);
        BigInteger cBI = BigInteger.valueOf(c);

        List<XYPair> result = new ArrayList<>(2);

        Optional<XYPair> solution1 = findSolution(aBI, bBI, cBI, convergentsIterator, continuedFraction);
        solution1.ifPresent(result::add);

        generator = new SquareRootPeriodicFractionGenerator(delta, b, -2 * a);
        continuedFraction = generator.generate();
        convergentsIterator = new ConvergentsIterator(continuedFraction);

        Optional<XYPair> solution2 = findSolution(aBI, bBI, cBI, convergentsIterator, continuedFraction);
        solution2.ifPresent(result::add);

        return result;
    }

    private static Optional<XYPair> findSolution(BigInteger a, BigInteger b, BigInteger c, ConvergentsIterator iterator, ContinuedFraction continuedFraction) {
        int index = 0;
        while (iterator.hasNext()) {
            XYPair xyPair = iterator.next();
            if(satisfiesEquation(a, b, c, xyPair.getX(), xyPair.getY())) {
                return Optional.of(xyPair);
            }
            index++;
            if(continuedFraction.getPeriodIndex() != -1 &&
                    index > 2 * continuedFraction.getPeriodSize() + continuedFraction.getPeriodIndex()) {
                break;
            }
        }
        return Optional.empty();
    }

    private static boolean satisfiesEquation(BigInteger a, BigInteger b, BigInteger c, BigInteger x, BigInteger y) {
        BigInteger value = a.multiply(x).multiply(x).add(b.multiply(x).multiply(y)).add(y.multiply(y).multiply(c));
        return value.equals(BigInteger.ONE);
    }

}
