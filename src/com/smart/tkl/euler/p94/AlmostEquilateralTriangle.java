package com.smart.tkl.euler.p94;

import com.smart.tkl.lib.utils.continuedfraction.PeriodicFraction;
import com.smart.tkl.lib.utils.continuedfraction.SquareRootPeriodicFractionGenerator;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class AlmostEquilateralTriangle {
    
    private final long perimeterLimit;
    private List<Triangle> triangles;
    
    public AlmostEquilateralTriangle(long limit) {
        this.perimeterLimit = limit;
    }
    
    public static void main(String[] args) {
        long perimeterLimit = 1000000000000000000L;
        AlmostEquilateralTriangle almostEquilateralTriangle = new AlmostEquilateralTriangle(perimeterLimit);
        AlmostEquilateralResult result = almostEquilateralTriangle.solveByPellsEquation();
        System.out.println("Number of triangles by Pells method: " + result.integerTriangles.size());
        System.out.println("Total perimeter by Pells method: " + result.totalPerimeter);
        System.out.println(result.integerTriangles);

        SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(3);
        PeriodicFraction fraction = generator.generate();
        System.out.println(fraction);
    }

    public long sumOfAllPerimeters(long limit) {
        if(triangles == null) {
            triangles = solveByPellsEquation().integerTriangles;
            triangles.sort(Comparator.comparingLong(t -> t.perimeter));
        }

        long totalPerimeter = 0;
        for(Triangle triangle : triangles) {
            if(triangle.perimeter > limit) {
               break;
            }
            totalPerimeter += triangle.perimeter;
        }
        return totalPerimeter;
    }
    
    AlmostEquilateralResult solveByPellsEquation() {
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
