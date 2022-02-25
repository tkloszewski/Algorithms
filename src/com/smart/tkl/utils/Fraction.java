package com.smart.tkl.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class Fraction {
    private final long p;
    private final long q;

    public Fraction(long p, long q) {
        long gcd = MathUtils.GCD(p, q);
        this.p = p/gcd;
        this.q = q/gcd;
    }

    public Fraction(long p, long q, boolean reducedFraction) {
        long gcd = reducedFraction ? 1 : MathUtils.GCD(p, q);
        this.p = p/gcd;
        this.q = q/gcd;
    }

    public long getNumerator() {
        return p;
    }

    public long getDenominator() {
        return q;
    }

    @Override
    public String toString() {
        return "{p=" + p + ", q=" + q + "}";
    }

    public BigDecimal toValue() {
        return new BigDecimal(p).divide(new BigDecimal(q), MathContext.DECIMAL128);
    }
}
