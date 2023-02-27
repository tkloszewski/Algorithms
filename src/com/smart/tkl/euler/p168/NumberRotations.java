package com.smart.tkl.euler.p168;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NumberRotations {

    private final int limit;

    public NumberRotations(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        NumberRotations numberRotations = new NumberRotations(100);
        long sum = numberRotations.sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sum() {
        long sum = 0;

        sum += sumOfDivisorsMultipliedBy9();
        sum += sumOfDivisorsMultipliedBy7();
        sum += sumOfDivisorsMultipliedBy(List.of(19, 29, 49, 59, 79, 89), 9);
        sum += sumOfDivisorsMultipliedBy(List.of(13, 23), 3);

        sum = sum % 100000;

        return sum;
    }

    private long sumOfDivisorsMultipliedBy9() {
        long result = 0;

        int max = Math.min(this.limit, 4);
        int k = 2;
        long repUnit = 11;
        while (k <= max){
            long lastValue = 9 * repUnit;
            result += (((repUnit + lastValue) * 9) / 2) % 100000;
            repUnit = 10 * repUnit + 1;
            k++;
        }

        long baseSum = (((11111 + 99999) * 9) / 2) % 100000;
        long m = Math.max(this.limit - 4, 0);

        result += (m * baseSum) % 100000;

        return result;
    }

    private long sumOfDivisorsMultipliedBy7() {
        long result = 0;

        List<Integer> repUnitsDividedBy7 = checkDivisibility(7);
        List<Integer> repUnitsDividedBy49 = checkDivisibility(49);
        repUnitsDividedBy7.removeAll(repUnitsDividedBy49);

        BigInteger mod = BigInteger.TEN.pow(5);

        for(int k : repUnitsDividedBy7) {
            BigInteger repUnit = toRepUnit(k);
            BigInteger n = repUnit.divide(BigInteger.valueOf(7)).multiply(BigInteger.valueOf(9));
            if(n.remainder(BigInteger.TEN).equals(BigInteger.valueOf(7))) {
               result +=  n.remainder(mod).longValue();
            }
        }

        return result;
    }

    /*Equation: digitMultiplier * d * R(k) = multipliers[i] * n */
    private long sumOfDivisorsMultipliedBy(List<Integer> multipliers, int digitMultiplier) {
        long result = 0;
        BigInteger mod = BigInteger.TEN.pow(5);
        for(Integer primeMultiplier : multipliers) {
            List<Integer> repUnits = checkDivisibility(primeMultiplier);

            for(int k : repUnits) {
                BigInteger repUnit = toRepUnit(k);
                int startDigit = getStartDigit(repUnit, k, digitMultiplier, primeMultiplier);
                for(int digit = startDigit; digit <= 9; digit++) {
                    BigInteger divResult = toDivResult(repUnit, digitMultiplier, digit, primeMultiplier);
                    if(divResult.remainder(BigInteger.TEN).equals(BigInteger.valueOf(digit))) {
                        result += divResult.remainder(mod).longValue();
                    }
                }
            }
        }
        return result;
    }

    private List<Integer> checkDivisibility(int number) {
        List<Integer> repUnits = new ArrayList<>();

        int k = 2;
        long n = 1;
        while (k <= this.limit) {
            n = (10 * n + 1) % number;
            if(n == 0) {
               repUnits.add(k);
            }
            k++;
        }

        return repUnits;
    }

    private static int getStartDigit(BigInteger repUnit, int k, int digitMultiplier, int divisor) {
        BigInteger pow10 = BigInteger.valueOf(10).pow(k - 1);
        BigInteger numerator = pow10.multiply(BigInteger.valueOf(divisor));
        BigInteger denominator = repUnit.multiply(BigInteger.valueOf(digitMultiplier));

        int result = numerator.divide(denominator).intValue();

        if(!numerator.remainder(denominator).equals(BigInteger.ZERO)) {
           result++;
        }

        return result;
    }

    private static BigInteger toDivResult(BigInteger repUnit, int digitMultiplier, int digit, int divisor) {
        return repUnit.multiply(BigInteger.valueOf(digitMultiplier))
                .multiply(BigInteger.valueOf(digit))
                .divide(BigInteger.valueOf(divisor));
    }

    private static BigInteger toRepUnit(int k) {
        return BigInteger.valueOf(10).pow(k).subtract(BigInteger.ONE).divide(BigInteger.valueOf(9));
    }

}
