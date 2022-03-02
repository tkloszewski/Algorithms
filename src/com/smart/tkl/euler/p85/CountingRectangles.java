package com.smart.tkl.euler.p85;

public class CountingRectangles {

    private final int countLimit;

    public static void main(String[] args) {
        CountingRectangles countingRectangles = new CountingRectangles(2000000);
        long time1 = System.currentTimeMillis();
        RectGrid rectGrid = countingRectangles.findNearestRectArea();
        long time2 = System.currentTimeMillis();
        System.out.println("Nearest rect area: " + rectGrid + " found in ms: " + (time2 - time1));
    }

    public CountingRectangles(int countLimit) {
        this.countLimit = countLimit;
    }

    public RectGrid findNearestRectArea() {
        int limit = getMaxGridSize(this.countLimit);
        int[][] countTab = countDP(limit);

        int minDiff = Integer.MAX_VALUE;
        RectGrid foundRectGrid = new RectGrid(1, 1, 1);

        for(int i = 1; i <= limit; i++) {
            for(int j = 1; j <= limit; j++) {
                int diff = Math.abs(countLimit - countTab[i][j]);
                if(diff < minDiff) {
                    foundRectGrid = new RectGrid(i, j, countTab[i][j]);
                    minDiff = diff;
                }
            }
        }

        return foundRectGrid;
    }

    private static int count(int m, int n) {
        int result = 0;
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                result += (m - i + 1) * (n - j + 1);
            }
        }

        return result;
    }

    private static int[][] countDP(int limit) {
        int[][] tab = new int[limit + 1][limit + 1];
        for(int j = 1; j <= limit; j++) {
            tab[1][j] = (j * (j + 1)) / 2;
        }

        for(int j = 1; j <= limit; j++) {
            for(int i = 2; i <= limit; i++) {
                tab[i][j] = tab[i - 1][j] + i * (j * (j + 1)) / 2;
            }
        }

        return tab;
    }

    private static int getMaxGridSize(int countLimit) {
        int delta = 1 + 8 * countLimit;
        return (int)((Math.sqrt(delta) - 1) / 2) + 10;
    }

    private static class RectGrid {
        final int rows;
        final int columns;
        final int count;
        final int area;

        public RectGrid(int rows, int columns, int count) {
            this.rows = rows;
            this.columns = columns;
            this.count = count;
            this.area = rows * columns;
        }

        @Override
        public String toString() {
            return "RectGrid{" +
                    "rows=" + rows +
                    ", columns=" + columns +
                    ", count=" + count +
                    ", area=" + area +
                    '}';
        }
    }


}
