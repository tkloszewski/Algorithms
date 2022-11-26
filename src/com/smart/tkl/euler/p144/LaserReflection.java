package com.smart.tkl.euler.p144;

public class LaserReflection {

    public static void main(String[] args) {
        int reflectionsCount = new LaserReflection().countReflections();
        System.out.println("Reflections count: " + reflectionsCount);
    }

    public int countReflections() {
        int reflectionsCount = 0;

        double minX = -0.01;
        double maxX = 0.01;
        double minY = Math.sqrt(100 - 4 * maxX * maxX);

        double m1 = (-9.6 - 10.1) / 1.4;

        Point p = new Point(1.4, -9.6);
        while (p.x <= minX || p.x >= maxX || p.y < minY) {
            reflectionsCount++;
            Intersection intersection = getIntersection(p, m1);
            p = intersection.p;
            m1 = intersection.slope;
        }

        return reflectionsCount;
    }

    private Intersection getIntersection(Point p, double m1) {
        double m0 = -4 * p.x / p.y;
        double a = getNextSlope(m0, m1);
        double b = p.y - p.x * a;

        double delta = 25 * a * a - b * b + 100;
        double deltaSqrt = Math.sqrt(delta);
        double denominator = a * a + 4;

        double x1 = (-a * b - 2 * deltaSqrt) / denominator;
        double x2 = (-a * b + 2 * deltaSqrt) / denominator;

        double solutionX = Math.abs(x1 - p.x) > Math.abs(x2 - p.x) ? x1 : x2;
        double solutionY = a * solutionX + b;

        return new Intersection(new Point(solutionX, solutionY), a);
    }

    private double getNextSlope(double m0, double m1) {
        return (2 * m0 + m1 * (m0 * m0 - 1)) / (1 + 2 * m0 * m1 - m0 * m0);
    }

    private static class Intersection {
        Point p;
        double slope;

        public Intersection(Point p, double slope) {
            this.p = p;
            this.slope = slope;
        }

        @Override
        public String toString() {
            return "Intersection{" +
                    "p=" + p +
                    ", slope=" + slope +
                    '}';
        }
    }

    private static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
