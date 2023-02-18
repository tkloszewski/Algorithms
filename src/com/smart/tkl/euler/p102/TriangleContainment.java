package com.smart.tkl.euler.p102;

class TriangleContainment {

    private final Triangle2D triangle;

    public TriangleContainment(Triangle2D triangle) {
        this.triangle = triangle;
    }

    public boolean isPointInside(int x, int y) {
        return isPointInside(new Point2D(x, y));
    }

    public boolean isPointInside(Point2D point) {
        int triangleCrossProduct = crossProductAbsValue(triangle.vector1, triangle.vector2);
        if(triangleCrossProduct == 0) {
            return false;
        }

        Vector2D vector1 = new Vector2D(point, triangle.point1);
        Vector2D vector2 = new Vector2D(point, triangle.point2);
        Vector2D vector3 = new Vector2D(point, triangle.point2);
        Vector2D vector4 = new Vector2D(point, triangle.point3);
        Vector2D vector5 = new Vector2D(point, triangle.point3);
        Vector2D vector6 = new Vector2D(point, triangle.point1);

        int subTriangleCrossProduct1 = crossProductAbsValue(vector1, vector2);
        if(subTriangleCrossProduct1 == 0) {
            return false;
        }

        int subTriangleCrossProduct2 = crossProductAbsValue(vector3, vector4);
        if(subTriangleCrossProduct2 == 0) {
            return false;
        }

        int subTriangleCrossProduct3 = crossProductAbsValue(vector5, vector6);
        if(subTriangleCrossProduct3 == 0) {
            return false;
        }

        int allCrossProductSums = subTriangleCrossProduct1 + subTriangleCrossProduct2 + subTriangleCrossProduct3;

        return triangleCrossProduct == allCrossProductSums;
    }

    private int crossProductAbsValue(Vector2D v1, Vector2D v2) {
        return Math.abs(crossProductValue(v1, v2));
    }

    private int crossProductValue(Vector2D v1, Vector2D v2) {
        return v1.x * v2.y - v1.y * v2.x;
    }
}
