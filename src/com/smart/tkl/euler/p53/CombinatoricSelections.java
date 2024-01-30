package com.smart.tkl.euler.p53;

import java.math.BigInteger;

public class CombinatoricSelections {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        BigInteger[][] pascalTriangle = getPascalTriangle(100);
        long count = count(pascalTriangle, 1000000L);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long count(BigInteger[][] triangle, long th) {
        long count = 0;
        BigInteger max = BigInteger.valueOf(th);
        for(int i = 1; i < triangle.length; i++) {
            int rowLength = triangle[i].length;
            if(triangle[i][1].compareTo(max) > 0) {
                long a1 = rowLength - 2;
                long an = triangle.length - 2;
                long n =  triangle.length - i;
                count += ((n * (a1 + an)) / 2);
                break;
            }
            else {
               for(int j = 2; j <= rowLength / 2; j++) {
                   if(triangle[i][j].compareTo(max) > 0) {
                      count += rowLength - 2 * j;
                      break;
                   }
               }
            }
        }
        return count;
    }

    private static BigInteger[][] getPascalTriangle(int N) {
        BigInteger[][] triangle = new BigInteger[N + 1][];
        triangle[0] = new BigInteger[1];
        triangle[0][0] = BigInteger.ONE;
        for(int i = 1; i <= N; i++) {
            triangle[i] = new BigInteger[i + 1];
            triangle[i][0] = BigInteger.ONE;
            triangle[i][i] = BigInteger.ONE;
            for(int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1].add(triangle[i - 1][j]);
            }
        }
        return triangle;
    }
}
