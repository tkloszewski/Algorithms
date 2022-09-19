package com.smart.tkl.euler.p102;

import com.smart.tkl.euler.p94.Triangle;

import java.util.List;

public class TriangleContainmentSolver {

    private static final String PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p102\\p102_triangles.txt";

    public static void main(String[] args) throws Exception {
        Triangle2D triangle1 = new Triangle2D(-340, 495, -153, -910, 835, -947);
        TriangleContainment triangleContainment1 = new TriangleContainment(triangle1);
        boolean isInside = triangleContainment1.isPointInside(0, 0);
        System.out.println("Is inside in triangle 1: " + isInside);

        Triangle2D triangle2 = new Triangle2D(-175, 41, -421, -714, 574, -645);
        TriangleContainment triangleContainment2 = new TriangleContainment(triangle2);
        isInside = triangleContainment2.isPointInside(0, 0);

        System.out.println("Is inside in triangle 2: " + isInside);


        List<Triangle2D> triangles = new TriangleReader(PATH).readTriangles();
        System.out.println("Number of triangles: " + triangles.size());

        int isInsideCount = 0;
        for(Triangle2D triangle : triangles) {
            TriangleContainment triangleContainment = new TriangleContainment(triangle);
            if(triangleContainment.isPointInside(0 , 0)) {
                isInsideCount++;
            }
        }

        System.out.println("Number of triangles for which interior contains origin: " + isInsideCount);
    }

}
