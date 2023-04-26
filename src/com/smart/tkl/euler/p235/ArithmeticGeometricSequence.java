package com.smart.tkl.euler.p235;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ArithmeticGeometricSequence {

    private final double target;
    private static final BigDecimal EPS = BigDecimal.ONE.divide(BigDecimal.TEN.pow(13), MathContext.DECIMAL128);

    public ArithmeticGeometricSequence(double target) {
        this.target = target;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        double r = new ArithmeticGeometricSequence(-600000000000.0).findR();
        long time2 = System.currentTimeMillis();
        System.out.println("r: " + BigDecimal.valueOf(r).setScale(12, RoundingMode.HALF_EVEN));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public double findR() {
       double eps = EPS.doubleValue();
       double rangeStart = 1;
       double rangeEnd = 2;
       double r;
       do {
           r = (rangeEnd + rangeStart) / 2;
           double sum = calcSumIterative(r, 5000);
           if(sum < target) {
              rangeEnd = r;
           }
           else {
               rangeStart = r;
           }
       } while (rangeEnd - rangeStart > eps);

       return r;
    }

    private static double calcSumIterative(double r, int n) {
        double sum = 0;
        double pow = 1;
        for(int k = 1; k <= n; k++) {
            double term = (900 - 3 * k) * pow;
            sum += term;
            pow *= r;
        }
        return sum;
    }

    private static double calcSum(double r, int n) {
        double pow = Math.pow(r, n);
        double denominator = 1 - r;
        double term1 = (897.0  - (897.0 - 3 * n) * pow) / denominator;
        double term2 = (-3 * r * (1 - pow)) / (denominator * denominator);
        return term1 + term2;
    }
}
