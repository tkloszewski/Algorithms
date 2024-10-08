package com.smart.tkl.lib.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public class Fraction implements Comparable<Fraction> {
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

    public Fraction toInverted() {
        return new Fraction(this.q, this.p, true);
    }

    public Fraction toMinusSign() {
        return new Fraction(- this.p, this.q, true);
    }

    public double toDouble() {
        return (double) this.p / this.q;
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.p).divide(new BigDecimal(this.q), MathContext.DECIMAL128);
    }

    public boolean isOne() {
        return p == q;
    }

    @Override
    public int compareTo(Fraction o) {
        return Double.compare(this.toDouble(), o.toDouble());
    }

    public static Fraction sum(Fraction a, Fraction b) {
        return new Fraction(a.p * b.q + a.q * b.p, a.q * b.q);
    }

    public static Fraction multiply(Fraction a, Fraction b) {
        Fraction fraction1 = new Fraction(a.p, b.q);
        Fraction fraction2 = new Fraction(b.p, a.q);
        return new Fraction(fraction1.p * fraction2.p, fraction1.q * fraction2.q, true);
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
