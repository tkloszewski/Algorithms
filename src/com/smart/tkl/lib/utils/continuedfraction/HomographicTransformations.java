package com.smart.tkl.lib.utils.continuedfraction;

import static com.smart.tkl.lib.utils.continuedfraction.ContinuedFractionUtils.toContinuedFractions;

import com.smart.tkl.lib.utils.Fraction;
import java.util.ArrayList;
import java.util.List;

public class HomographicTransformations {

    public static void main(String[] args) {
        Fraction fraction = findSquareRootConvergent(2, 49);
        System.out.println("49-th Convergent of square root of 2: " + fraction + " => " + fraction.toBigDecimal());

        fraction = findSquareRootConvergent(13, 49);
        System.out.println("49-th Convergent of square root of 13: " + fraction + " => " + fraction.toBigDecimal());

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

    public static Fraction findSquareRootConvergent(int n, long k) {
         SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(n);
         PeriodicFraction periodicFraction = generator.generate();
         if(periodicFraction.getPeriodSize() == 0) {
            return new Fraction(periodicFraction.getBase(), 1);
         }

         long a0 = periodicFraction.getBase();
         List<Long> sequence = periodicFraction.getCoefficients();
         long periodSize = periodicFraction.getPeriodSize();
         long numOfPeriods = k / periodSize;


         /*L([a[k], 1, 1, 0], [0, 1, 0, 0]) => [0, a[k], 0, 1]
           L([a[k-1], 1, 1, 0], [0, a[k], 0, 1]) => [0, a[k-1]a[k] + 1, 0, a[k]]
         * */
         long[] trans = new long[]{0, 1, 0, 0}; //first combine() brings last element of period sequence
         for(int i = sequence.size() - 1; i % periodSize != 0; i--) {
             //(a[i] * x + 1)/x => [a[i], 1, 1, 0)
             trans = combine(new long[]{sequence.get(i), 1, 1, 0}, trans);
         }

         if(numOfPeriods > 0) {
            long[] baseTrans = new long[]{1, 0, 0, 1};
            for(int i = sequence.size() - 1; i >= 0; i--) {
                baseTrans = combine(new long[]{sequence.get(i), 1, 1, 0}, baseTrans);
            }
            trans = combine(pow(baseTrans, numOfPeriods), trans);
         }

         trans = combine(new long[]{a0, 1, 1, 0}, trans);

         return new Fraction(trans[1], trans[3]);
    }

    public static long[] pow(long[] A, long pow) {
        if(pow == 0) {
           return new long[]{1, 0, 0, 1};
        }
        if(pow % 2 == 1) {
           return combine(A, pow(A, pow - 1));
        }
        else {
            return pow(combine(A, A), pow / 2);
        }
    }

    public static long[] combine(long[] A, long[] B) {
        return new long[] {
                A[0] * B[0] + A[1] * B[2], A[0] * B[1] + A[1] * B[3],
                A[2] * B[0] + A[3] * B[2], A[2] * B[1] + A[3] * B[3]
        };
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
