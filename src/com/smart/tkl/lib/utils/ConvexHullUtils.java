package com.smart.tkl.lib.utils;

import static com.smart.tkl.lib.utils.ContinuedFractionUtils.toConvergentList;

import java.util.ArrayList;
import java.util.List;

public class ConvexHullUtils {

    public static void main(String[] args) {
        System.out.println(toConvexHullUnderTheLine(4, 7, 19));
        System.out.println("Convex hull: Cy - Ax <= B: " + toConvexHullUnderTheLine(4, 7, 0, 19));
        System.out.println("Sum floor: " + sumFloor(4, 7, 19));
        System.out.println("Sum floor direct: " + sumFloorDirect(4, 7, 19));
    }

    public static long sumFloorDirect(long p, long q, long limit) {
        long result = 0;
        for(int i = 1; i <= limit; i++) {
            result += (i * p) / q ;
        }
        return result;
    }

    public static long sumFloor(long p, long q, long limit) {
        long result = 0;
        long N = limit + 1;
        ConvexHull convexHull = toConvexHullUnderTheLine(p, q, N);
        List<LatticePoint> latticePoints = convexHull.latticePoints;
        for(int i = 1; i < latticePoints.size(); i++) {
            LatticePoint p1 = latticePoints.get(i - 1);
            LatticePoint p2 = latticePoints.get(i);
            result += picks(p1.y, p2.y, p2.x - p1.x, p2.k);
        }
        return result - N;
    }

    public static ConvexHull toConvexHullUnderTheLine(long p, long q, long limit) {
        List<LongPoint> result = new ArrayList<>();
        result.add(new LongPoint(0, 0));

        List<Fraction> convergents = toConvergentList(p, q);

        List<LatticePoint> workingList = new ArrayList<>();
        long t = limit/ q;
        workingList.add(new LatticePoint(0 ,0 , 1));
        workingList.add(new LatticePoint(t * q, t * p, (int)t));

        for(int i = 1; i <= t; i++) {
            result.add(new LongPoint(i * q, i * p));
        }

        for(int i = convergents.size() - 1; i >= 0; i--) {
            if(i % 2 == 1) {
                LatticePoint point = workingList.get(workingList.size() - 1);
                while (point.x + convergents.get(i - 1).getDenominator() <= limit) {
                    t = (limit - point.x - convergents.get(i - 1).getDenominator()) / convergents.get(i).getDenominator();

                    long x = point.x;
                    long y = point.y;
                    long dx = convergents.get(i - 1).getDenominator() + t * convergents.get(i).getDenominator();
                    long dy = convergents.get(i - 1).getNumerator() + t * convergents.get(i).getNumerator();
                    int maxK = (int)((limit - point.x) / dx);

                    point = new LatticePoint(x + dx * maxK, y + dy * maxK, maxK);
                    workingList.add(point);

                    for(int k = 1; k <= maxK; k++) {
                        result.add(new LongPoint(x + dx * k, y + dy * k));
                    }
                }
            }
        }

        return new ConvexHull(result, workingList);
    }

    /*Number of lattice points within a vertical right trapezoid*/
    private static long picks(long y1, long y2, long dx, int k) {
        long b = y1 + y2 + dx + k;
        long s = (dx * (y1 + y2)) / 2;
        long inner = (2 * s - b + 2) / 2;
        return inner + b  - (y2 + 1);
    }

    public static ConvexHull toConvexHullUnderTheLine(long p, long q, long b, long limit) {
        List<LongPoint> result = new ArrayList<>();
        result.add(new LongPoint(0, b/ q));

        List<Fraction> convergents = toConvergentList(p, q);

        List<LatticePoint> workingList = new ArrayList<>();
        workingList.add(new LatticePoint(0, b / q, 1));

        for(int i = 1; i < convergents.size() - 1; i++) {
            if(i % 2 == 0) {
                LatticePoint point = workingList.get(workingList.size() - 1);
                Fraction convergent0 = convergents.get(i - 1);
                Fraction convergent1 = convergents.get(i);
                Fraction convergent2 = convergents.get(i + 1);
                while (diff(p, q, point.x + convergent2.getDenominator(), point.y + convergent2.getNumerator()) <= b) {
                    long diff = diff(p, q, point.x + convergent0.getDenominator(), point.y + convergent0.getNumerator());
                    long t = (b - diff) / Math.abs(diff(p, q, convergent1.getDenominator(), convergent1.getNumerator()));
                    long dq = convergent0.getDenominator() + t * convergent1.getDenominator();
                    long dp = convergent0.getNumerator()  + t * convergent1.getNumerator();

                    if(dq < 0 || point.x + dq  > limit) {
                        break;
                    }

                    insert(p, q, b, limit, dq, dp, workingList, result);
                    point = workingList.get(workingList.size() - 1);
                }
            }
        }
        Fraction lastConvergent = convergents.get(convergents.size() - 1);
        insert(p, q, b, limit, lastConvergent.getDenominator(), lastConvergent.getNumerator(), workingList, result);

        for(int i = convergents.size() - 1; i > 0; i--) {
            if(i % 2 == 1) {
                LatticePoint point = workingList.get(workingList.size() - 1);
                Fraction convergent1 = convergents.get(i - 1);
                Fraction convergent2 = convergents.get(i);
                while (point.x + convergent1.getDenominator() <= limit) {
                    long t = (limit - point.x - convergent1.getDenominator()) / convergent2.getDenominator();
                    long dq = convergent1.getDenominator() + t * convergent2.getDenominator();
                    long dp = convergent1.getNumerator() + t * convergent2.getNumerator();
                    insert(p, q, b, limit, dq, dp, workingList, result);
                    point = workingList.get(workingList.size() - 1);
                }
            }
        }

        return new ConvexHull(result, workingList);
    }

    private static void insert(long p, long q, long b, long limit, long dq, long dp, List<LatticePoint> workingList, List<LongPoint> result) {
        LatticePoint lastPoint = workingList.get(workingList.size() - 1);
        int maxK = (int)((limit - lastPoint.x) / dq);
        long diff = diff(p, q, dq , dp);
        if(diff > 0) {
            maxK = (int)(Math.min(maxK, (b - diff(p, q, lastPoint.x, lastPoint.y)) / diff));
        }
        if (maxK > 0) {
            workingList.add(new LatticePoint(lastPoint.x + maxK * dq, lastPoint.y + maxK * dp, maxK));
            for(int k = 1; k <= maxK; k++) {
                result.add(new LongPoint(lastPoint.x + k * dq, lastPoint.y + k * dp));
            }
        }
    }

    private static long diff(long p, long q, long x, long y) {
        return q * y - p * x;
    }

    private static class ConvexHull {
        List<LongPoint> allPoints;
        List<LatticePoint> latticePoints;

        public ConvexHull(List<LongPoint> allPoints, List<LatticePoint> latticePoints) {
            this.allPoints = allPoints;
            this.latticePoints = latticePoints;
        }

        @Override
        public String toString() {
            return "ConvexHull{" +
                    "allPoints=" + allPoints +
                    ", latticePoints=" + latticePoints +
                    '}';
        }
    }

    public static class LatticePoint {
        long x;
        long y;
        int k;

        public LatticePoint(long x, long y, int k) {
            this.x = x;
            this.y = y;
            this.k = k;
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    ", k=" + k +
                    '}';
        }
    }
}
