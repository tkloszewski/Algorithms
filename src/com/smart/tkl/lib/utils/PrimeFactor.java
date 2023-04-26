package com.smart.tkl.lib.utils;

public class PrimeFactor {

    private final int factor;
    private int pow;

    public PrimeFactor(int factor, int pow) {
        this.factor = factor;
        this.pow = pow;
    }

    public int getFactor() {
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
