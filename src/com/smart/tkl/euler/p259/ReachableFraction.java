package com.smart.tkl.euler.p259;

public class ReachableFraction implements Comparable<ReachableFraction> {

    long denominator;
    long numerator;

    public ReachableFraction(long num) {
        this(num, 1);
    }

    public long toIntegerValue() {
        if(this.numerator % this.denominator == 0) {
           return this.numerator / this.denominator;
        }
        return 0;
    }

    public ReachableFraction(long numerator, long denominator) {
        int sign = 1;
        if(numerator < 0 && denominator < 0) {
           sign = -1;
        }
        this.numerator = numerator * sign;
        this.denominator = denominator * sign;

        if(this.denominator < 0) {
           this.numerator = -this.numerator;
           this.denominator = -this.denominator;
        }
    }

    public boolean isGreaterThanZero() {
        return this.numerator * this.denominator > 0;
    }

    public ReachableFraction add(ReachableFraction b) {
       long newNumerator = this.numerator * b.denominator + this.denominator * b.numerator;
       long newDenominator = this.denominator * b.denominator;
       return new ReachableFraction(newNumerator, newDenominator);
    }

    public ReachableFraction subtract(ReachableFraction b) {
        long newNumerator = this.numerator * b.denominator - this.denominator * b.numerator;
        long newDenominator = this.denominator * b.denominator;
        return new ReachableFraction(newNumerator, newDenominator);
    }

    public ReachableFraction multiply(ReachableFraction b) {
        long newNumerator = this.numerator * b.numerator;
        long newDenominator = this.denominator * b.denominator;
        return new ReachableFraction(newNumerator, newDenominator);
    }

    public ReachableFraction divide(ReachableFraction b) {
        long newNumerator = this.numerator * b.denominator;
        long newDenominator = this.denominator * b.numerator;
        return new ReachableFraction(newNumerator, newDenominator);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ReachableFraction) {
           ReachableFraction other = (ReachableFraction)obj;
           return this.numerator * other.denominator == this.denominator * other.numerator;
        }
        return false;
    }

    @Override
    public int compareTo(ReachableFraction other) {
        long diff = this.numerator * other.denominator - this.denominator * other.numerator;
        //descending
        return -1 * Long.compare(diff, 0);
    }

    @Override
    public String toString() {
        return "{" +
                "denominator=" + denominator +
                ", numerator=" + numerator +
                '}';
    }
}
