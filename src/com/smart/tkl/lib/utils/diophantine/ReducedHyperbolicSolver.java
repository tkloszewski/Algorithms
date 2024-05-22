package com.smart.tkl.lib.utils.diophantine;

import static com.smart.tkl.lib.utils.continuedfraction.ContinuedFractionUtils.coefficient;

import com.smart.tkl.lib.utils.Divisors;
import com.smart.tkl.lib.utils.XYPair;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class ReducedHyperbolicSolver {

    private static final BigInteger MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    private final BigInteger convergentLimit;

    public ReducedHyperbolicSolver() {
        this(null);
    }

    public ReducedHyperbolicSolver(BigInteger convergentLimit) {
        this.convergentLimit = convergentLimit;
    }

    public static void main(String[] args) {
        long a = 171;
        long b = 0;
        long c = -79;
        long f = -92;

        ReducedHyperbolicSolver solver = new ReducedHyperbolicSolver();

        long time1 = System.currentTimeMillis();
        List<XYPair> solutions = solver.getSolutions(a, b, c, f);
        long time2 = System.currentTimeMillis();

        System.out.println("Representative solutions: " + solutions);
        System.out.println("Time in ms: " + (time2 - time1));


        a = 171;
        b = 0;
        c = -79;
        f = -92;

        time1 = System.currentTimeMillis();
        solutions = solver.getSolutions(a, b, c, f);
        time2 = System.currentTimeMillis();

        System.out.println("Representative solutions: " + solutions);
        System.out.println("Time in ms: " + (time2 - time1));
    }


    /*
     * Solutions for ax^2 + bxy + cy^2 where b is even and gcd(a, b, c, f) = 1
     * */
    public List<XYPair> getSolutions(long a, long b, long c, long f) {
        List<XYPair> solutions = new ArrayList<>();

        long delta = b * b - 4 * a * c;
        f = Math.abs(f);

        List<Long> squareDivisors = Divisors.listSquareDivisors(f);
        for(long divisor : squareDivisors) {
            BigInteger factor = BigInteger.valueOf((long) Math.sqrt(divisor));
            Set<XYPair> primitiveSolutions = getPrimitiveSolutions(a, b, c, f / divisor, delta);
            for(XYPair primitiveSolution : primitiveSolutions) {
                solutions.add(new XYPair(primitiveSolution.getX().multiply(factor), primitiveSolution.getY().multiply(factor)));
            }
        }

        return solutions;
    }

    private Set<XYPair> getPrimitiveSolutions(long a, long b, long c, long f, long delta) {
        Set<XYPair> primitiveSolutions = new LinkedHashSet<>();
        List<Long> thetas = QuadraticCongruenceSolver.solve(a, b, c, f);
        for(long theta : thetas) {
            List<XYPair> solutions = findPrimitiveSolutions(a, b, f, delta, theta);
            primitiveSolutions.addAll(solutions);
        }
        return primitiveSolutions;
    }

    private List<XYPair> findPrimitiveSolutions(long a, long b, long f, long delta, long theta) {
        List<XYPair> result = new ArrayList<>();
        long P = a * theta + b / 2;
        long Q = a * Math.abs(f);
        Optional<XYPair> solution1 = findPrimitiveSolutionEvenWithQuotientBI(f, delta / 4, theta, -P, Q, 1);
        Optional<XYPair> solution2 = findPrimitiveSolutionEvenWithQuotientBI(f, delta / 4, theta, P, -Q, -1);
        if(solution1.isPresent()) {
            result.add(solution1.get());
        }
        if(solution2.isPresent()) {
            result.add(solution2.get());
        }

        return result;
    }

    private Optional<XYPair> findPrimitiveSolutionEven(long f, long delta, long theta, long P, long Q, int target) {
        double exactSqrt = Math.sqrt(delta);
        long sqrt = (long) exactSqrt;
        long a0 = coefficient(P, sqrt, Q);

        boolean overflow = false;
        BigInteger PBI = BigInteger.valueOf(P);
        BigInteger QBI = BigInteger.valueOf(Q);
        if(PBI.multiply(PBI).compareTo(MAX_LONG_VALUE) > 0 || QBI.multiply(QBI).compareTo(MAX_LONG_VALUE) > 0) {
           overflow = true;
        }

        if(!overflow && (delta - P * P) % Q != 0) {
           if(BigInteger.valueOf(delta).multiply(QBI).multiply(QBI).compareTo(MAX_LONG_VALUE) > 0) {
              overflow = true;
           }
        }

        if(overflow) {
           return findPrimitiveSolutionEvenWithQuotientBI(f, delta, theta, P, Q, target);
        }

        if((delta - P * P) % Q != 0) {
            delta = delta * Q * Q;
            P = P * Q;
            sqrt = (long)(Q * exactSqrt);
            Q = Q * Q;
        }

        Surd surd = new Surd(P, Q, a0);
        XYPair prevConvergent = new XYPair(BigInteger.ONE, BigInteger.ZERO);
        XYPair currentConvergent = new XYPair(BigInteger.valueOf(a0), BigInteger.ONE);

        Map<Surd, Integer> generatedQuotients = new HashMap<>();
        generatedQuotients.put(surd, 0);

        int i = 1, periodLength = -1, periodStart = -1;
        while (true) {
            surd = nextSurd(surd, delta, sqrt);
            XYPair oldConvergent = currentConvergent;
            currentConvergent = nextConvergent(currentConvergent, prevConvergent, surd.coefficient);
            prevConvergent = oldConvergent;

            if(convergentLimit != null && convergentLimit.compareTo(prevConvergent.getY().abs()) < 0) {
               return Optional.empty();
            }

            if(periodLength == -1 || periodStart != -1) {
                if(generatedQuotients.containsKey(surd)) {
                    periodStart = generatedQuotients.get(surd) - 1;
                    periodLength = generatedQuotients.size() - 1 - periodStart;
                }
                else {
                    generatedQuotients.put(surd, i);
                }
            }

            Q = surd.c;

            if(Math.abs(Q) == Math.abs(target)) {
                if ((i % 2 == 0 && Q == target) || Q == -target) {
                    return Optional.of(solutionFromConvergent(prevConvergent, BigInteger.valueOf(theta), BigInteger.valueOf(f)));
                }
            }

            if(periodLength != -1 && periodStart != -1) {
                if(periodLength % 2 == 0 && i >= periodStart + periodLength) {
                    break;
                }
                if(periodLength % 2 == 1 && i >= periodStart + 2 * periodLength) {
                    break;
                }
            }

            i++;
        }
        return Optional.empty();
    }

    private Optional<XYPair> findPrimitiveSolutionEvenWithQuotientBI(long f, long delta, long theta, long p, long q, int target) {
        double exactSqrt = Math.sqrt(delta);
        long sqrt = (long) exactSqrt;
        long a0 = coefficient(p, sqrt, q);


        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger D = BigInteger.valueOf(delta);

        if(D.subtract(P.multiply(P)).mod(Q.abs()).longValue() != 0) {
           D = D.multiply(Q).multiply(Q);
           P = P.multiply(Q);
           sqrt = (long)(q * exactSqrt);
           Q = Q.multiply(Q);
        }

        BigInteger SQRT = BigInteger.valueOf(sqrt);
        BigInteger T = BigInteger.valueOf(target);

        SurdBI surd = new SurdBI(P, Q, a0);
        XYPair prevConvergent = new XYPair(BigInteger.ONE, BigInteger.ZERO);
        XYPair currentConvergent = new XYPair(BigInteger.valueOf(a0), BigInteger.ONE);

        Map<SurdBI, Integer> generatedQuotients = new HashMap<>();
        generatedQuotients.put(surd, 0);

        int i = 1, periodLength = -1, periodStart = -1;
        while (true) {
            surd = nextSurdBI(surd, D, SQRT);
            XYPair oldConvergent = currentConvergent;
            currentConvergent = nextConvergent(currentConvergent, prevConvergent, surd.coefficient);
            prevConvergent = oldConvergent;

            if(convergentLimit != null && convergentLimit.compareTo(prevConvergent.getY().abs()) < 0) {
                return Optional.empty();
            }

            if(periodLength == -1 || periodStart != -1) {
                if(generatedQuotients.containsKey(surd)) {
                    periodStart = generatedQuotients.get(surd) - 1;
                    periodLength = generatedQuotients.size() - 1 - periodStart;
                }
                else {
                    generatedQuotients.put(surd, i);
                }
            }

            Q = surd.c;

            if(Q.abs().equals(T.abs())) {
                if ((i % 2 == 0 && Q.equals(T)) || (i % 2 == 1 && Q.equals(T.negate()))) {
                    XYPair solution = solutionFromConvergent(prevConvergent, BigInteger.valueOf(theta), BigInteger.valueOf(f));
                    return Optional.of(solution);
                }
            }

            if(periodLength != -1 && periodStart != -1) {
                if(periodLength % 2 == 0 && i >= periodStart + periodLength) {
                    break;
                }
                if(periodLength % 2 == 1 && i >= periodStart + 2 * periodLength) {
                    break;
                }
            }

            i++;
        }
        return Optional.empty();

    }

    private static XYPair solutionFromConvergent(XYPair convergent, BigInteger theta, BigInteger absF) {
        BigInteger X = convergent.getX();
        BigInteger y = convergent.getY();

        // x = y * theta + |f| * X
        BigInteger x = y.multiply(theta).add(absF.multiply(X));

        return new XYPair(x, y);
    }

    private static XYPair nextConvergent(XYPair convergent, XYPair prevConvergent, long coefficient) {
        BigInteger coefficientBI = BigInteger.valueOf(coefficient);
        BigInteger numerator = convergent.getX().multiply(coefficientBI).add(prevConvergent.getX());
        BigInteger denominator = convergent.getY().multiply(coefficientBI).add(prevConvergent.getY());
        return new XYPair(numerator, denominator);
    }
    private static Surd nextSurd(Surd pair, long value, long sqrt) {
        long nextA = pair.coefficient * pair.c - pair.a;
        long nextC = (value - nextA * nextA) / pair.c;
        long nextCoefficient = coefficient(nextA, sqrt, nextC);
        return new Surd(nextA, nextC, nextCoefficient);
    }

    private static SurdBI nextSurdBI(SurdBI pair, BigInteger value, BigInteger sqrt) {
        BigInteger nextA = BigInteger.valueOf(pair.coefficient).multiply(pair.c).subtract(pair.a);
        BigInteger nextC = value.subtract(nextA.multiply(nextA)).divide(pair.c);
        long nextCoefficient = toCoefficient(nextA, sqrt, nextC);
        return new SurdBI(nextA, nextC, nextCoefficient);
    }

    private static long toCoefficient(BigInteger m, BigInteger sqrt, BigInteger d) {
        BigInteger numerator = m.add(sqrt);
        return numerator.divide(d).longValue();
    }

    private static class SurdBI {
        BigInteger a;
        BigInteger c;
        long coefficient;

        public SurdBI(BigInteger a, BigInteger c, long coefficient) {
            this.a = a;
            this.c = c;
            this.coefficient = coefficient;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SurdBI pair = (SurdBI) o;
            return a.equals(pair.a) && c.equals(pair.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, c);
        }

        @Override
        public String toString() {
            return "Triplet{" +
                    "a=" + a +
                    ", c=" + c +
                    '}';
        }
    }


    private static class Surd {
        long a;
        long c;
        long coefficient;

        public Surd(long a, long c, long coefficient) {
            this.a = a;
            this.c = c;
            this.coefficient = coefficient;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Surd pair = (Surd) o;
            return a == pair.a && c == pair.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, c);
        }

        @Override
        public String toString() {
            return "Triplet{" +
                    "a=" + a +
                    ", c=" + c +
                    '}';
        }
    }
}
