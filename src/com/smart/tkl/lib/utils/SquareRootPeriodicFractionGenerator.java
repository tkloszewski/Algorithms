package com.smart.tkl.lib.utils;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class SquareRootPeriodicFractionGenerator {

    private final int value;

    public SquareRootPeriodicFractionGenerator(int value) {
        this.value = value;
    }

    public PeriodicFraction generate() {
        double squareRoot = Math.sqrt(this.value);
        int a0 = (int)squareRoot;

        PeriodicFraction periodicFraction = new PeriodicFraction(this.value, a0);

        if(a0 == squareRoot) {
           return periodicFraction;
        }

        Triplet baseTriplet = new Triplet(a0, 0, 1);
        Triplet triplet = nextTriplet(baseTriplet, a0);

        Set<Triplet> generatedTriplets = new LinkedHashSet<>();
        generatedTriplets.add(baseTriplet);

        while(!generatedTriplets.contains(triplet)) {
            generatedTriplets.add(triplet);
            periodicFraction.addToSequence(triplet.a);
            triplet = nextTriplet(triplet, a0);
        }

        return periodicFraction;
    }


    private Triplet nextTriplet(Triplet triplet, int a0) {
        int nextM = triplet.a * triplet.d - triplet.m;
        int nextD = (this.value - nextM * nextM) / triplet.d;
        int nextA = (a0 + nextM) / nextD;
        return new Triplet(nextA, nextM, nextD);
    }

    /*In the form of (S^0.5 + m)/n*/
    private static class Triplet {
        int a;
        int m;
        int d;

        public Triplet(int a, int m, int d) {
            this.a = a;
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
                    a == triplet.a;
        }

        @Override
        public int hashCode() {
            return Objects.hash(m, d, a);
        }
    }
}
