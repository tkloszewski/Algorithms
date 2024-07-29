package com.smart.tkl.lib.utils;

import java.util.ArrayList;
import java.util.List;

public class Radical implements Comparable<Radical> {
    public long n;
    public long product;
    public List<Long> factors = new ArrayList<>(10);

    public Radical(long n, long product) {
        this.n = n;
        this.product = product;
    }

    public void addFactor(long factor) {
        factors.add(factor);
    }

    public boolean isPureRadical() {
        return n == product;
    }

    @Override
    public int compareTo(Radical o) {
        int compareResult = Long.compare(this.product, o.product);
        if(compareResult == 0) {
            compareResult = Long.compare(this.n, o.n);
        }
        return compareResult;
    }

    @Override
    public String toString() {
        return "Radical{" +
                "n=" + n +
                ", product=" + product +
                ", factors=" + factors +
                '}';
    }
}


