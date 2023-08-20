package com.smart.tkl.lib.utils;

public class Congruence {

    private final long a, m;

    public Congruence(long a, long m) {
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
