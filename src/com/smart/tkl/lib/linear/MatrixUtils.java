package com.smart.tkl.lib.linear;

public class MatrixUtils {

    public static long[][] pow(long[][] a, long pow) {
        checkMatrixIsSquare(a);

        int size = a.length;
        long[][] result = new long[size][size];

        for(int i = 0; i < size; i++) {
            result[i][i] = 1;
        }

        while (pow > 0) {
            if(pow % 2 == 1) {
                result = multiply(result, a);
            }
            a = multiply(a, a);
            pow = pow / 2;
        }

        return result;
    }

    public static long[][] pow(long[][] a, long pow, long mod) {
        checkMatrixIsSquare(a);

        int size = a.length;
        long[][] result = new long[size][size];

        for(int i = 0; i < size; i++) {
            result[i][i] = 1;
        }

        while (pow > 0) {
            if(pow % 2 == 1) {
                result = multiply(result, a, mod);
            }
            a = multiply(a, a, mod);
            pow = pow / 2;
        }

        return result;
    }

    public static long[][] multiply(long[][] a, long[][] b) {
        long[][] c = new long[a.length][b[0].length];
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < b[0].length; j++) {
                for(int k = 0; k < a[0].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public static long[][] multiply(long[][] a, long[][] b, long mod) {
        long[][] c = new long[a.length][b[0].length];
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < b[0].length; j++) {
                for(int k = 0; k < a[0].length; k++) {
                    c[i][j] += (a[i][k] * b[k][j]) % mod;
                    c[i][j] = c[i][j] % mod;
                }
            }
        }
        return c;
    }

    public static void printGrid(long[][] tab) {
        for (long[] arr : tab) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.printf("%d ", arr[j]);
            }
            System.out.println();
        }
    }

    private static void checkMatrixIsSquare(long[][] a) {
        if(a.length != a[0].length) {
            throw new IllegalArgumentException("Non-square matrix exponentiation!!!");
        }
    }
}
