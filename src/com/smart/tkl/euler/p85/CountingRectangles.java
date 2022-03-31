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

        int minDiff = Integer.MAX_VALUE;
        RectGrid foundRectGrid = new RectGrid(1, 1, 1);      
    
        for(int i = 1; i <= limit; i++) {
            for(int j = i; j <= limit; j++) {
                int count = ((i * (i + 1)) / 2) * ((j * (j + 1)) / 2);                
                int diff = Math.abs(countLimit - count);
                if(diff < minDiff) {
                    foundRectGrid = new RectGrid(i, j, count);
                    minDiff = diff;
                }
            }
        }

        return foundRectGrid;
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
