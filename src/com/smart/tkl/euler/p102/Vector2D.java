package com.smart.tkl.euler.p102;

class Vector2D {

    final Point2D p1, p2;
    final int x, y;

    public Vector2D(Point2D p1, Point2D p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.x = p2.x - p1.x;
        this.y = p2.y - p1.y;
    }
}
