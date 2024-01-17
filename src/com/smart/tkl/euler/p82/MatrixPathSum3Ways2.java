package com.smart.tkl.euler.p82;

import com.smart.tkl.euler.MatrixFileReader;
import com.smart.tkl.lib.tree.binary.heap.MinBinaryHeap;
import java.io.IOException;
import java.util.Random;

public class MatrixPathSum3Ways2 {

    private static final String MATRIX_80_80_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p82\\p082_matrix.txt";

    private static final long[][] TEST_MATRIX = {
            {131, 673, 234, 103, 18},
            {201, 96,  342, 965, 150},
            {630, 803, 746, 422, 111},
            {537, 699, 497, 121, 956},
            {805, 732, 524, 37,  331}
    };

    private static final int[] ROW_VECTOR = {-1, 0, 1};
    private static final int[] COL_VECTOR = { 0, 1, 0};

    private final long[][] matrix;
    private final int rowCount;
    private final int colCount;

    public static void main(String[] args) throws IOException {
        MatrixPathSum3Ways2 matrix = new MatrixPathSum3Ways2(TEST_MATRIX);
        long minPath = matrix.getMinPath();
        System.out.println("Min path: " + minPath);

        long time1 = System.currentTimeMillis();
        matrix = new MatrixPathSum3Ways2(MATRIX_80_80_FILE_PATH);
        minPath = matrix.getMinPath();
        long time2 = System.currentTimeMillis();
        System.out.println("Min path: " + minPath);
        System.out.println("Time in ms: " + (time2 - time1));

        long[][] bigMatrix = new long[1000][1000];
        for(int i = 0; i < 1000; i++) {
            for(int j = 0; j < 1000; j++) {
                bigMatrix[i][j] = new Random().nextInt(10);
            }
        }

        time1 = System.currentTimeMillis();
        matrix = new MatrixPathSum3Ways2(bigMatrix);
        minPath = matrix.getMinPath();
        time2 = System.currentTimeMillis();
        System.out.println("Min path: " + minPath);
        System.out.println("Time in ms: " + (time2 - time1));

    }

    public MatrixPathSum3Ways2(String filePath) throws IOException {
        this(new MatrixFileReader(filePath).readMatrix());
    }

    public MatrixPathSum3Ways2(long[][] matrix) {
        checkSize(matrix);
        this.matrix = matrix;
        this.rowCount = matrix.length;
        this.colCount = matrix[0].length;
    }

    public long getMinPath() {
        CostEntry costEntry = findMinCostEntry(this.matrix);
        return costEntry.value;
    }

    private CostEntry findMinCostEntry(long[][] a) {
        CostEntry[][] costTable = initCostTable(a);

        MinBinaryHeap<CostEntry> minBinaryHeap = new MinBinaryHeap<>(CostEntry.class);

        for(int row = 0; row < rowCount; row++) {
            costTable[row][0].status = Status.ESTIMATED;
            minBinaryHeap.insert(costTable[row][0]);
        }

        CostEntry foundCostEntry = null;

        while(foundCostEntry == null) {
            CostEntry minCostEntry = minBinaryHeap.deleteFirst();
            for(int i = 0; i < 3; i++) {
                int neighbourRowIdx = minCostEntry.rowIdx + ROW_VECTOR[i];
                int neighbourColIdx = minCostEntry.colIdx + COL_VECTOR[i];
                if(isInTable(neighbourRowIdx, neighbourColIdx)) {
                    CostEntry neighbour = costTable[neighbourRowIdx][neighbourColIdx];
                    if(neighbour.status.equals(Status.NEW)) {
                        neighbour.value = neighbour.value + minCostEntry.value;
                        neighbour.status = Status.ESTIMATED;
                        minBinaryHeap.insert(neighbour);
                    }
                }
            }
            minCostEntry.status = Status.DONE;
            if(minCostEntry.colIdx == colCount - 1) {
               foundCostEntry = minCostEntry;
            }
        }

        return foundCostEntry;
    }

    private boolean isInTable(int rowIdx, int colIdx) {
        return (rowIdx < this.rowCount && rowIdx >= 0) && (colIdx < this.colCount && colIdx >= 0);
    }

    private static void checkSize(long[][] m) {
        if(m == null || m.length == 0) {
            throw new IllegalArgumentException("Matrix mus be not empty.");
        }
        int size = m[0].length;
        for(long[] row : m) {
            if(row == null || row.length != size) {
                throw new IllegalArgumentException("Invalid matrix size.");
            }
        }
    }

    private static CostEntry[][] initCostTable(long[][] a) {
        CostEntry[][] costTable = new CostEntry[a.length][a[0].length];
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < a[0].length; j++) {
                costTable[i][j] = new CostEntry(i, j, a[i][j]);
            }
        }
        return costTable;
    }

    enum Status {NEW, ESTIMATED, DONE}

    private static class CostEntry implements Comparable<CostEntry> {
        int rowIdx;
        int colIdx;
        long value;

        Status status;

        public CostEntry(int rowIdx, int colIdx, long value) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
            this.value = value;
            this.status = Status.NEW;
        }

        @Override
        public int compareTo(CostEntry other) {
            int compareResult = Long.compare(this.value, other.value);
            if(compareResult == 0) {
                compareResult = -Integer.compare(this.colIdx, other.colIdx);
            }
            return compareResult;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }
}
