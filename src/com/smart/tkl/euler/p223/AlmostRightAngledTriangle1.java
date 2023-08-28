package com.smart.tkl.euler.p223;

import com.smart.tkl.lib.Stack;
import com.smart.tkl.lib.tree.pythagorean.PythagoreanTriple;
import com.smart.tkl.lib.tree.pythagorean.PythagoreanTripleTree;
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
        List<PythagoreanTriple> initialTriples = List.of(
                new PythagoreanTriple(1, 1, 1), new PythagoreanTriple(1, 2, 2));
        long count = new PythagoreanTripleTree(limit, initialTriples).countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Fast force count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
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
