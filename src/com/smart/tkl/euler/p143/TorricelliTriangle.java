package com.smart.tkl.euler.p143;

import java.util.Objects;

public class TorricelliTriangle implements Comparable<TorricelliTriangle> {

    final long a;
    final long b;
    final long c;
    final long p;
    final long q;
    final long r;
    final long distance;

    public TorricelliTriangle(long a, long b, long c, long p, long q, long r, long distance) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.p = p;
        this.q = q;
        this.r = r;
        this.distance = distance;
    }

    @Override
    public int compareTo(TorricelliTriangle other) {
        return Long.compare(this.distance, other.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TorricelliTriangle that = (TorricelliTriangle) o;
        return a == that.a &&
                b == that.b &&
                c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a * b * c);
    }

    @Override
    public String toString() {
        return "{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", p=" + p +
                ", q=" + q +
                ", r=" + r +
                ", distance=" + distance +
                '}';
    }

    public String asString() {
        return a + " " + b + " " + c;
    }
}
