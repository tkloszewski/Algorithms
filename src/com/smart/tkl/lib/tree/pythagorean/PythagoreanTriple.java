package com.smart.tkl.lib.tree.pythagorean;

public class PythagoreanTriple {

    final int a, b, c;
    final int perimeter;

    public PythagoreanTriple(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.perimeter = a + b + c;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
