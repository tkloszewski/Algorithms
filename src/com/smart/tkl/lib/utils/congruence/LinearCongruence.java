package com.smart.tkl.lib.utils.congruence;

public class LinearCongruence {

    private final long a, m;

    public LinearCongruence(long a, long m) {
        this.a = a % m;
        this.m = m;
    }

    public long getA() {
        return this.a;
    }

    public long getM() {
        return this.m;
    }
}
