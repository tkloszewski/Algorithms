package com.smart.tkl.lib.utils;

public class PrimeFactor {

    private final long factor;
    private int pow;

    public PrimeFactor(long factor, int pow) {
        this.factor = factor;
        this.pow = pow;
    }

    public long getFactor() {
        return factor;
    }

    public int getPow() {
        return pow;
    }

    public void incrPower() {
        this.pow++;
    }

    @Override
    public String toString() {
        return factor + "^" + pow;
    }
}
