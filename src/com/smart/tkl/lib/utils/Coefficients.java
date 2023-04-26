package com.smart.tkl.lib.utils;

public class Coefficients {

    private final long gcd;
    private final long x;
    private final long y;

    public Coefficients(long gcd, long x, long y) {
        this.gcd = gcd;
        this.x = x;
        this.y = y;
    }

    public long getGcd() {
        return gcd;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coefficients{" +
                "gcd=" + gcd +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
