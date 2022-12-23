package com.smart.tkl.euler.p150;

public class MinSubTriangle {

    private final int numOfRows;
    private final long[][] triangle;
    private final long[][] rowSums;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        MinSubTriangle subTriangle = new MinSubTriangle(1000);
        long min = subTriangle.findMinSubTriangleSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Min sum: " + min);
        System.out.println("The solution took: " + (time2 - time1));
    }

    public MinSubTriangle(int rows) {
        this.numOfRows = rows;
        this.triangle = new long[rows][];
        this.rowSums = new long[rows][];
        for(int i = 0; i < rows; i++) {
            triangle[i] = new long[i + 1];
            rowSums[i] = new long[i + 1];
        }
        fillTriangleAndSums();
    }

    public long findMinSubTriangleSum() {
        long min = Long.MAX_VALUE;
        for(int i = 0; i < triangle.length; i++) {
            long[] row = triangle[i];
            for(int j = 0; j < row.length; j++) {
                long sum = 0;
                if(j == 0) {
                   for(int k = i, l = 0; k < rowSums.length; k++, l++) {
                       sum += rowSums[k][l];
                       min = Math.min(sum, min);
                   }
                }
                else {
                    for(int k = i, l = j; k < rowSums.length; k++, l++) {
                        sum += (rowSums[k][l] - rowSums[k][j - 1]);
                        min = Math.min(sum, min);
                    }
                }
            }
        }

        return min;
    }

    private void fillTriangleAndSums() {
        long t = 0;
        long c1 = (long)Math.pow(2, 20);
        long c2 = (long)Math.pow(2, 19);

        for(int i = 0; i < numOfRows; i++) {
            long rowSum = 0;
            for(int j = 0; j <= i; j++) {
                t = (615949 * t + 797807) % c1;
                long value = t - c2;
                rowSum += value;
                triangle[i][j] = value;
                rowSums[i][j] = rowSum;
            }
        }
    }


}
