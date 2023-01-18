package com.smart.tkl.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

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

    public static Fraction sum(Fraction a, Fraction b) {
        return new Fraction(a.p * b.q + a.q * b.p, a.q * b.q);
    }

    public static Fraction multiply(Fraction a, Fraction b) {
        return new Fraction(a.p * b.p, a.q * b.q);
    }

    public static Fraction geometricAvg(Fraction a, Fraction b) {
        return new Fraction( a.p * b.p, a.p * b.q + a.q * b.p);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return p == fraction.p && q == fraction.q;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p, q);
    }
}
