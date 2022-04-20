package com.smart.tkl.euler.p99;

import java.math.BigDecimal;
import java.util.Comparator;

public class ExponentialComparator implements Comparator<Exponential> {
    
    @Override
    public int compare(Exponential e1, Exponential e2) {
        if(equal(e1, e2)) {
            return 0;
        }
        BigDecimal logBase1 = BigDecimal.valueOf(Math.log(e1.base));
        BigDecimal logBase2 = BigDecimal.valueOf(Math.log(e2.base));
        BigDecimal value1 = logBase1.multiply(BigDecimal.valueOf(e1.exponent));
        BigDecimal value2 = logBase2.multiply(BigDecimal.valueOf(e2.exponent)); 
        
        return value1.compareTo(value2);
    }
    
    private boolean equal(Exponential e1, Exponential e2) {
        int a = e1.base;
        int b = e1.exponent;
        int c = e2.base;
        int d = e2.exponent;
        
        while (true) {
            boolean oneOrZero1 = a == 1 || b == 0;
            boolean oneOrZero2 = c == 1 || d == 0;
            if(oneOrZero1 || oneOrZero2) {
                return oneOrZero1 && oneOrZero2;
            }
            if(a > c) {
                int temp = a;
                a = c;
                c = temp;
                temp = b;
                b = d;
                d = temp;
            }
            if(b < d) {
                return false;
            }
            int r = c % a;
            if(r != 0) {
                return false;
            }
            b -= d;
            c = c / a;
        }
    }
}
