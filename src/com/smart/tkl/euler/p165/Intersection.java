package com.smart.tkl.euler.p165;

import com.smart.tkl.lib.utils.Fraction;
import java.util.Objects;

public class Intersection {

    final Fraction x;
    final Fraction y;

    public Intersection(long a, long b, long c, long d) {
        this.x = new Fraction(Math.abs(a), Math.abs(b));
        this.y = new Fraction(Math.abs(c), Math.abs(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intersection that = (Intersection) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
