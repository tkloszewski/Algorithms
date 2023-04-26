package com.smart.tkl.euler.p73;

import com.smart.tkl.euler.p71.NearestReducedFraction;
import com.smart.tkl.lib.utils.Fraction;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.ReduceFraction;

import java.util.*;

public class FractionRangeCounter {

    private final ReduceFraction lowerBound;
    private final ReduceFraction upperBound;
    private final int denominatorLimit;

    public FractionRangeCounter(ReduceFraction lowerBound, ReduceFraction upperBound, int limit) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.denominatorLimit = limit;
    }

    public static void main(String[] args) {
        long time1, time2, count;

        FractionRangeCounter fractionsRange = new FractionRangeCounter(new ReduceFraction(1, 3), new ReduceFraction(1, 2), 12000);

        time1 = System.currentTimeMillis();
        count = fractionsRange.fastCount();
        time2 = System.currentTimeMillis();
        System.out.printf("Fast count: %d .Execution took %d ms.\n", count, (time2 - time1));

        time1 = System.currentTimeMillis();
        count = fractionsRange.fareySequenceCount();
        time2 = System.currentTimeMillis();
        System.out.printf("Farey sequence count: %d .Execution took %d ms.\n", count, (time2 - time1));

        time1 = System.currentTimeMillis();
        count = fractionsRange.gcdCount();
        time2 = System.currentTimeMillis();
        System.out.printf("GCD based count: %d .Execution took %d ms.\n", count, (time2 - time1));

        time1 = System.currentTimeMillis();
        count = fractionsRange.slowCount();
        time2 = System.currentTimeMillis();
        System.out.printf("Slow count: %d .Execution took %d ms.\n", count, (time2 - time1));
    }

    public long fastCount() {
        long a = lowerBound.getNumerator();
        long b = lowerBound.getDenominator();
        long c = upperBound.getNumerator();
        long d = upperBound.getDenominator();

        RangeCounter[] rangeCounters = new RangeCounter[this.denominatorLimit + 1];
        rangeCounters[0] = new RangeCounter(0, 0, 0);
        rangeCounters[1] = new RangeCounter(1, 1, 1);
        for(int q = 2; q <= denominatorLimit; q++) {
            long r1 = (a * q) / b + 1;
            long r2 = (c * q - 1) / d;
            rangeCounters[q] = new RangeCounter(r1, r2, q);
        }

        long result = 0;
        long[] usedFractionsCount = new long[this.denominatorLimit + 1];
        for(int i = 2; i <= this.denominatorLimit; i++) {
            long fractionsCount = rangeCounters[i].counter;
            if(fractionsCount == 0) {
               continue;
            }
            long remainedFractions = fractionsCount - usedFractionsCount[i];
            if(remainedFractions > 0) {
               result += remainedFractions;
               for(int j = 2 * i; j <= this.denominatorLimit; j += i) {
                   usedFractionsCount[j] += remainedFractions;
               }
            }
        }

        return result;
    }

    public int gcdCount() {
        int result = 0;

        long a = this.lowerBound.getNumerator();
        long b = this.lowerBound.getDenominator();
        long c = this.upperBound.getNumerator();
        long d = this.upperBound.getDenominator();

        for(int i = 2; i <= this.denominatorLimit; i++) {
            for(int j = 1; j < i; j++) {
               if(j * b > i * a && c * i > j * d && MathUtils.GCD(j, i) == 1) {
                  result++;
               }
            }
        }
        return result;
    }

    public int slowCount() {
        int result = 0;
        ReduceFraction fraction1 = new ReduceFraction(1, 2);
        ReduceFraction fraction2 = new ReduceFraction(1, 3);

        Queue<ReduceFraction> queue = new LinkedList<>();
        queue.add(fraction1);
        queue.add(fraction2);

        while (!queue.isEmpty()) {
            ReduceFraction fraction = queue.poll();
            if(fraction.getDenominator() <= denominatorLimit) {
                if(isBetween(fraction)) {
                    result++;
                }
                queue.addAll(getTriplet(fraction));
            }
        }



        return result;
    }

    public int fareySequenceCount() {
        long a = 1;
        long b = 3;

        NearestReducedFraction nearestFraction = new NearestReducedFraction(this.denominatorLimit, new Fraction(a, b));
        Fraction fraction = nearestFraction.findNearestRight();

        long c = fraction.getNumerator();
        long d = fraction.getDenominator();

        int result = 0;
        while (c != this.upperBound.getNumerator() || d != this.upperBound.getDenominator()) {
            result++;

            long k = (this.denominatorLimit + b) / d;
            long e = k * c - a;
            long f = k * d - b;

            a = c;
            b = d;
            c = e;
            d = f;
        }

        return result;
    }

    private List<ReduceFraction> getTriplet(ReduceFraction fraction) {
        List<ReduceFraction> triplet = new ArrayList<>();

        long n = fraction.getNumerator();
        long m = fraction.getDenominator();

        triplet.add(new ReduceFraction(m, 2 * m - n));
        triplet.add(new ReduceFraction(m, 2 * m + n));
        triplet.add(new ReduceFraction(n, m + 2 * n));

        return triplet;
    }

    private boolean isBetween(ReduceFraction fraction) {
        long p1 = lowerBound.getNumerator();
        long q1 = lowerBound.getDenominator();
        long p2 = upperBound.getNumerator();
        long q2 = upperBound.getDenominator();

        long n = fraction.getNumerator();
        long m = fraction.getDenominator();

        return n * q1 - m * p1 > 0 && m * p2 - n * q2 > 0;
    }

    private static class RangeCounter {
        long r1;
        long r2;
        long q;
        long counter;

        public RangeCounter(long r1, long r2, long q) {
            this.r1 = r1;
            this.r2 = r2;
            this.q = q;
            this.counter = r2 - r1 + 1;
        }

        @Override
        public String toString() {
            return "{" +
                    "r1=" + r1 +
                    ", r2=" + r2 +
                    ", q=" + q +
                    ", counter=" + counter +
                    '}';
        }
    }

}
