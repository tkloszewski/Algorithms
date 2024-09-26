package com.smart.tkl.euler.p148;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangleDivisibility2 {

    private static final long MOD = (long) Math.pow(10, 9) + 7;
    private static final long[] MOD_POWERS_28 = getModPowers28();
    private static final long[] POWERS7 = getPowers7();

    public static void main(String[] args) {
         long rowsColumns = 1000000000000000000L;
         long time1 = System.currentTimeMillis();
         for(int i = 0; i < 1; i++) {
             count(rowsColumns, rowsColumns);
         }
         long time2 = System.currentTimeMillis();
         System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long count(long row, long column) {
        if(row == 0 || column == 0) {
           return 0;
        }
        int[] rows = toBase7(row);
        int[] columns = toBase7(column, rows.length);
        return count(rows, columns);
    }

    private static long count(int[] rows, int[] columns) {
        long result = 0;
        int size = rows.length;
        int maxPower = size - 1;
        long maxPow7 = POWERS7[maxPower];
        System.out.println("Max power: " + maxPower);
        long colValueFrom = 0;
        for(int m = 0; m < columns.length; m++) {
            int col = columns[m];
            if(col == 0) {
               continue;
            }
            int pow1 = maxPower - m;
            long colPow7 = POWERS7[pow1] * col;
            long colValueTo = colValueFrom + colPow7;
            long rowValueFrom = 0;
            for(int n = 0; n < rows.length; n++) {
                int row = rows[n];
                if(row == 0) {
                   continue;
                }
                int pow2 = maxPower - n;
                long rowPow7 = POWERS7[pow2] * row;
                long rowValueTo = rowValueFrom + rowPow7;

                int pow = Math.min(pow1, pow2);
                long pow7 = POWERS7[pow];
                long pow28 = MOD_POWERS_28[pow];

                long elements = countElements(rowValueFrom, rowValueTo, colValueFrom, colValueTo, maxPow7, pow7);
                long elementsInCell = (elements * pow28) % MOD;

                result = (result + elementsInCell) % MOD;

                rowValueFrom = rowValueTo;
            }
            colValueFrom = colValueTo;
        }


        return result;
    }

    private static long countElements(long rowFrom, long rowTo, long colFrom, long colTo, long maxPow7, long minPow7) {
        long result = 1;

        boolean rowFullRange = false, columnFullRange = false;
        for(long pow7 = maxPow7; pow7 >= minPow7; pow7 = pow7 / 7) {
            long row1, row2;
            long col1, col2;
            if(!rowFullRange) {
                long q1 = rowFrom / pow7;
                long q2 = rowTo / pow7;
                long r2 = rowTo % pow7;

                if(q1 == q2) {
                   row1 = q1 + 1;
                   row2 = q2 + 1;
                   rowFrom = rowFrom - q1 * pow7;
                   rowTo = rowTo - q2 * pow7;
                }
                else {
                   row1 = Math.max(1, q1);
                   row2 = q2;
                }

                if(r2 == 0) {
                    rowFullRange = true;
                }
            }
            else {
               row1 = 1;
               row2 = 7;
            }

            if(!columnFullRange) {
                long q1 = colFrom / pow7;
                long q2 = colTo / pow7;
                long r2 = colTo % pow7;

                if(q1 == q2) {
                    col1 = q1 + 1;
                    col2 = q2 + 1;
                    colFrom = colFrom - q1 * pow7;
                    colTo = colTo - q2 * pow7;
                }
                else {
                    col1 = Math.max(1, q1);
                    col2 = q2;
                }

                if(r2 == 0) {
                    columnFullRange = true;
                }
            }
            else {
               col1 = 1;
               col2 = 7;
            }

            long multiplier = countSameDimensionElements(row1, row2, col1, col2);
            //System.out.println("(" + row1 + ":" + row2 + ") x (" + col1 + ":" + col2 + ") = " + multiplier);

            if(multiplier == 0) {
               return 0;
            }

            result = (result * multiplier) % MOD;

        }
        return result;
    }

    private static long countSameDimensionElements(long row1, long row2, long col1, long col2) {
        if(row1 == 1 && col1 == 1) {
           return col2 <= row2 ? sumFirst(col2) + (row2 - col2) * col2 : sumFirst(row2);
        }
        else if(col1 == 1 && col2 == 7) {
            return sumFirst(row2) - sumFirst(row1) + row1;
        }
        else if(col1 <= row2) {
           long result = 0;
           for(long col = col1; col <= col2; col++) {
               if(col > row2) {
                  break;
               }
               long from = Math.max(col, row1);
               result += (row2 - from + 1);
           }
           return result;
        }
        else {
            return 0;
        }
    }

    private static long sumFirst(long n) {
        return (n * (n + 1)) / 2;
    }

    private static int[] toBase7(long value) {
        List<Integer> digits = new ArrayList<>();
        while (value > 0) {
            int digit = (int) (value % 7);
            digits.add(digit);
            value = value / 7;
        }
        int size = digits.size();
        int[] result = new int[size];
        for(int i = size - 1, k = 0; i >= 0; i--, k++) {
            result[k] = digits.get(i);
        }
        return result;
    }

    private static int[] toBase7(long value, int size) {
        int[] result = new int[size];
        int pos = size - 1;
        while (value > 0) {
            int digit = (int) (value % 7);
            result[pos--] = digit;
            value = value / 7;
        }
        return result;
    }

    private static int[][] getTriangle(int size) {
        int[][] result = new int[size][];

        int[] values = new int[]{1};
        result[0] = values;
        for(int row = 1; row < size; row++) {
            int[] newValues = new int[row + 1];
            for(int i = 0; i <= row; i++) {
                int value;
                if(i == 0 || i == row) {
                   value = 1;
                }
                else {
                   value = (values[i - 1] + values[i]) % 7;
                }
                newValues[i] = value;
            }

            values = newValues;
            result[row] = newValues;
        }

        return result;
    }

    private static long triangleCount(int[][] triangle, int row, int col) {
        long result = 0;
        for(int i = 0; i < row; i++) {
            int[] rowValues = triangle[i];
            for(int j = 0; j < col && j < rowValues.length; j++) {
                if(triangle[i][j] != 0) {
                   result++;
                }
            }
        }
        return result;
    }

    private static long[] getModPowers28() {
        long[] result = new long[31];
        long value = 1;
        for(int pow = 0; pow <= 30; pow++) {
            result[pow] = value;
            value = (value * 28) % MOD;
        }
        return result;
    }

    private static long[] getPowers7() {
        long[] result = new long[23];
        long pow7 = 1;
        for(int pow = 0; pow < result.length; pow++) {
            result[pow] = pow7;
            pow7 *= 7;
        }
        return result;
    }
}
