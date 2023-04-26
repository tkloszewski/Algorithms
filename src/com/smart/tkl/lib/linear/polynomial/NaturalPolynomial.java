package com.smart.tkl.lib.linear.polynomial;

import java.util.List;

public class NaturalPolynomial {

    private final List<Long> coefficients;
    private final int degree;

    public NaturalPolynomial(List<Long> coefficients) {
        assert coefficients != null && !coefficients.isEmpty();
        this.coefficients = coefficients;
        this.degree = coefficients.size();
    }

    public List<Long> getCoefficients() {
        return coefficients;
    }

    public int getDegree() {
        return degree;
    }

    public long getValueFor(long x) {
        long value = 0;
        long currPower = 1;
        for (Long coefficient : coefficients) {
            value += coefficient * currPower;
            currPower *= x;
        }
        return value;
    }
}
