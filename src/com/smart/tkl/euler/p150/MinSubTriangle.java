package com.smart.tkl.euler.p150;

import com.smart.tkl.lib.tree.binary.heap.MinRawBinaryHeap;
import java.util.ArrayList;
import java.util.List;

public class MinSubTriangle {

    private final int numOfRows;
    private final int K;
    private final long[][] triangle;
    private final long[][] rowSums;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        MinSubTriangle subTriangle = new MinSubTriangle(350, 100000);
       // long min = subTriangle.findMinSubTriangleSum();
        List<Long> minSums = subTriangle.findMinSums();
        long min = minSums.get(0);
        long time2 = System.currentTimeMillis();
        System.out.println("Min sum: " + min);
        System.out.println("The solution took: " + (time2 - time1));
    }

    public MinSubTriangle(int rows, int K) {
        this.numOfRows = rows;
        this.K = K;
        this.triangle = new long[rows][];
        this.rowSums = new long[rows][];
        for(int i = 0; i < rows; i++) {
            triangle[i] = new long[i + 1];
            rowSums[i] = new long[i + 1];
        }
        fillTriangleAndSums();
    }

    public MinSubTriangle(long[][] triangles, int K) {
        this.triangle = triangles;
        this.K = K;
        this.numOfRows = triangles.length;
        int rows = triangles.length;
        this.rowSums = new long[rows][];
        for(int i = 0; i < rows; i++) {
            rowSums[i] = new long[i + 1];
        }
        fillSums();
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

    public List<Long> findMinSums() {
        List<Long> sums = new ArrayList<>(this.K);

        MinRawBinaryHeap<Long> minSizedBinaryHeap = new MinRawBinaryHeap<>(Long.class);

        long time1 = System.currentTimeMillis();
        for(int i = 0; i < triangle.length; i++) {
            long[] row = triangle[i];
            for(int j = 0; j < row.length; j++) {
                long sum = 0;
                if(j == 0) {
                    for(int k = i, l = 0; k < rowSums.length; k++, l++) {
                        sum += rowSums[k][l];
                        minSizedBinaryHeap.insert(sum);
                    }
                }
                else {
                    for(int k = i, l = j; k < rowSums.length; k++, l++) {
                        sum += (rowSums[k][l] - rowSums[k][j - 1]);
                        minSizedBinaryHeap.insert(sum);
                    }
                }
            }
        }

        for(int i = 0; i < K; i++) {
            sums.add(minSizedBinaryHeap.deleteFirst());
        }

        return sums;
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

    private void fillSums() {
        for(int i = 0; i < numOfRows; i++) {
            long rowSum = 0;
            for(int j = 0; j <= i; j++) {
                long value = this.triangle[i][j];
                rowSum += value;
                rowSums[i][j] = rowSum;
            }
        }
    }


}
