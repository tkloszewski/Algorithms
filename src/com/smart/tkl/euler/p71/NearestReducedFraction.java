package com.smart.tkl.euler.p71;

import com.smart.tkl.lib.utils.Fraction;
import java.math.BigInteger;

public class NearestReducedFraction {

    private final long maxDenominator;
    private final Fraction limitFraction;

    public NearestReducedFraction(long maxDenominator, Fraction limitFraction) {
        this.maxDenominator = maxDenominator;
        this.limitFraction = limitFraction;
    }

    public static void main(String[] args) {
        NearestReducedFraction orderedFraction = new NearestReducedFraction(8L, new Fraction(4, 5));
        Fraction closestLeftFraction = orderedFraction.findNearestLeft();
        Fraction left = orderedFraction.findLeft();
        Fraction left2 = orderedFraction.findBySternBrocotTree();
        System.out.println("Closest left fraction: " + closestLeftFraction);
        System.out.println("Left: " + left);
        System.out.println("Left2: " + left2);
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

    public Fraction findLeft() {
        long bestNumerator = 0;
        long bestDenominator = 1;
        long minDenominator = 1;
        long currDenominator = maxDenominator;

        long a = limitFraction.getNumerator();
        long b = limitFraction.getDenominator();

        while (currDenominator >= minDenominator) {
            BigInteger v0 = BigInteger.valueOf(a).multiply(BigInteger.valueOf(currDenominator)).subtract(BigInteger.ONE);
            //long currNumerator = (a * currDenominator - 1) / b;
            long currNumerator = v0.divide(BigInteger.valueOf(b)).longValue();
            BigInteger v1 = BigInteger.valueOf(bestNumerator).multiply(BigInteger.valueOf(currDenominator));
            BigInteger v2 = BigInteger.valueOf(currNumerator).multiply(BigInteger.valueOf(bestDenominator));

            if(v1.compareTo(v2) < 0) {
                bestNumerator = currNumerator;
                bestDenominator = currDenominator;
                BigInteger v3 = BigInteger.valueOf(a).multiply(BigInteger.valueOf(currDenominator));
                BigInteger v4 = BigInteger.valueOf(b).multiply(BigInteger.valueOf(currNumerator));

                long delta = v3.subtract(v4).longValue();
                minDenominator = currDenominator / delta + 1;
            }
            currDenominator--;
        }

        return new Fraction(bestNumerator, bestDenominator);
    }

    public Fraction findBySternBrocotTree() {
        long a = limitFraction.getNumerator();
        long b = limitFraction.getDenominator();

        if(a == 1) {
           long n = (maxDenominator - 1) / b;
           return new Fraction(n, 1 + n * b, true);
        }
        else {
            long leftNumerator, leftDenominator, rightNumerator, rightDenominator;

            if(a + 1 == b) {
               leftNumerator = a - 1;
               leftDenominator = b - 1;
               rightNumerator = 1;
               rightDenominator = 1;
            }
            else if(2 * a < b) {
               leftDenominator = b / a + 1;
               rightDenominator = leftDenominator - 1;
               leftNumerator = rightNumerator = 1;
            }
            else {
               long n = b / (b - a);
               leftNumerator = n - 1;
               leftDenominator = n;
               rightNumerator = n;
               rightDenominator = n + 1;
            }

            long middleNumerator = leftNumerator + rightNumerator;
            long middleDenominator = leftDenominator + rightDenominator;

            while (middleNumerator != a || middleDenominator != b) {
                if(a * middleDenominator < b * middleNumerator) {
                   rightNumerator =  middleNumerator;
                   rightDenominator = middleDenominator;
                }
                else {
                   leftNumerator = middleNumerator;
                   leftDenominator = middleDenominator;
                }
                middleNumerator = leftNumerator + rightNumerator;
                middleDenominator = leftDenominator + rightDenominator;
            }

            long n = (maxDenominator - leftDenominator) / b;

            return new Fraction(leftNumerator + n * a, leftDenominator + n * b, true);
        }
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
