package com.smart.tkl.euler.p116;

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
}
