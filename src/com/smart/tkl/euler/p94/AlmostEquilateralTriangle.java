package com.smart.tkl.euler.p94;

import java.util.LinkedList;
import java.util.List;

public class AlmostEquilateralTriangle {
    
    private final long perimeterLimit;
    
    public AlmostEquilateralTriangle(int limit) {
        this.perimeterLimit = limit;
    }
    
    public static void main(String[] args) {
        AlmostEquilateralTriangle almostEquilateralTriangle = new AlmostEquilateralTriangle(1000000000);   
        AlmostEquilateralResult result = almostEquilateralTriangle.solveByPellsEquation();
        System.out.println("Number of triangles by Pells method: " + result.integerTriangles.size());
        System.out.println("Total perimeter by Pells method: " + result.totalPerimeter);
    }   
    
    public AlmostEquilateralResult solveByPellsEquation() {
        List<Triangle> triangles = new LinkedList<>();
        long totalPerimeter = 0;
        
        long x1 = 2, y1 = 1, n = 3, perimeter, a ;
        long prevX = 1, prevY = 0;
        
        while (true) {
            long currX = x1 * prevX + n * y1 * prevY;
            long currY = x1 * prevY + y1 * prevX;           
            
            if((2 * currX + 1) % 3 == 0) {
                a = (2 * currX + 1) / 3;
                if (3 * a + 1 <= this.perimeterLimit) {
                    triangles.add(new Triangle(a, a, a + 1));
                    totalPerimeter += (3 * a + 1);
                }
            }
            
            if((2 * currX - 1) % 3 == 0) {
                a = (2 * currX - 1) / 3;
                if (a - 1 > 0 && (3 * a - 1) <= this.perimeterLimit) {
                    triangles.add(new Triangle(a, a, a - 1));
                    totalPerimeter += (3 * a - 1);
                }
            }
            
            a = (2 * currX - 1) / 3;
            perimeter = 3 * a - 1;
            
            if(perimeter > this.perimeterLimit) {
                break;
            }
            
            prevX = currX;
            prevY = currY;
        }
        
        return new AlmostEquilateralResult(triangles, totalPerimeter);
    }
    
    private static class AlmostEquilateralResult {
        final List<Triangle> integerTriangles;
        final long totalPerimeter;
        
        public AlmostEquilateralResult(List<Triangle> integerTriangles, long totalPerimeter) {
            this.integerTriangles = integerTriangles;
            this.totalPerimeter = totalPerimeter;
        }
    }
}
