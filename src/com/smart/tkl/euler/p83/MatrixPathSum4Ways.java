package com.smart.tkl.euler.p83;

import com.smart.tkl.euler.MatrixFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixPathSum4Ways {

    private static final int[][] TEST_MATRIX_1 = {
            {7, 5, 4, 3},
            {2, 2, 1, 7},
            {3, 4, 5, 1},
            {4, 5, 6, 2}
    };

    private static final int[][] TEST_MATRIX_2 = {
            {131, 673, 234, 103, 18},
            {201, 96,  342, 965, 150},
            {630, 803, 746, 422, 111},
            {537, 699, 497, 121, 956},
            {805, 732, 524, 37,  331}
    };

    private static final String MATRIX_80_80_FILE_PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\p83\\p083_matrix.txt";

    private static final int[] ROW_VECTOR = {-1, 0, 1, 0};
    private static final int[] COL_VECTOR = {0, -1, 0, 1};

    private final int[][] matrix;
    private final int rowCount;
    private final int colCount;

    public static void main(String[] args) {
        MatrixPathSum4Ways matrix = new MatrixPathSum4Ways(TEST_MATRIX_1);
        int minPathSum  = matrix.findMinPathSum();
        System.out.println("Min path sum for test matrix 1: " + minPathSum);

        matrix = new MatrixPathSum4Ways(TEST_MATRIX_2);
        minPathSum  = matrix.findMinPathSum();
        System.out.println("Min path sum for test matrix 2: " + minPathSum);

        try {
            matrix = new MatrixPathSum4Ways(MATRIX_80_80_FILE_PATH);
            long time1 = System.currentTimeMillis();
            minPathSum  = matrix.findMinPathSum();
            long time2 = System.currentTimeMillis();
            System.out.println("Min path sum for 80x80 matrix: " + minPathSum + ". Time in ms: " + (time2 - time1));
        } catch (IOException e) {
            System.out.println("Error reading matrix file: " + e);
        }
    }

    public MatrixPathSum4Ways(String filePath) throws IOException  {
        this(new MatrixFileReader(filePath).readMatrix());
    }

    public MatrixPathSum4Ways(int[][] matrix) {
        checkSize(matrix);
        this.matrix = matrix;
        this.rowCount = matrix.length;
        this.colCount = matrix[0].length;
    }

    public int findMinPathSum() {
        CostEntry[][] costTable = buildCostTable(this.matrix);
        return costTable[0][0].value;
    }

    private CostEntry[][] buildCostTable(int[][] a) {
        int lastRow = a.length - 1;
        int lastColumn = a[0].length - 1;

        CostEntry[][] costTable = initCostTable(a);
        costTable[lastRow][lastColumn].status = Status.ESTIMATED;

        List<CostEntry> estimatedCostEntries = new ArrayList<>(a.length);
        estimatedCostEntries.add(costTable[lastRow][lastColumn]);

        while(!costTable[0][0].status.equals(Status.ESTIMATED)) {
            int minCostEntryIdx = findMinEntryClosestToTopLeft(estimatedCostEntries);
            CostEntry minCostEntry = estimatedCostEntries.get(minCostEntryIdx);
            estimatedCostEntries.remove(minCostEntryIdx);
            for(int i = 0; i < 4; i++) {
                int neighbourRowIdx = minCostEntry.rowIdx + ROW_VECTOR[i];
                int neighbourColIdx = minCostEntry.colIdx + COL_VECTOR[i];
                if(isInTable(neighbourRowIdx, neighbourColIdx)) {
                    CostEntry neighbour = costTable[neighbourRowIdx][neighbourColIdx];
                    if(neighbour.status.equals(Status.NEW)) {
                        neighbour.value = neighbour.value + minCostEntry.value;
                        neighbour.status = Status.ESTIMATED;
                        neighbour.next = minCostEntry;
                        estimatedCostEntries.add(neighbour);
                    }
                }
            }
            minCostEntry.status = Status.DONE;
        }

        return costTable;
    }

    private int findMinEntryClosestToTopLeft(List<CostEntry> estimatedCostEntries) {
        int foundIdx = -1, minValue = Integer.MAX_VALUE;
        int minDistance = Integer.MAX_VALUE;
        for(int i = 0; i < estimatedCostEntries.size(); i++) {
            CostEntry costEntry = estimatedCostEntries.get(i);
            int distance = costEntry.rowIdx + costEntry.colIdx;
            if((minValue > costEntry.value) || (minValue == costEntry.value && minDistance > distance)) {
                minValue = costEntry.value;
                foundIdx = i;
                minDistance = costEntry.rowIdx + costEntry.colIdx;
            }
        }
        return foundIdx;
    }

    private CostEntry[][] initCostTable(int[][] a) {
        CostEntry[][] costTable = new CostEntry[a.length][a[0].length];
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                costTable[i][j] = new CostEntry(i, j, a[i][j]);
            }
        }
        return costTable;
    }

    private boolean isInTable(int rowIdx, int colIdx) {
        return (rowIdx < this.rowCount && rowIdx >= 0) && (colIdx < this.colCount && colIdx >= 0);
    }

    private static void checkSize(int[][] m) {
        if(m == null || m.length == 0) {
            throw new IllegalArgumentException("Matrix mus be not empty.");
        }
        int size = m[0].length;
        for(int[] row : m) {
            if(row == null || row.length != size) {
                throw new IllegalArgumentException("Invalid matrix size.");
            }
        }
    }

    private static void printCostTable(CostEntry[][] t) {
        for (CostEntry[] row : t) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void printMatrix(int[][] t) {
        for (int[] row : t) {
            System.out.println(Arrays.toString(row));
        }
    }

    enum Status {NEW, ESTIMATED, DONE}

    private static class CostEntry {
        int rowIdx;
        int colIdx;
        int value;

        Status status;
        CostEntry next;

        public CostEntry(int rowIdx, int colIdx, int value) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
            this.value = value;
            this.status = Status.NEW;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }
}
