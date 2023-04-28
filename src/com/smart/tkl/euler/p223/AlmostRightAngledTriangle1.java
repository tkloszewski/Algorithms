package com.smart.tkl.euler.p223;

import com.smart.tkl.lib.Stack;
import java.util.ArrayList;
import java.util.List;

public class AlmostRightAngledTriangle1 {

    private static final int[][] T1 = new int[][] {
            {1, -2, 2},
            {2, -1, 2},
            {2, -2, 3}
    };

    private static final int[][] T2 = new int[][] {
            {-1, 2, 2},
            {-2, 1, 2},
            {-2, 2, 3}
    };

    private static final int[][] T3 = new int[][] {
            {1, 2, 2},
            {2, 1, 2},
            {2, 2, 3}
    };

    private final int perimeterLimit;

    public AlmostRightAngledTriangle1(int perimeterLimit) {
        this.perimeterLimit = perimeterLimit;
    }

    public static void main(String[] args) {
        int limit = 25000000;
        long time1 = System.currentTimeMillis();
        AlmostRightAngledTriangle1 counter = new AlmostRightAngledTriangle1(limit);
        long count = counter.countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Fast force count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countTriangles() {
        long result = 2;

        Stack<Triple> stack = new Stack<>();
        stack.push(new Triple(1, 1, 1));
        stack.push(new Triple(1, 2, 2));

        while (!stack.isEmpty()) {
            Triple triple = stack.pop();

            Triple nextTriple = transform(triple, T1);
            if(nextTriple.perimeter <= perimeterLimit) {
               result++;
               stack.push(nextTriple);
            }

            if(triple.a != triple.b) {
               nextTriple = transform(triple, T2);
               if(nextTriple.perimeter <= perimeterLimit) {
                   result++;
                   stack.push(nextTriple);
               }
            }

            nextTriple = transform(triple, T3);
            if(nextTriple.perimeter <= perimeterLimit) {
                result++;
                stack.push(nextTriple);
            }
        }

        return result;
    }

    private static Triple transform(Triple triple, int[][] transformation) {
        int[] product = new int[3];
        int[] tripleVector = new int[]{triple.a, triple.b, triple.c};

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                product[i] += transformation[i][j] * tripleVector[j];
            }
        }

        return new Triple(product[0], product[1], product[2]);
    }

    private static class Triple {
        final int a, b, c;
        final int perimeter;

        public Triple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.perimeter = a + b + c;
        }

        @Override
        public String toString() {
            return "Triple{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }

    private static long bruteForceCount(int limit) {
        List<Triple> triples = new ArrayList<>();
        for(int c = 1; c < limit; c++) {
            for(int b = 1; b <=c && b + c < limit; b++) {
                for(int a = 1; a <= b && a + b + c <= limit; a++) {
                    int s1 = a * a + b * b;
                    int s2 = c * c + 1;
                    if(s1 == s2) {
                        triples.add(new Triple(a, b, c));
                    }
                }
            }
        }

        return triples.size();
    }
}
