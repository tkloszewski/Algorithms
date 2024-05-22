package com.smart.tkl.lib.utils.continuedfraction;

import static com.smart.tkl.lib.utils.continuedfraction.ContinuedFractionUtils.coefficient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SquareRootPeriodicFractionGenerator {

    private final long value;
    private long m0;
    private long d0;

    public SquareRootPeriodicFractionGenerator(long value) {
        this(value, 0, 1);
    }

    public SquareRootPeriodicFractionGenerator(long value, long m0, long d0) {
        this.value = value;
        this.m0 = m0;
        this.d0 = d0;
    }

    public static void main(String[] args) {
        //Check for 10^7
        long a = (long)Math.pow(10, 5);
        long c = (long) Math.pow(10, 5);
        long value = 332;

        long time1 = System.currentTimeMillis();
        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(value, a, c);
        ContinuedFraction periodicFraction = generator.generate();
        long time2 = System.currentTimeMillis();
        System.out.println(periodicFraction);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public ContinuedFraction generate() {
        double exactSqrt = Math.sqrt(this.value);
        long a0 = (m0 + (long) exactSqrt) / d0;

        if((long)exactSqrt == exactSqrt) {
           return ContinuedFraction.ofSimpleContinueFraction(value, a0,
                   ContinuedFractionUtils.toContinuedFractions(m0 + (long) a0, d0));
        }

        long value = this.value;
        long sqrt = (long)exactSqrt;

        if(a0 <= 0) {
           a0 = coefficient(m0, sqrt, d0);
        }

        if((value - m0 * m0) % d0 != 0) {
           value = value * d0 * d0;
           m0 = m0 * d0;
           sqrt = (long)(d0 * exactSqrt);
           d0 = d0 * d0;
        }

        List<Long> coefficients = new ArrayList<>();

        Triplet baseTriplet = new Triplet(a0, m0, d0);
        Triplet triplet = nextTriplet(baseTriplet, value, sqrt);

        Map<Triplet, Integer> generatedTriplets = new HashMap<>();
        generatedTriplets.put(baseTriplet, 0);

        int periodIndex = 0;

        while(!generatedTriplets.containsKey(triplet)) {
            generatedTriplets.put(triplet, periodIndex);
            coefficients.add(triplet.coefficient);
            triplet = nextTriplet(triplet, value, sqrt);
            periodIndex++;
        }

        periodIndex = generatedTriplets.get(triplet);

        return new ContinuedFraction(this.value, a0, periodIndex, coefficients);
    }


    private Triplet nextTriplet(Triplet triplet, long value, long sqrt) {
        long nextM = triplet.coefficient * triplet.d - triplet.m;
        long nextD = (value - nextM * nextM) / triplet.d;
        long nextCoefficient = coefficient(nextM,sqrt, nextD);
        return new Triplet(nextCoefficient, nextM, nextD);
    }

    /*In the form of (S^0.5 + m)/n*/
    private static class Triplet {
        long coefficient;
        long m;
        long d;

        public Triplet(long coefficient, long m, long d) {
            this.coefficient = coefficient;
            this.m = m;
            this.d = d;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triplet triplet = (Triplet) o;
            return m == triplet.m &&
                    d == triplet.d &&
                    coefficient == triplet.coefficient;
        }

        @Override
        public int hashCode() {
            return Objects.hash(m, d, coefficient);
        }

        @Override
        public String toString() {
            return "Triplet{" +
                    "coefficient=" + coefficient +
                    ", a=" + m +
                    ", c=" + d +
                    '}';
        }
    }
}
