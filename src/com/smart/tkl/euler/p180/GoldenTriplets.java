package com.smart.tkl.euler.p180;

import com.smart.tkl.lib.utils.Fraction;
import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GoldenTriplets {

    private final int k;

    public GoldenTriplets(int k) {
        this.k = k;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int k = 35;
        GoldenTriplets goldenTriplets = new GoldenTriplets(k);
        BigInteger sum = goldenTriplets.sumReduced();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum reduced: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public BigInteger sumReduced() {
        List<Fraction> fractions = new ArrayList<>();
        Set<Fraction> fractionsSet = new HashSet<>();
        Map<Fraction, Fraction> squareFractions = new HashMap<>();

        for(int b = 2; b <= k; b++) {
            for(int a = 1; a < b; a++) {
                if(MathUtils.GCD(a, b) == 1) {
                   Fraction fraction = new Fraction(a, b, true);
                   fractions.add(fraction);
                   fractionsSet.add(fraction);
                   squareFractions.put(Fraction.multiply(fraction, fraction), fraction);
                }
            }
        }

        Set<Fraction> totalSumFractions = new HashSet<>();

        for(int i = 0; i < fractions.size(); i++) {
            Fraction fraction1 = fractions.get(i);
            Fraction inverted1 = fraction1.toInverted();
            Fraction squared1 = Fraction.multiply(fraction1, fraction1);
            Fraction squareInverted1 = squared1.toInverted();
            for(int j = i; j < fractions.size(); j++) {
                Fraction fraction2 = fractions.get(j);
                Fraction inverted2 = fraction2.toInverted();
                Fraction squared2 = Fraction.multiply(fraction2, fraction2);
                Fraction squareInverted2 = squared2.toInverted();

                Fraction sum = Fraction.sum(fraction1, fraction2);
                Fraction totalSumFraction = Fraction.sum(sum, sum);
                if(fractionsSet.contains(sum)) {
                   totalSumFractions.add(totalSumFraction);
                }

                Fraction sumInverted = Fraction.sum(inverted1, inverted2).toInverted();
                if(fractionsSet.contains(sumInverted)) {
                    Fraction totalSumInvertedFraction = Fraction.sum(sum, sumInverted);
                    totalSumFractions.add(totalSumInvertedFraction);
                }

                Fraction sumSquared = Fraction.sum(squared1, squared2);
                if(squareFractions.containsKey(sumSquared)) {
                    Fraction squareRootFraction = squareFractions.get(sumSquared);
                    Fraction totalSumSquared = Fraction.sum(sum, squareRootFraction);
                    totalSumFractions.add(totalSumSquared);
                }

                Fraction sumSquaredInverted = Fraction.sum(squareInverted1, squareInverted2).toInverted();
                if(squareFractions.containsKey(sumSquaredInverted)) {
                    Fraction squareRootFraction = squareFractions.get(sumSquaredInverted);
                    Fraction totalSumInvertedSquared = Fraction.sum(sum, squareRootFraction);
                    totalSumFractions.add(totalSumInvertedSquared);
                }
            }
        }

        BigIntegerFraction totalSum = new BigIntegerFraction(0, 1, true);
        for(Fraction reducedFraction : totalSumFractions) {
            totalSum = totalSum.sum(new BigIntegerFraction(reducedFraction));
        }


        return totalSum.p.add(totalSum.q);
    }

    private static class BigIntegerFraction {
        private BigInteger p;
        private BigInteger q;

        public BigIntegerFraction(Fraction reducedFraction) {
             this(reducedFraction.getNumerator(), reducedFraction.getDenominator(), true);
        }

        public BigIntegerFraction(long p, long q, boolean reduced) {
            this(BigInteger.valueOf(p), BigInteger.valueOf(q), reduced);
        }

        public BigIntegerFraction(BigInteger p, BigInteger q, boolean reduced) {
            this.p = p;
            this.q = q;
            BigInteger gcd = reduced ? BigInteger.ONE : gcd(this.p, this.q);
            if(!gcd.equals(BigInteger.ONE)) {
                this.p = this.p.divide(gcd);
                this.q = this.q.divide(gcd);
            }
        }

        public BigIntegerFraction sum(BigIntegerFraction other) {
            BigInteger a = this.p.multiply(other.q).add(this.q.multiply(other.p));
            BigInteger b = this.q.multiply(other.q);
            return new BigIntegerFraction(a, b, false);
        }

        @Override
        public String toString() {
            return "BigIntegerFraction{" +
                    "p=" + p +
                    ", q=" + q +
                    '}';
        }
    }


    private static BigInteger gcd(BigInteger a, BigInteger b) {
        if(BigInteger.ZERO.equals(b)) {
           return a;
        }
        return gcd(b, a.mod(b));
    }
}
