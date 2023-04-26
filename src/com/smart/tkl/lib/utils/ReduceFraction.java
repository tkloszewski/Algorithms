package com.smart.tkl.lib.utils;

public class ReduceFraction {
    private final long p;
    private final long q;

    public ReduceFraction(long p, long q) {
        this.p = p;
        this.q = q;
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
}

