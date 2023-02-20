package com.smart.tkl.euler.p163;

public class CrossHatchedTriangle {

    private static final double EPS = Math.pow(10, -9);
    private static final double TAN_30 = Math.sqrt(3) / 3;
    private static final double TAN_60 = Math.sqrt(3);
    private static final double BASE_HEIGHT = Math.sqrt(3) / 2;

    private final RegularLine baseLine2;
    private final RegularLine baseLine3;

    private final Line[] set1;
    private final Line[] set2;
    private final Line[] set3;
    private final Line[] set4;
    private final Line[] set5;
    private final Line[] set6;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        CrossHatchedTriangle crossHatchedTriangle = new CrossHatchedTriangle(36);
        long count = crossHatchedTriangle.countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public CrossHatchedTriangle(int n) {
        this.baseLine2 = new RegularLine(-TAN_60, TAN_60 * n);
        this.baseLine3 = new RegularLine(TAN_60, 0);
        this.set1 = createHorizontalLines(n, BASE_HEIGHT);
        this.set2 = createRegularLines(n, -TAN_60, TAN_60);
        this.set3 = createRegularLines(n, TAN_60, 0);
        this.set4 = createVerticalLines(2 * n -1);
        this.set5 = createRegularLines(2 * n - 1, TAN_30, (n - 1) * TAN_30);
        this.set6 = createRegularLines(2 * n - 1, -TAN_30, TAN_30);
    }

    public long countTriangles() {
        long result = 0;

        long baseTrianglesCount = countInnerTriangles(set1, set2, set3);
        long heightTrianglesCount = countInnerTriangles(set4, set5, set6);
        long obtuseTrianglesCount1 = 3 * countInnerTriangles(set1, set2, set6);
        long obtuseTrianglesCount2 = 3 * countInnerTriangles(set1, set5, set6);
        long rightTrianglesCount1 = 6 * countInnerTriangles(set1, set2, set4);
        long rightTrianglesCount2 = 6 * countInnerTriangles(set1, set4, set5);

        result += baseTrianglesCount;
        result += heightTrianglesCount;
        result += obtuseTrianglesCount1;
        result += obtuseTrianglesCount2;
        result += rightTrianglesCount1;
        result += rightTrianglesCount2;

        return result;
    }

    private long countInnerTriangles(Line[] set1, Line[] set2, Line[] set3) {
        long result = 0;
        for(Line line1 : set1) {
            for(Line line2 : set2) {
                for(Line line3 : set3) {
                    if(isInnerTriangle(line1, line2, line3)) {
                       result++;
                    }
                }
            }
        }
        return result;
    }

    private boolean isInnerTriangle(Line line1, Line line2, Line line3) {
        Point2D intersection1 = line1.intersection(line2);
        Point2D intersection2 = line1.intersection(line3);
        Point2D intersection3 = line2.intersection(line3);

        if(samePoint(intersection1, intersection2) || samePoint(intersection1, intersection3) ||
                samePoint(intersection2, intersection3)) {
           return false;
        }

        return insideTriangle(intersection1) && insideTriangle(intersection2) && insideTriangle(intersection3);
    }

    private boolean insideTriangle(Point2D p) {
        if(p.y < -EPS) {
           return false;
        }
        double value1 = baseLine2.valueOf(p.x) + EPS;
        double value2 = baseLine3.valueOf(p.x) + EPS;
        return p.y <= value1 && p.y <= value2;
    }

    private static boolean samePoint(Point2D p1, Point2D p2) {
        return Math.abs(p1.x - p2.x) <= EPS && Math.abs(p1.y - p2.y) <= EPS;
    }

    private static Line[] createRegularLines(int num, double a, double b) {
        Line[] lines = new Line[num];
        for(int offset = 0; offset < num; offset++) {
            lines[offset] = new RegularLine(a, b - a * offset);
        }
        return lines;
    }

    private static Line[] createHorizontalLines(int num, double b) {
        Line[] lines = new Line[num];
        for(int offset = 0; offset < num; offset++) {
            lines[offset] = new RegularLine(0, b * offset);
        }
        return lines;
    }

    private static Line[] createVerticalLines(int num) {
        Line[] lines = new Line[num];
        for(int offset = 0; offset < num; offset++) {
            lines[offset] = new VerticalLine(0.5 * (offset + 1));
        }
        return lines;
    }

    interface Line {

        Point2D intersection(Line line);

        double valueOf(double x);
    }

    static final class RegularLine implements Line {

        final double a;
        final double b;

        public RegularLine(double a, double b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public Point2D intersection(Line line) {
            if(line instanceof VerticalLine) {
                double x = ((VerticalLine)line).x;
                return new Point2D(x, valueOf(x));
            }
            RegularLine regularLine = (RegularLine)line;
            return intersection(regularLine.a, regularLine.b);
        }

        Point2D intersection(double c, double d) {
            double x = (d - b) / (a - c);
            double y = a * x + b;
            return new Point2D(x, y);
        }

        @Override
        public double valueOf(double x) {
            return a * x + b;
        }
    }

    static final class VerticalLine implements Line {

        final double x;

        public VerticalLine(double x) {
            this.x = x;
        }

        @Override
        public Point2D intersection(Line line) {
            if(line instanceof VerticalLine) {
                return null;
            }
            RegularLine regularLine = (RegularLine)line;
            return new Point2D(this.x, regularLine.valueOf(this.x));
        }

        @Override
        public double valueOf(double x) {
            throw new UnsupportedOperationException();
        }
    }
}
