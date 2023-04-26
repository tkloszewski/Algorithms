package com.smart.tkl.lib;

import java.util.concurrent.atomic.AtomicInteger;

public class NumberRange {

    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    private Object monitor = new Object();

    public void setLower(int i) {
// Warning -- unsafe check-then-act
        synchronized (monitor) {
            if (i > upper.get())
                throw new IllegalArgumentException(
                        "can’t set lower to " + i + " > upper");
            lower.set(i);
        }
    }
    public void setUpper(int i) {
// Warning -- unsafe check-then-act
        synchronized (monitor) {
            if (i < lower.get())
                throw new IllegalArgumentException(
                        "can’t set upper to " + i + " < lower");
            upper.set(i);
        }
        
    }

    public boolean isInRange(int i) {
        return (i >= lower.get() && i <= upper.get());
    }
}
