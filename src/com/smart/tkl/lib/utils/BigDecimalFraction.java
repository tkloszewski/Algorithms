package com.smart.tkl.lib.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalFraction {

    private static final MathContext mc = new MathContext(200, RoundingMode.HALF_DOWN);
    private final BigDecimal p;
    private final BigDecimal q;

    public BigDecimalFraction(BigDecimal p, BigDecimal q) {
        this.p = p;
        this.q = q;
    }

    public BigDecimal getNumerator() {
        return p;
    }

    public BigDecimal getDenominator() {
        return q;
    }

    @Override
    public String toString() {
        return "{p=" + p + ", q=" + q + "}";
    }

    public BigDecimal toValue() {
        return p.divide(q, mc);
    }

    public BigDecimal toValue(int precision) {
        return p.divide(q, new MathContext(precision, RoundingMode.HALF_DOWN));
    }
}
