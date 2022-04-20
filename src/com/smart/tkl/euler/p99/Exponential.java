package com.smart.tkl.euler.p99;

public class Exponential {
    
    final int base;
    final int exponent;
    final int lineNumber;
    
    public Exponential(int base, int exponent, int lineNumber) {
        this.base = base;
        this.exponent = exponent;
        this.lineNumber = lineNumber;
    }
    
    @Override
    public String toString() {
        return "Exponential{" +
                "base=" + base +
                ", exponent=" + exponent +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
