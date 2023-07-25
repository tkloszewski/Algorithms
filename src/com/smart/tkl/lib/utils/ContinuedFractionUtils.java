package com.smart.tkl.lib.utils;

import static com.smart.tkl.lib.utils.MathUtils.toFraction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContinuedFractionUtils {

    public static void main(String[] args) {
        System.out.println(solveLinearEquation(5, 3, 13));
        System.out.println("Convergents: " + toConvergentList(127, 1293));
        System.out.println(compareContinuedFractions(toContinuedFractions(12, 13), toContinuedFractions(14, 13)));
        System.out.println("To fraction sqrt(2): " + toContinuedFractions(toFraction(Math.sqrt(2))));
        System.out.println("Continued fractions: " + toContinuedFractions(43, 19));
        System.out.println("From continued fraction: " + fromContinuedFractions(toContinuedFractions(43, 19)));

        List<Long> f = toContinuedFractions(127, 41);
        System.out.println(f);
        System.out.println(toContinuedFractions(254, 41));
        System.out.println(homographic(f, 2, 0 ,0, 1));

        List<Long> x = toContinuedFractions(17, 13);
        List<Long> y = toContinuedFractions(14, 13);
        List<Long> sum = toContinuedFractions(31, 13);
        List<Long> product = toContinuedFractions(238, 169);

        System.out.println("x= "+ x);
        System.out.println("y=" + y);
        System.out.println("sum= " + sum);
        System.out.println("product= " + product);
        System.out.println("Transformation sum: " + add(x, y));
        System.out.println("Transformation product: " + multiply(x, y));
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

    public static List<Long> multiply(List<Long> x, List<Long> y) {
        return biHomographic(x, y, 1, 0, 0, 0, 0, 0, 0, 1);
    }

    public static List<Long> add(List<Long> x, List<Long> y) {
        return biHomographic(x, y, 0, 1, 1, 0, 0, 0, 0, 1);
    }

    public static List<Long> homographic(List<Long> f, long a, long b, long c, long d) {
        List<Long> result = new ArrayList<>();
        doHomographic(f, 0, a, b, c, d, result);
        return result;
    }

    public static List<Long> biHomographic(List<Long> x, List<Long> y, long a, long b, long c, long d,
                                           long e, long f, long g, long h) {
        List<Long> result = new ArrayList<>();
        doBiHomographic(x, y, 0 ,0 , a, b, c, d, e, f, g, h, result);
        return result;
    }

    private static void doHomographic(List<Long> input, int k, long a, long b, long c, long d, List<Long> output) {
        if(k >= input.size()) {
            List<Long> rest = toContinuedFractions(new Fraction(a, c));
            output.addAll(rest);
        }
        else if(a == 0 && c == 0) {
            output.addAll(toContinuedFractions(new Fraction(b, d)));
        }
        else if(c != 0 && d != 0 && a/c == b/d) {
            long out = a/c;
            System.out.println("Transformed term: " + out);
            output.add(out);
            doHomographic(input, k, c, d, a % c, b % d, output);
        }
        else {
            long term = input.get(k);
            doHomographic(input, k + 1, a * term + b, a, term * c + d, c, output);
        }
    }


    private static void doBiHomographic(List<Long> x, List<Long> y, int i, int j, long a, long b, long c, long d,
                                    long e, long f, long g, long h, List<Long> output) {
         if(i >= x.size()) {
             // x is infinity
            doHomographic(y, j, a, b, e, f, output);
         }
         else if(j >= y.size()) {
             // y is infinity
            doHomographic(x, i, a, c, e, g, output);
         }
         else if(a == 0 && b == 0 && e == 0 && f == 0) {
             doHomographic(y, j, c, d, g, h, output);
         }
         else if(a == 0 && c == 0 && e == 0 && g == 0) {
             doHomographic(x, i, b, d, f, h, output);
         }
         else if(e != 0 && f != 0 && g != 0 && h != 0 &&
                 a/e == b/f && b/f == c/g && c/g == d/h ) {
             output.add(a/e);
             doBiHomographic(x, y, i, j, e, f, g, h, a % e, b % f, c % g, d % h, output);
         }
         else if(f == 0 || g == 0 || h == 0 || absDiff(b, f, d, h).compareTo(absDiff(c, g, d, h)) > 0) {
            long term = x.get(i);
            doBiHomographic(x, y, i + 1, j, a * term + c, b * term + d, a, b,
                    e * term + g, f * term + h, e, f, output);
         }
         else {
             long term = y.get(i);
             doBiHomographic(x, y, i, j + 1, a * term + b, a, c * term + d, c,
                     e * term + f, e, g * term + h, g, output);
         }
    }



    private static Fraction absDiff(long p1, long q1, long p2, long q2) {
        return new Fraction(Math.abs(p1 * q2 - p2 * q1), q1 * q2);
    }
}
