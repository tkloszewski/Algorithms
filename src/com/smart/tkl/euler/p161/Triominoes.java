package com.smart.tkl.euler.p161;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Triominoes {

    private final int width;
    private final int height;
    private final GridBitmask gridBitmask;

    private final Map<Integer, Map<Integer, Map<Integer, Long>>>[] memo;

    private long cacheHits = 0;

    private final List<Triomino> allTriominoes = List.of(new UpperLeftTriomino(), new UpperRightTriomino(), new BottomLeftTriomino(),
            new BottomRightTriomino(), new HorizontalTriomino(), new VerticalTriomino());

    private final List<Triomino> allButHorizontalTriominoes = List.of(new UpperLeftTriomino(), new UpperRightTriomino(), new BottomLeftTriomino(),
            new BottomRightTriomino(), new VerticalTriomino());

    private final List<Triomino> smallestSetOfTriominoes = List.of(new UpperRightTriomino(), new VerticalTriomino());

    public Triominoes(int width, int height) {
        if(width > height) {
           int temp = width;
           width = height;
           height = temp;
        }
        this.width = width;
        this.height = height;
        this.gridBitmask = new GridBitmask(width, height);
        this.memo = new Map[height];
        for(int y = 0; y < height; y++) {
            this.memo[y] = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        Triominoes triominoes = new Triominoes(9, 12);
        long count = triominoes.countTiles();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Cache hits: " + triominoes.cacheHits);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countTiles() {
        if((this.width * this.height) % 3 != 0){
            return 0;
        }
        return countTiles(0, 0);
    }

    public long countTiles(int x, int y) {
        long result = 0;

        while (y < gridBitmask.height && gridBitmask.isFullRow(y)) {
            y++;
            x = 0;
        }
        if(y == gridBitmask.height) {
            return 1;
        }

        int row1 = this.gridBitmask.getRowBitmask(y);
        int row2 = y + 1 < height ? this.gridBitmask.getRowBitmask(y + 1) : -1;
        int row3 = y + 2 < height ? this.gridBitmask.getRowBitmask(y + 2) : -1;

        Long cachedValue = getFromCache(y, row1, row2, row3);
        if(cachedValue != null) {
            cacheHits++;
            return cachedValue;
        }

        while (gridBitmask.isMarkedAt(x, y)) {
            x++;
        }

        for(Triomino triomino : getTriominoes(x)) {
            if(triomino.canBePut(x, y, gridBitmask)) {
                triomino.put(x, y, gridBitmask);
                if(gridBitmask.isFullRow(y)) {
                    result += countTiles(0, y + 1);
                }
                else {
                    result += countTiles(x + triomino.getHorizontalShift(), y);
                }
                triomino.remove(x, y , gridBitmask);
            }
        }

        putToCache(y, row1, row2, row3, result);

        return result;
    }

    private Long getFromCache(int y, int row1, int row2, int row3) {
        Map<Integer, Map<Integer, Map<Integer, Long>>> nestedMap = memo[y];

        Map<Integer, Map<Integer, Long>> map1 = nestedMap.get(row1);
        if(map1 == null) {
            return null;
        }
        Map<Integer, Long> map2 = map1.get(row2);
        if(map2 == null) {
            return null;
        }
        return map2.get(row3);
    }

    private void putToCache(int y, int row1, int row2, int row3, long value) {
        Map<Integer, Map<Integer, Map<Integer, Long>>> nestedMap = memo[y];
        Map<Integer, Map<Integer, Long>> map1 = nestedMap.get(row1);

        if(map1 == null) {
            map1 = new HashMap<>();
            Map<Integer, Long> map2 = new HashMap<>();
            map2.put(row3, value);
            map1.put(row2, map2);
            nestedMap.put(row1, map1);
            return;
        }

        Map<Integer, Long> map2 = map1.get(row2);
        if(map2 == null) {
            map2 = new HashMap<>();
            map2.put(row3, value);
            map1.put(row2, map2);
            return;
        }
        map2.put(row3, value);
    }

    private List<Triomino> getTriominoes(int x) {
        int remainingSpace = this.width - x;
        if(remainingSpace >= 3) {
           return allTriominoes;
        }
        if(remainingSpace == 2) {
           return allButHorizontalTriominoes;
        }
        if(remainingSpace == 1) {
           return smallestSetOfTriominoes;
        }
        return List.of();
    }

    private interface Triomino {

        boolean canBePut(int x, int y, GridBitmask gridBitmask);

        void put(int x, int y, GridBitmask gridBitmask);

        void remove(int x, int y, GridBitmask gridBitmask);

        int getHorizontalShift();
    }

    private static class UpperLeftTriomino implements Triomino {

        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(x + 1 < gridBitmask.width && y + 1 < gridBitmask.height) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x][y + 1] == 0
                        && gridBitmask.bitmask[x + 1][y + 1] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x, y + 1);
            gridBitmask.mark(x + 1, y + 1);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x, y + 1);
            gridBitmask.unmark(x + 1, y + 1);
        }

        @Override
        public int getHorizontalShift() {
            return 1;
        }
    }

    private static class UpperRightTriomino implements Triomino {
        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(x - 1 >= 0 && y + 1 < gridBitmask.height) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x][y + 1] == 0
                        && gridBitmask.bitmask[x - 1][y + 1] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x, y + 1);
            gridBitmask.mark(x - 1, y + 1);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x, y + 1);
            gridBitmask.unmark(x - 1, y + 1);
        }

        @Override
        public int getHorizontalShift() {
            return 1;
        }
    }

    private static class BottomLeftTriomino implements Triomino {
        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(x + 1 < gridBitmask.width && y + 1 < gridBitmask.height) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x][y + 1] == 0
                        && gridBitmask.bitmask[x + 1][y] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x, y + 1);
            gridBitmask.mark(x + 1, y);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x, y + 1);
            gridBitmask.unmark(x + 1, y);
        }

        @Override
        public int getHorizontalShift() {
            return 2;
        }
    }

    private static class BottomRightTriomino implements Triomino {
        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(x + 1 < gridBitmask.width && y + 1 < gridBitmask.height) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x + 1][y] == 0
                        && gridBitmask.bitmask[x + 1][y + 1] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x + 1, y);
            gridBitmask.mark(x + 1, y + 1);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x + 1, y);
            gridBitmask.unmark(x + 1, y + 1);
        }

        @Override
        public int getHorizontalShift() {
            return 2;
        }
    }

    private static class HorizontalTriomino implements Triomino {
        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(x + 2 < gridBitmask.width) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x + 1][y] == 0
                        && gridBitmask.bitmask[x + 2][y] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x + 1, y);
            gridBitmask.mark(x + 2, y);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x + 1, y);
            gridBitmask.unmark(x + 2, y);
        }

        @Override
        public int getHorizontalShift() {
            return 3;
        }
    }

    private static class VerticalTriomino implements Triomino {
        @Override
        public boolean canBePut(int x, int y, GridBitmask gridBitmask) {
            if(y + 2 < gridBitmask.height) {
                return gridBitmask.bitmask[x][y] == 0 && gridBitmask.bitmask[x][y + 1] == 0
                        && gridBitmask.bitmask[x][y + 2] == 0;
            }
            return false;
        }

        @Override
        public void put(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.mark(x, y);
            gridBitmask.mark(x, y + 1);
            gridBitmask.mark(x, y + 2);
        }

        @Override
        public void remove(int x, int y, GridBitmask gridBitmask) {
            gridBitmask.unmark(x, y);
            gridBitmask.unmark(x, y + 1);
            gridBitmask.unmark(x, y + 2);
        }

        @Override
        public int getHorizontalShift() {
            return 1;
        }
    }
}
