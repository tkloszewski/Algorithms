package com.smart.tkl.euler.p86;

public class CuboidRoute {

    private final int target;

    public CuboidRoute(int target) {
        this.target = target;
    }

    public static void main(String[] args) {
        CuboidRoute cuboidRoute = new CuboidRoute(1000000);
        int solution = cuboidRoute.optimizedFind();
        System.out.println("Found M: " + solution);
    }

    public int optimizedFind() {
        int count = 0;
        int a = 0;
        while (count <= target) {
            a++;
            for(int sum = 2; sum <= 2 * a; sum++) {
                double sqrt = Math.sqrt(a * a + sum * sum);
                if(sqrt == (int)sqrt) {
                   if(sum <= a) {
                      count += sum / 2;
                   }
                   else {
                      count += (2 * a - sum) / 2 + 1;
                   }
                }
            }
        }
        System.out.println("count= " + count + " M = " + a);
        return a;

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
