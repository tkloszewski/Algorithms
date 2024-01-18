package com.smart.tkl.euler.p116;

import com.smart.tkl.lib.linear.MatrixUtils;

public class RedGreenOrBlueTiles {

    private final int length;

    public RedGreenOrBlueTiles(int length) {
        this.length = length;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        RedGreenOrBlueTiles redGreenOrBlueTiles = new RedGreenOrBlueTiles(50);
        long totalWays = redGreenOrBlueTiles.countTotalWays();
        long time2 = System.currentTimeMillis();
        System.out.println("Total ways: " + totalWays);
        System.out.println("Total time in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        totalWays = redGreenOrBlueTiles.countTotalWaysByMatrixExp();
        time2 = System.currentTimeMillis();
        System.out.println("Total ways by matrix exp: " + totalWays);
        System.out.println("Total time in ms: " + (time2 - time1));
    }

    public long countTotalWays() {
        long redTilesWays = countWays(2);
        long greenTilesWays = countWays(3);
        long blueTilesWays = countWays(4);
        return redTilesWays + greenTilesWays + blueTilesWays;
    }

    private long countWays(int colouredTileLength) {
        int maxNumberOfColouredTiles = length / colouredTileLength;
        long[][] ways = new long[maxNumberOfColouredTiles + 1][this.length + 1];

        for(int rowLength = colouredTileLength; rowLength <= length; rowLength++) {
            ways[1][rowLength] = rowLength - colouredTileLength + 1;
        }

        for(int numberOfColouredTiles = 2; numberOfColouredTiles <= maxNumberOfColouredTiles; numberOfColouredTiles++) {
            for(int rowLength = numberOfColouredTiles * colouredTileLength; rowLength <= length; rowLength++) {
                for(int pos = (numberOfColouredTiles - 1) * colouredTileLength; pos <= rowLength - colouredTileLength; pos++) {
                    ways[numberOfColouredTiles][rowLength] += ways[numberOfColouredTiles - 1][pos];
                }
            }
        }

        long waysCount = 0;
        for(int numberOfColouredTiles = 1; numberOfColouredTiles <= maxNumberOfColouredTiles; numberOfColouredTiles++) {
            waysCount += ways[numberOfColouredTiles][this.length];
        }
        return waysCount;
    }

    public long countTotalWaysByMatrixExp() {
        long redCount = countRedWaysByMatrixExp(this.length);
        long greenCount = countGreenWaysByMatrixExp(this.length);
        long blueCount = countBlueWaysByMatrixExp(this.length);
        return redCount + greenCount + blueCount;
    }

    private static long countRedWaysByMatrixExp(long length) {
        long[][] initial = new long[][]{
                {1},
                {0},
                {1}
        };

        if(length <= 2) {
           return initial[(int)length][0];
        }

        long[][] matrix = new long[][] {
                {1, 0, 0},
                {0, 0, 1},
                {1, 1, 1}
        };

        long mod = Long.MAX_VALUE;
        matrix = MatrixUtils.pow(matrix, length - 2, mod);
        long[][] result = MatrixUtils.multiply(matrix, initial);
        return result[2][0];
    }

    private static long countGreenWaysByMatrixExp(long length) {
        long[][] initial = new long[][]{
                {1},
                {0},
                {0},
                {1}
        };

        if(length <= 3) {
            return initial[(int)length][0];
        }

        long[][] matrix = new long[][] {
                {1, 0, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {1, 1, 0, 1}
        };

        long mod = Long.MAX_VALUE;
        matrix = MatrixUtils.pow(matrix, length - 3, mod);
        long[][] result = MatrixUtils.multiply(matrix, initial);
        return result[3][0];
    }

    private static long countBlueWaysByMatrixExp(long length) {
        long[][] initial = new long[][]{
                {1},
                {0},
                {0},
                {0},
                {1}
        };

        if(length <= 4) {
            return initial[(int)length][0];
        }

        long[][] matrix = new long[][] {
                {1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 0, 0, 1}
        };

        long mod = Long.MAX_VALUE;
        matrix = MatrixUtils.pow(matrix, length - 4, mod);
        long[][] result = MatrixUtils.multiply(matrix, initial);
        return result[4][0];
    }
}
