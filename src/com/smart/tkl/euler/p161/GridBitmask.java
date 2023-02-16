package com.smart.tkl.euler.p161;

public class GridBitmask {

    final int width;
    final int height;
    final int[] rowBitMask;
    final int[][] bitmask;

    private final int fullRowMask;

    public GridBitmask(int width, int height) {
        this.width = width;
        this.height = height;
        this.rowBitMask = new int[height];
        this.bitmask = new int[width][height];
        this.fullRowMask = (int)Math.pow(2, width) - 1;
    }

    public void mark(int x, int y) {
        setMarked(x, y, true);
    }

    public void unmark(int x, int y) {
        setMarked(x, y , false);
    }

    public boolean isMarkedAt(int x, int y) {
        return bitmask[x][y] == 1;
    }

    public void setMarked(int x, int y, boolean marked) {
        this.bitmask[x][y] = marked ? 1 : 0;
        if(marked) {
           rowBitMask[y] = rowBitMask[y] | (1 << x);
        }
        else {
           rowBitMask[y] = rowBitMask[y] & ~(1 << x);
        }
    }

    public int getRowBitmask(int y) {
        return rowBitMask[y];
    }

    public boolean isFullRow(int y) {
        return rowBitMask[y] == fullRowMask;
    }
}
