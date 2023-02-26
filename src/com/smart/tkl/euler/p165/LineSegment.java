package com.smart.tkl.euler.p165;

public class LineSegment {

    final long x1;
    final long x2;
    final long y1;
    final long y2;

    final long minY;
    final long maxY;

    final long dx;
    final long dy;

    public LineSegment(long t1, long t2, long t3, long t4) {
        this.x1 = Math.min(t1, t3);
        this.x2 = Math.max(t1, t3);
        this.y1 = t1 < t3 ? t2 : t4;
        this.y2 = t1 < t3 ? t4 : t2;
        this.minY = Math.min(t2, t4);
        this.maxY = Math.max(t2, t4);
        this.dx = this.x2 - this.x1;
        this.dy = this.y2 - this.y1;
    }

    public Intersection getIntersection(LineSegment lineSegment) {
        if(this.isVertical() && lineSegment.isVertical()) {
           return null;
        }
        if(this.isVertical()) {
           return getVerticalIntersection(this.x1, this.y1, this.y2, lineSegment);
        }
        if(lineSegment.isVertical()) {
           return getVerticalIntersection(lineSegment.x1, lineSegment.y1, lineSegment.y2, this);
        }

        long a = lineSegment.x1 * lineSegment.dy * this.dx  - this.x1 * this.dy * lineSegment.dx +
                (this.y1 - lineSegment.y1) * this.dx * lineSegment.dx;

        long b = this.dx * lineSegment.dy - lineSegment.dx * this.dy;

        if(b == 0) {
           return null;
        }

        if(a * b < 0) {
          return null;
        }

        if (this.liesOnSegment(a, b) && lineSegment.liesOnSegment(a, b)) {
           long c = dy * (a - b * this.x1) + y1 * b * dx;
           long d = dx * b;
           return new Intersection(a, b, c, d);
        }

        return null;
    }

    public boolean isVertical() {
        return x1 == x2;
    }

    public boolean liesOnSegment(long a, long b) {
        long p1 = a - b * x1;
        long p2 = a - b * x2;
        return isGreaterThanZero(p1, b) && isLessThanZero(p2, b);
    }

    private static Intersection getVerticalIntersection(long x, long y1, long y2, LineSegment lineSegment) {
        if(x <= lineSegment.x1 || x >= lineSegment.x2) {
           return null;
        }

        long yMin1 = Math.min(y1, y2);
        long yMax1 = Math.max(y1, y2);

        long c = lineSegment.dy * (x - lineSegment.x1) +  lineSegment.y1 * lineSegment.dx;
        long d = lineSegment.dx;

        if(lineSegment.dy == 0) {
           return lineSegment.y1 < yMax1 && lineSegment.y1 > yMin1 ? new Intersection(x, 1, lineSegment.y1, 1) : null;
        }

        boolean isIntersection = isGreaterThanZero(c - d * yMin1, d)
                && isLessThanZero(c - d * yMax1, d)
                && isGreaterThanZero(c - d * lineSegment.minY, d)
                && isLessThanZero(c - d * lineSegment.maxY, d);

        return isIntersection ? new Intersection(x, 1, c, d) : null;
    }

    private static boolean isGreaterThanZero(long p, long q) {
        return p * q > 0;
    }

    private static boolean isLessThanZero(long p, long q) {
        return p * q < 0;
    }

    @Override
    public String toString() {
        return "{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", minY=" + minY +
                ", maxY=" + maxY +
                ", dx=" + dx +
                ", dy=" + dy +
                '}';
    }
}
