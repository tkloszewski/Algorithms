package com.smart.tkl.euler.p199;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class IterativeCirclePacking {

    private final int n;
    private final int iterations;

    public IterativeCirclePacking(int n, int iterations) {
        this.n = n;
        this.iterations = iterations;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        IterativeCirclePacking iterativeCirclePacking = new IterativeCirclePacking(3,10);
        double gapFraction = iterativeCirclePacking.calcGapFraction();
        long time2 = System.currentTimeMillis();
        System.out.println("Original fraction: " + gapFraction);
        System.out.println("Fraction: " + new DecimalFormat("#.########").format(gapFraction));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public double calcGapFraction() {
        double totalFraction = 0.0;

        double ringRadius = calcRingCircleRadius(n);
        double centerRadius = BigDecimal.valueOf(ringRadius).multiply(BigDecimal.valueOf(2.0))
                .negate().add(BigDecimal.ONE).doubleValue();

        double k0 = -1.0;  // outer circle curvature
        double k1 = toCurvature(ringRadius);
        double k2 = toCurvature(centerRadius);

        double area1 = toArea(k1);

        totalFraction += n * area1;
        totalFraction += toArea(k2);

        double innerCirclesArea = iterations > 1 ? calcArea(k1, k1, k2, iterations - 1) : 0.0;
        double outerCircleArea = calcArea(k1, k1, k0, iterations);

        totalFraction += innerCirclesArea * n;
        totalFraction += outerCircleArea * n;

        return 1 - totalFraction;
    }

    private double calcArea(double k1, double k2, double k3, int iterationsLeft) {
        double k4 = calcCurvature(k1, k2, k3);
        double result = toArea(k4);

        if(iterationsLeft > 1) {

           if(k1 == k2 && k2 == k3) {
              result += 3 * calcArea(k1, k2, k4, iterationsLeft - 1);
           }
           else if(k1 == k2) {
              result += 2 * calcArea(k1, k3, k4, iterationsLeft - 1);
              result += calcArea(k1, k2, k4, iterationsLeft - 1);
           }
           else if(k2 == k3) {
               result += 2 * calcArea(k1, k2, k4, iterationsLeft - 1);
               result += calcArea(k2, k3, k4, iterationsLeft - 1);
           }
           else {
               result += calcArea(k1, k2, k4, iterationsLeft - 1);
               result += calcArea(k1, k3, k4, iterationsLeft - 1);
               result += calcArea(k2, k3, k4, iterationsLeft - 1);
           }
        }

        return result;
    }

    private static double calcRingCircleRadius(int n) {
        double angle = 180.0 * (n - 2) / (2 * n);
        double cos = Math.cos(Math.toRadians(angle));
        return 1.0 / (1.0 + 1.0 / cos);
    }

    private static double calcCurvature(double k1, double k2, double k3) {
        return k1 + k2 + k3 + 2 * Math.sqrt(k1 * k2 + k1 * k3 + k2 * k3);
    }

    private static double toArea(double k) {
        return (1.0 / k) * (1.0 / k);
    }

    private static double toCurvature(double radius) {
        return 1 / radius;
    }
}
