package com.smart.tkl.euler.p142;

import java.util.Objects;

public class PerfectSquare {

    long x, y, z;

    public PerfectSquare(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfectSquare that = (PerfectSquare) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}
