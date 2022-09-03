package com.smart.tkl.linear.polynomial;

import java.util.List;

public class NaturalPolynomial {

    private final List<Long> coefficients;

    public NaturalPolynomial(List<Long> coefficients) {
        assert coefficients != null && !coefficients.isEmpty();
        this.coefficients = coefficients;
    }

    public List<Long> getCoefficients() {
        return coefficients;
    }

    public long getValueFor(long x) {
        long value = 0;
        int currPower = 1;
        for(int i = 0; i < coefficients.size(); i++) {
            value += coefficients.get(i) * currPower;
            currPower *= x;
        }
        return value;
    }
}
