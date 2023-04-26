package com.smart.tkl.lib.utils;

import java.util.LinkedList;
import java.util.List;

public class PeriodicFraction {

    private final int value;
    private final int base;

    private final List<Integer> sequence = new LinkedList<>();

    public PeriodicFraction(int value, int base) {
        this.value = value;
        this.base = base;
    }

    public void addToSequence(int a) {
        this.sequence.add(a);
    }

    public int getPeriodSize() {
        return sequence.size();
    }

    public int getValue() {
        return value;
    }

    public int getBase() {
        return base;
    }

    public List<Integer> getSequence() {
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
