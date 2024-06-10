package com.smart.tkl.euler.p119;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DigitalPowerSum2 {

    private final BigInteger limit;
    private final int base;

    public DigitalPowerSum2(BigInteger limit, int base) {
        this.limit = limit;
        this.base = base;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int base = 100;
        List<BigInteger> sequence = new DigitalPowerSum2(BigInteger.TEN.pow(100), base).resolveSequence();
        long time2 = System.currentTimeMillis();
        System.out.println(sequence);
        for(BigInteger n : sequence) {
            System.out.println(n.toString(base));
        }
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public String resolveSequenceAsString() {
        List<String> seq = resolveSequence().stream().map(BigInteger::toString).collect(Collectors.toList());
        return String.join(" ", seq.toArray(new String[]{}));
    }

    public List<BigInteger> resolveSequence() {
        List<BigInteger> result = new ArrayList<>();
        BigInteger B = BigInteger.valueOf(base);
        int maxDigitsCount = getExponent(B, limit) + 1;
        int maxDigitsSum = maxDigitsCount * (base - 1);
        if(!isAllHighestDigits(this.limit.subtract(BigInteger.ONE), B)) {
           maxDigitsSum = maxDigitsSum - base + 1;
        }
        for(int digitSum = 2; digitSum <= maxDigitsSum; digitSum++) {
            BigInteger digitSumBI = BigInteger.valueOf(digitSum);
            BigInteger pow = BigInteger.valueOf(digitSum);
            while (pow.compareTo(B) < 0) {
                pow = pow.multiply(digitSumBI);
            }
            while (pow.compareTo(limit) <= 0) {
                int sumOfDigits = sumOfDigits(pow);
                if(digitSum == sumOfDigits) {
                    result.add(pow);
                }
                pow = pow.multiply(digitSumBI);
            }
        }

        Collections.sort(result);
        return result;
    }

    private int sumOfDigits(BigInteger n) {
        int sum = 0;
        BigInteger B = BigInteger.valueOf(base);
        while (n.compareTo(BigInteger.ZERO) > 0) {
            int digit = n.mod(B).intValue();
            sum += digit;
            n = n.divide(B);
        }
        return sum;
    }

    private boolean isAllHighestDigits(BigInteger n, BigInteger base) {
        int b = base.intValue();
        while (n.compareTo(BigInteger.ZERO) > 0) {
            int digit = n.mod(base).intValue();
            if(digit != b - 1) {
               return false;
            }
            n = n.divide(base);
        }
        return true;
    }

    private int getExponent(BigInteger n, BigInteger bound) {
        int exp = 1;
        BigInteger m = n;
        while (n.multiply(m).compareTo(bound) <= 0) {
            n = n.multiply(m);
            exp++;
        }
        return exp;
    }
}
