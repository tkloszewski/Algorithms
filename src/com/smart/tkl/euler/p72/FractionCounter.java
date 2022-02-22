package com.smart.tkl.euler.p72;

import com.smart.tkl.utils.MathUtils;

import java.math.BigDecimal;

public class FractionCounter {

    private final int maxDenominator;

    public FractionCounter(int maxDenominator) {
        this.maxDenominator = maxDenominator;
    }

    public static void main(String[] args) {
        FractionCounter counter = new FractionCounter(1000000);
        System.out.println(counter.count());
    }

    public BigDecimal count() {
        BigDecimal result = BigDecimal.ZERO;
        long[] totients = MathUtils.generateTotientsUpTo(maxDenominator);
        for(int i = 2; i < totients.length; i++) {
            long totient = totients[i];
            result = result.add(BigDecimal.valueOf(totient));
        }
        return result;
    }
}
