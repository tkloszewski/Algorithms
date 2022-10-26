package com.smart.tkl.euler.p126;

public class CuboidLayers {

    private final int cuboidsCount;

    public CuboidLayers(int cubesCount) {
        this.cuboidsCount = cubesCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        CuboidLayers cuboidLayers = new CuboidLayers(1000);
        int leastValueOfCubes = cuboidLayers.findLeasValueOfCubes();
        long time2 = System.currentTimeMillis();
        System.out.println("Least value of cubes: " + leastValueOfCubes);
        System.out.println("The solution took: " + (time2 - time1));
    }

    public int findLeasValueOfCubes() {
        int cubes = 6;
        while (countCuboids(cubes) != this.cuboidsCount) {
            cubes += 2;
        }
        System.out.println("Cubes: " + cubes);
        return cubes;
    }

    public int countCuboids(int cubes) {
        int count = 0;
        int maxLayer = getMaxLayers(cubes);
        for(int n = 1; n <= maxLayer; n++) {
            int maxSize = getMaxSizeForLayer(cubes, n);
            for(int a = 1; a <= maxSize; a++) {
                if(countCubesInLayer(n, a, 1, 1) > cubes) {
                   break;
                }
                for(int b = a; b <= maxSize; b++) {
                    if(countCubesInLayer(n, a, b, 1) > cubes) {
                       break;
                    }
                    for(int c = b; c <= maxSize; c++) {
                        int cubesInLayer = countCubesInLayer(n, a, b, c);
                        if(cubesInLayer >= cubes) {
                            if(cubesInLayer == cubes) {
                               count++;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }

    private int getMaxSizeForLayer(int cubes, int n) {
        return (cubes - 2 - 4 * n * n + 4 * n) / (4 * n);
    }

    private int getMaxLayers(int cubes) {
        return (int)Math.sqrt(((double)cubes - 2) / 4);
    }

    private int countCubesInLayer(int n, int a, int b, int c) {
        return 2 * (a * b + a * c + b * c) + 4 * (n - 1) * (a + b + c)
                + 4 * (n - 2) * (n - 1);
    }
}
