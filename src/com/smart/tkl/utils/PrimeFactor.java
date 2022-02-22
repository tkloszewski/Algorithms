package com.smart.tkl.utils;

public class PrimeFactor {

    private final int factor;
    private final int pow;

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

    @Override
    public String toString() {
        return factor + "^" + pow;
    }
}
