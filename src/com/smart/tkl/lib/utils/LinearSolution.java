package com.smart.tkl.lib.utils;

public class LinearSolution {

    private final Long x;
    private final Long y;
    private final Long gcd;

    public static LinearSolution emptySolution() {
        return new LinearSolution();
    }

    public static LinearSolution solution(long x, long y, long gcd) {
        return new LinearSolution(x, y, gcd);
    }

    private LinearSolution() {
        this.x = null;
        this.y = null;
        this.gcd = null;
    }

    private LinearSolution(Long x, Long y) {
        this.x = x;
        this.y = y;
        this.gcd = MathUtils.GCD(x, y);
    }

    private LinearSolution(Long x, Long y, Long gcd) {
        this.x = x;
        this.y = y;
        this.gcd = gcd;
    }

    public boolean hasSolutions() {
        return x != null && y != null;
    }

    public Long getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    public Long getGcd() {
        return gcd;
    }

    @Override
    public String toString() {
        return "LinearSolution{" +
                "x=" + x +
                ", y=" + y +
                ", gcd=" + gcd +
                '}';
    }
}
