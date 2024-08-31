package com.smart.tkl.euler.p144;

public class LaserReflection {

    private static final double GAP_X = 0.01;

    public static void main(String[] args) {
        System.out.println("Reflections count for (4, 1, 100, 0.0, 10.1, 1.4, -9.6) " + countReflections(4, 1, 100, 0.0, 10.1, 1.4, -9.6));
        System.out.println("Reflections count for (4, 1, 100, 0.0, 10.1, 0.0, -9.6) " + countReflections(4, 1, 100, 0.0, 10.1, 0.0, -10.0));
        System.out.println("Reflections count for (75, 77, 720, 0.0, 3.06, -2.70, -1.50) " + countReflections(75, 77, 720, 0.0, 3.06, -2.70, -1.50));
    }

    public static int countReflections(int a, int b, int c, double x0, double y0, double x1, double y1) {
        if(x0 == 0 && x1 == 0) {
            return 1;
        }

        int reflectionsCount = 0;

        double minY = Math.sqrt((c - a * GAP_X * GAP_X) / b);
        double m1 = (y1 - y0) / (x1 - x0);
        Point p = new Point(x1, y1);

        while (p.x < -GAP_X || p.x > GAP_X || p.y < minY) {
            reflectionsCount++;
            Intersection intersection = getIntersection(a, b, p, m1);
            p = intersection.p;
            m1 = intersection.slope;
        }

        return reflectionsCount;
    }

    private static Intersection getIntersection(int a, int b, Point p, double m1) {
        double m0 = - (p.x * a) / (p.y * b);
        double m2 = getNextSlope(m0, m1);
        double bk = p.y - m2 * p.x;

        double solutionX = - (2 * b * m2 * bk) / (a + b * m2 * m2) - p.x;
        double solutionY = m2 * solutionX + bk;

        return new Intersection(new Point(solutionX, solutionY), m2);
    }

    private static double getNextSlope(double m0, double m1) {
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
