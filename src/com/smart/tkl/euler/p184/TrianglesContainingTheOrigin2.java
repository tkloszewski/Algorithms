package com.smart.tkl.euler.p184;

import com.smart.tkl.lib.Stack;

public class TrianglesContainingTheOrigin2 {

    private final int r;

    public TrianglesContainingTheOrigin2(int r) {
        this.r = r;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int r = 500;
        TrianglesContainingTheOrigin2 trianglesContainingTheOrigin = new TrianglesContainingTheOrigin2(r);
        long count = trianglesContainingTheOrigin.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long points = pointsInTheCircle(this.r);
        long halfPoints = points / 2;

        long squares = 2L * (r - 1) * (r - 1);
        long cubes = 2L * (r - 1) * (r - 1) * (r - 1);

        long pointsOnDiagonal = (long) (r / Math.sqrt(2));

        squares += 2 * pointsOnDiagonal * pointsOnDiagonal;
        cubes += 2 * pointsOnDiagonal * pointsOnDiagonal * pointsOnDiagonal;

        Stack<CoPrime> stack = new Stack<>();
        stack.push(new CoPrime(2, 1));
        stack.push(new CoPrime(3 , 1));

        while (!stack.isEmpty()) {
            CoPrime coPrime = stack.pop();
            int x = coPrime.a;
            int y = coPrime.b;
            long pointsOnLine = (long) (Math.sqrt(r * r - 1) / Math.sqrt(x * x + y * y));
            squares += 4 * pointsOnLine * pointsOnLine;
            cubes += 4 * pointsOnLine * pointsOnLine * pointsOnLine;

            if(2 * x - y < r) {
               stack.push(new CoPrime(2 * x - y, x));
            }
            if(2 * x + y < r) {
                stack.push(new CoPrime(2 * x + y, x));
            }
            if(x + 2 * y < r) {
                stack.push(new CoPrime(x + 2 * y, y));
            }
        }

        return (halfPoints * halfPoints * halfPoints - 3 * halfPoints * squares + 2 * cubes) / 3;
    }

    private static long pointsInTheCircle(int r) {
        long points =  r - 1;
        for(int x = 1; x < r; x++) {
             points += Math.sqrt(r * r - x * x - 1);
        }
        return 4 * points;
    }

    private static class CoPrime {
        final int a;
        final int b;

        public CoPrime(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public String toString() {
            return "CoPrime{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }
}
