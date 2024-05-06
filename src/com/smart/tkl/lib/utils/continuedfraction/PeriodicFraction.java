package com.smart.tkl.lib.utils.continuedfraction;

import java.util.List;

public class PeriodicFraction {

    private final long value;
    private final long base;
    private final int periodIndex;
    private final boolean periodic;

    private final List<Long> coefficients;

    public PeriodicFraction(long value, long base, int repetitionIndex, List<Long> coefficients) {
        this(value, base, repetitionIndex, true, coefficients);
    }

    public PeriodicFraction(long value, long base, int repetitionIndex, boolean periodic, List<Long> coefficients) {
        this.value = value;
        this.base = base;
        this.periodIndex = repetitionIndex;
        this.periodic = periodic;
        this.coefficients = coefficients;
    }

    public static PeriodicFraction ofSimpleContinueFraction(long value, long base, List<Long> coefficients) {
        return new PeriodicFraction(value, base, -1, false, coefficients);
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

    @Override
    public String toString() {
        return "PeriodicFraction{" +
                "value=" + value +
                ", base=" + base +
                ", periodIndex=" + periodIndex +
                ", periodic=" + periodic +
                ", coefficients=" + coefficients +
                '}';
    }
}
