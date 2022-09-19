package com.smart.tkl.euler.p102;

public class Triangle2D {

    final Point2D point1, point2, point3;
    final Vector2D vector1, vector2;

    public Triangle2D(int x1, int y1, int x2, int y2, int x3, int y3) {
        this(new Point2D(x1, y1), new Point2D(x2, y2), new Point2D(x3, y3));
    }

    public Triangle2D(Point2D p1, Point2D p2, Point2D p3) {
        this.point1 = p1;
        this.point2 = p2;
        this.point3 = p3;
        this.vector1 = new Vector2D(p1, p2);
        this.vector2 = new Vector2D(p1, p3);
    }
}
