package com.smart.tkl.lib.utils;

import java.util.LinkedList;
import java.util.List;

public class PeriodicFraction {

    private final long value;
    private final long base;

    private final List<Long> sequence = new LinkedList<>();

    public PeriodicFraction(long value, long base) {
        this.value = value;
        this.base = base;
    }

    public void addToSequence(long a) {
        this.sequence.add(a);
    }

    public int getPeriodSize() {
        return sequence.size();
    }

    public long getValue() {
        return value;
    }

    public long getBase() {
        return base;
    }

    public List<Long> getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return "PeriodicFraction{" +
                "value=" + value +
                ", base=" + base +
                ", sequence=" + sequence +
                '}';
    }
}
