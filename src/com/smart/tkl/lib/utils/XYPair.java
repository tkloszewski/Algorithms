package com.smart.tkl.lib.utils;

import java.math.BigInteger;
import java.util.Objects;

public class XYPair {

    final BigInteger x, y;

    public XYPair(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        XYPair xyPair = (XYPair) o;
        return Objects.equals(x, xyPair.x) && Objects.equals(y, xyPair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "XYPair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
