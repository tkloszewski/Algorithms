package com.smart.tkl.euler.p99.hk;

public class Exponent implements Comparable<Exponent>  {
    private final int base;
    private final int exponent;

    public Exponent(int base, int exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public int compareTo(Exponent o) {
        double val1 = this.exponent * Math.log10(base);
        double val2 = o.exponent * Math.log10(o.base);
        return Double.compare(val1, val2);
    }

    public int getBase() {
        return base;
    }

    public int getExponent() {
        return exponent;
    }
}
