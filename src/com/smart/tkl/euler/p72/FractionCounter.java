package com.smart.tkl.euler.p72;

import com.smart.tkl.utils.MathUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FractionCounter {

    private final int maxDenominator;

    public FractionCounter(int maxDenominator) {
        this.maxDenominator = maxDenominator;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FractionCounter counter = new FractionCounter(1000000);
        BigInteger count = counter.count();
        long time2 = System.currentTimeMillis();
        System.out.println(count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public BigInteger count() {
        BigInteger result = BigInteger.ZERO;
        long[] totients = MathUtils.generateTotientsUpTo(maxDenominator);
        for(int i = 2; i < totients.length; i++) {
            long totient = totients[i];
            result = result.add(BigInteger.valueOf(totient));
        }
        return result;
    }
}
