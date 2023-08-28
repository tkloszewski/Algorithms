package com.smart.tkl.lib.tree.pythagorean;

import com.smart.tkl.lib.Stack;
import java.util.List;

public class PythagoreanTripleTree {

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
    private final List<PythagoreanTriple> initialTriples;

    public PythagoreanTripleTree(int perimeterLimit, List<PythagoreanTriple> initialTriples) {
        this.perimeterLimit = perimeterLimit;
        this.initialTriples = initialTriples;
    }

    public long countTriangles() {
        long result = 0;

        Stack<PythagoreanTriple> stack = new Stack<>();
        for(PythagoreanTriple initialTriple : initialTriples) {
            stack.push(initialTriple);
            result++;
        }


        while (!stack.isEmpty()) {
            PythagoreanTriple triple = stack.pop();

            PythagoreanTriple nextTriple = transform(triple, T1);
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

    private static PythagoreanTriple transform(PythagoreanTriple triple, int[][] transformation) {
        int[] product = new int[3];
        int[] tripleVector = new int[]{triple.a, triple.b, triple.c};

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                product[i] += transformation[i][j] * tripleVector[j];
            }
        }

        return new PythagoreanTriple(product[0], product[1], product[2]);
    }
}
