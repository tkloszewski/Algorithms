package com.smart.tkl.euler.p189;

public class TriColorTriangle {

    private final int height;
    private final int[] triangles;
    private final long[][] memo;

    public TriColorTriangle(int height) {
        this.height = height;
        this.triangles = new int[height * height];
        this.memo = new long[height][(int)Math.pow(3, height)];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        TriColorTriangle triColorTriangle = new TriColorTriangle(8);
        long count = triColorTriangle.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private int getRowHashValue(int row) {
        int start = row * row;
        int end = start + 2 * row;

        int hash = 0, reversedHash = 0;

        for(int k = 0, i = start, pow = 1; i <= end; i += 2, pow *= 3, k += 2) {
            hash += pow * triangles[i];
            reversedHash += pow * triangles[end - k];
        }

        return Math.min(hash, reversedHash);
    }

    public long count() {
        return count(0, 0);
    }

    private long count(int row, int pos) {
        int rowLength = 2 * row + 1;

        int nextRow = row;
        int nextPos = pos + 1;

        if(nextPos == rowLength) {
            nextPos = 0;
            nextRow++;
        }

        if(row == this.height) {
            return 1;
        }

        if(row > 0 && pos == 0) {
            int hash = getRowHashValue(row - 1);
            if(this.memo[row - 1][hash] != 0) {
                return this.memo[row - 1][hash];
            }
        }

        long result = 0;

        int triangleIdx = row * row + pos;

        if(pos % 2 == 0) {
            if(pos == 0) {
                for (int color = 0; color <= 2; color++) {
                    triangles[triangleIdx] = color;
                    result += count(nextRow, nextPos);
                }
            }
            else {
                for (int color = 0; color <= 2; color++) {
                    if(triangles[triangleIdx - 1] == color) {
                        continue;
                    }
                    triangles[triangleIdx] = color;
                    result += count(nextRow, nextPos);
                }
            }
        }
        else {
            int offset = rowLength - 1;
            for (int color = 0; color <= 2; color++) {
                if(triangles[triangleIdx - 1] == color || triangles[triangleIdx - offset] == color) {
                    continue;
                }
                triangles[triangleIdx] = color;
                result += count(nextRow, nextPos);
            }
        }

        if(pos == 0 && row > 0) {
            int hashValue = getRowHashValue(row - 1);
            memo[row - 1][hashValue] = result;
        }

        return result;
    }

}
