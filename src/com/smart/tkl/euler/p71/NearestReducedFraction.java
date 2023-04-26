package com.smart.tkl.euler.p71;

import com.smart.tkl.lib.utils.Fraction;

public class NearestReducedFraction {

    private final int maxDenominator;
    private final Fraction limitFraction;

    public NearestReducedFraction(int maxDenominator, Fraction limitFraction) {
        this.maxDenominator = maxDenominator;
        this.limitFraction = limitFraction;
    }

    public static void main(String[] args) {
        NearestReducedFraction orderedFraction = new NearestReducedFraction(1000000, new Fraction(3, 7));
        Fraction closestLeftFraction = orderedFraction.findNearestLeft();
        Fraction closestRightFraction = orderedFraction.findNearestRight();
        System.out.println("Closest left fraction: " + closestLeftFraction);
        System.out.println("Closest right fraction: " + closestRightFraction);
    }

    public Fraction findNearestLeft() {
        long prevNumerator = (limitFraction.getNumerator() - 1) / limitFraction.getDenominator();
        long prevDenominator = 1;

        Fraction result = new Fraction(prevNumerator, prevDenominator);

        long a = limitFraction.getNumerator();
        long b = limitFraction.getDenominator();

        for(int currDenominator = 2; currDenominator <= maxDenominator; currDenominator++) {
            long p = (a * currDenominator - 1) / b;
            if(p * prevDenominator > prevNumerator * currDenominator) {
                prevNumerator = p;
                prevDenominator = currDenominator;
                result = new Fraction(p, currDenominator);
            }
        }

        return result;
    }

    public Fraction findNearestRight() {
        long a = limitFraction.getNumerator();
        long b = limitFraction.getDenominator();

        long prevNumerator = (long)Math.ceil((double)(a + 1) / b);
        long prevDenominator = 1;

        Fraction result = new Fraction(prevNumerator, prevDenominator);

        for(int currDenominator = 2; currDenominator <= maxDenominator; currDenominator++) {
            long currNumerator = (long)Math.ceil((double)(a * currDenominator + 1) / b);
            if(currNumerator * prevDenominator < prevNumerator * currDenominator) {
                prevNumerator = currNumerator;
                prevDenominator = currDenominator;
                result = new Fraction(currNumerator, currDenominator);
            }
        }

        return result;
    }


}
