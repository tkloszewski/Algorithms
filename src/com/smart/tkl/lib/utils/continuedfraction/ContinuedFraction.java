package com.smart.tkl.lib.utils.continuedfraction;

import java.util.List;

public class ContinuedFraction {

    private final long value;
    private final long base;
    private final int periodIndex;
    private final boolean periodic;

    private final List<Long> coefficients;

    public ContinuedFraction(long value, long base, int repetitionIndex, List<Long> coefficients) {
        this(value, base, repetitionIndex, true, coefficients);
    }

    public ContinuedFraction(long value, long base, int repetitionIndex, boolean periodic, List<Long> coefficients) {
        this.value = value;
        this.base = base;
        this.periodIndex = repetitionIndex;
        this.periodic = periodic;
        this.coefficients = coefficients;
    }

    public static ContinuedFraction ofSimpleContinueFraction(long value, long base, List<Long> coefficients) {
        return new ContinuedFraction(value, base, -1, false, coefficients);
    }

    public int getPeriodSize() {
        return periodIndex >= 0 ? coefficients.size() - periodIndex : 0;
    }

    public long getValue() {
        return value;
    }

    public long getBase() {
        return base;
    }

    public int getPeriodIndex() {
        return periodIndex;
    }

    public boolean isPeriodic() {
        return periodic;
    }

    public List<Long> getCoefficients() {
        return coefficients;
    }

    public long getCoefficient(int index) {
        return coefficients.get(index);
    }

    @Override
    public String toString() {
        return "ContinuedFraction{" +
                "value=" + value +
                ", base=" + base +
                ", periodIndex=" + periodIndex +
                ", periodic=" + periodic +
                ", coefficients=" + coefficients +
                '}';
    }
}
