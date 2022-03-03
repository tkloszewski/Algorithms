package com.smart.tkl.euler.p86;

public class CuboidRoute {

    private final int target;

    public CuboidRoute(int target) {
        this.target = target;
    }

    public static void main(String[] args) {
        CuboidRoute cuboidRoute = new CuboidRoute(1000000);
        int solution = cuboidRoute.findSolution();
        System.out.println("Found M: " + solution);
    }

    public int findSolution() {
        int count = 0;

        int a = 0;
        while (count <= target) {
            a++;
            for(int b = 1; b <= a; b++) {
                for(int c = 1; c <= b; c++) {
                    double sqrt = Math.sqrt(a * a + (b + c) * (b + c));
                    if(sqrt == (int)sqrt) {
                        count++;
                    }
                }
            }
        }

        return a;
    }
}
