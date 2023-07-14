package com.smart.tkl.euler.p345;

import com.smart.tkl.lib.utils.GenericUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MatrixMinMaxSum {

    private final int[][] matrix;
    private final int size;
    private final Map<Integer, Integer> memo;

    private static volatile long time1, time2;

    private static final int[][] TEST_MATRIX = {
            {7,   53,  183, 439, 863},
            {497, 383, 563, 79,  973},
            {287, 63,  343, 169, 583},
            {627, 343, 773, 959, 943},
            {767, 473, 103, 699, 303},
    };

    private static final int[][] MATRIX_345 = {
            {7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {627, 343, 773, 959, 943, 767, 473, 103, 699, 303, 957, 703, 583, 639, 913},
            {447, 283, 463, 29,  23,  487, 463, 993, 119, 883, 327, 493, 423, 159, 743},
            {217, 623, 3,   399, 853, 407, 103, 983, 89,  463, 290, 516, 212, 462, 350},
            {960, 376, 682, 962, 300, 780, 486, 502, 912, 800, 250, 346, 172, 812, 350},
            {870, 456, 192, 162, 593, 473, 915, 45,  989, 873, 823, 965, 425, 329, 803},
            {973, 965, 905, 919, 133, 673, 665, 235, 509, 613, 673, 815, 165, 992, 326},
            {322, 148, 972, 962, 286, 255, 941, 541, 265, 323, 925, 281, 601, 95,  973},
            {445, 721, 11,  525, 473, 65,  511, 164, 138, 672, 18,  428, 154, 448, 848},
            {414, 456, 310, 312, 798, 104, 566, 520, 302, 248, 694, 976, 430, 392, 198},
            {184, 829, 373, 181, 631, 101, 969, 613, 840, 740, 778, 458, 284, 760, 390},
            {821, 461, 843, 513, 17,  901, 711, 993, 293, 157, 274, 94,  192, 156, 574},
            {34,  124, 4,   878, 450, 476, 712, 914, 838, 669, 875, 299, 823, 329, 699},
            {815, 559, 813, 459, 522, 788, 168, 586, 966, 232, 308, 833, 251, 631, 107},
            {813, 883, 451, 509, 615, 77,  281, 613, 459, 205, 380, 274, 302, 35,  805}
    };

    private static final int[][] MATRIX_30_30 = {
            {7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {627, 343, 773, 959, 943, 767, 473, 103, 699, 303, 957, 703, 583, 639, 913, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {447, 283, 463, 29,  23,  487, 463, 993, 119, 883, 327, 493, 423, 159, 743, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {217, 623, 3,   399, 853, 407, 103, 983, 89,  463, 290, 516, 212, 462, 350, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {960, 376, 682, 962, 300, 780, 486, 502, 912, 800, 250, 346, 172, 812, 350, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {870, 456, 192, 162, 593, 473, 915, 45,  989, 873, 823, 965, 425, 329, 803, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {973, 965, 905, 919, 133, 673, 665, 235, 509, 613, 673, 815, 165, 992, 326, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {322, 148, 972, 962, 286, 255, 941, 541, 265, 323, 925, 281, 601, 95,  973, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {445, 721, 11,  525, 473, 65,  511, 164, 138, 672, 18,  428, 154, 448, 848, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {414, 456, 310, 312, 798, 104, 566, 520, 302, 248, 694, 976, 430, 392, 198, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {184, 829, 373, 181, 631, 101, 969, 613, 840, 740, 778, 458, 284, 760, 390, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {821, 461, 843, 513, 17,  901, 711, 993, 293, 157, 274, 94,  192, 156, 574, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {34,  124, 4,   878, 450, 476, 712, 914, 838, 669, 875, 299, 823, 329, 699, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {815, 559, 813, 459, 522, 788, 168, 586, 966, 232, 308, 833, 251, 631, 107, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {813, 883, 451, 509, 615, 77,  281, 613, 459, 205, 380, 274, 302, 35,  805, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {627, 343, 773, 959, 943, 767, 473, 103, 699, 303, 957, 703, 583, 639, 913, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {447, 283, 463, 29,  23,  487, 463, 993, 119, 883, 327, 493, 423, 159, 743, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {217, 623, 3,   399, 853, 407, 103, 983, 89,  463, 290, 516, 212, 462, 350, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {960, 376, 682, 962, 300, 780, 486, 502, 912, 800, 250, 346, 172, 812, 350, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {870, 456, 192, 162, 593, 473, 915, 45,  989, 873, 823, 965, 425, 329, 803, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {973, 965, 905, 919, 133, 673, 665, 235, 509, 613, 673, 815, 165, 992, 326, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {322, 148, 972, 962, 286, 255, 941, 541, 265, 323, 925, 281, 601, 95,  973, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {445, 721, 11,  525, 473, 65,  511, 164, 138, 672, 18,  428, 154, 448, 848, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {414, 456, 310, 312, 798, 104, 566, 520, 302, 248, 694, 976, 430, 392, 198, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {184, 829, 373, 181, 631, 101, 969, 613, 840, 740, 778, 458, 284, 760, 390, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {821, 461, 843, 513, 17,  901, 711, 993, 293, 157, 274, 94,  192, 156, 574, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {34,  124, 4,   878, 450, 476, 712, 914, 838, 669, 875, 299, 823, 329, 699, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {815, 559, 813, 459, 522, 788, 168, 586, 966, 232, 308, 833, 251, 631, 107, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583},
            {813, 883, 451, 509, 615, 77,  281, 613, 459, 205, 380, 274, 302, 35,  805, 7,   53,  183, 439, 863, 497, 383, 563, 79,  973, 287, 63,  343, 169, 583}
    };




    public static void main(String[] args) {
        MatrixMinMaxSum matrix = new MatrixMinMaxSum(MATRIX_345);
        time1 = System.currentTimeMillis();
        int maxSum = matrix.calcMaxSumDP();
        time2 = System.currentTimeMillis();
        System.out.println("Max sum DP: " + maxSum + ". Time: " + (time2 - time1) + " milliseconds.");

        time1 = System.currentTimeMillis();
        maxSum = matrix.calcMaxSumHungarian();
        time2 = System.currentTimeMillis();
        System.out.println("Max sum Hungarian method: " + maxSum + ". Time: " + (time2 - time1) + " milliseconds.");

        matrix = new MatrixMinMaxSum(MATRIX_30_30);
        time1 = System.currentTimeMillis();
        maxSum = matrix.calcMaxSumHungarian();
        time2 = System.currentTimeMillis();
        System.out.println("Max sum Hungarian method for 30x30 matrix: " + maxSum + ". Time: " + (time2 - time1) + " milliseconds.");

        int bigSize = 5000;
        int[][] matrix_1000_1000 = new int[bigSize][bigSize];
        for(int i = 0; i < matrix_1000_1000.length; i++) {
            for(int j = 0; j < matrix_1000_1000.length; j++) {
                matrix_1000_1000[i][j] = i + j;
            }
        }


        matrix = new MatrixMinMaxSum(matrix_1000_1000);
        time1 = System.currentTimeMillis();
        maxSum = matrix.calcMaxSumHungarian();
        time2 = System.currentTimeMillis();
        System.out.println("Max sum Hungarian method for 5000x5000 matrix: " + maxSum + ". Time: " + (time2 - time1) + " milliseconds.");
    }

    public MatrixMinMaxSum(int[][] m) {
        checkSize(m);
        this.matrix = m;
        this.size = m.length;
        this.memo = new HashMap<>((int)Math.pow(2, size));
    }

    /*Calculates max sum using recursion with memoization*/
    public int calcMaxSumDP() {
        int mask = (int)Math.pow(2, this.size) - 1;
        return calcMaxSumDP(mask, 0);
    }

    /*Calculates max sum using hungarian method*/
    public int calcMaxSumHungarian() {
        return findMax(this.matrix);
    }

    /*Calculates min sum using hungarian method*/
    public int calcMinSumHungarian() {
        return findMin(this.matrix);
    }

    /*Returns max path using hungarian method*/
    public List<Coordinates> findMaxPath() {
        return findMinOrMaxAssignments(GenericUtils.clone(this.matrix), false);
    }

    /*Returns min path using hungarian method*/
    public List<Coordinates> findMinPath() {
        return findMinOrMaxAssignments(GenericUtils.clone(this.matrix), true);
    }

    private int calcMaxSumDP(int mask, int row) {
        if(this.memo.containsKey(mask)) {
            return this.memo.get(mask);
        }
        int result = 0;
        if(mask == 0) {
            return 0;
        }
        for(int j = 0; j < this.size; j++) {
            if(((mask >> j) & 1) == 1) {
                int sum = this.matrix[row][j] + calcMaxSumDP(mask & ~(1 << j), row + 1);
                result = Math.max(result, sum);
            }
        }
        this.memo.put(mask, result);
        return result;
    }

    private static void checkSize(int[][] m) {
        if(m == null || m.length == 0) {
            throw new IllegalArgumentException("Matrix mus be not empty.");
        }
        int size = m.length;
        for(int[] row : m) {
            if(row == null || row.length != size) {
                throw new IllegalArgumentException("Invalid matrix size.");
            }
        }
    }

    private static int findMin(int[][] tab) {
        int result = 0;
        List<Coordinates> assignments = findMinOrMaxAssignments(GenericUtils.clone(tab), true);
        for(Coordinates assignment : assignments) {
            result += tab[assignment.x][assignment.y];
        }
        return result;
    }

    private static int findMax(int[][] tab) {
        int result = 0;
        List<Coordinates> assignments = findMinOrMaxAssignments(GenericUtils.clone(tab), false);
        for(Coordinates assignment : assignments) {
            result += tab[assignment.x][assignment.y];
        }
        return result;
    }

    private static List<Coordinates> findMinOrMaxAssignments(int[][] tab, boolean minMode) {
        int size = tab.length;
        subtractMinOrMaxInRowsAndColumns(tab, minMode);

        List<CrossedLine> crossedLines = getMinCrossedLines(tab);
        while(crossedLines.size() != size) {
            subtractMinOrMaxEntry(tab, crossedLines, minMode);
            crossedLines = getMinCrossedLines(tab);
        }

        return getOptimalAssignment(tab);
    }

    private static void subtractMinOrMaxInRowsAndColumns(int[][] tab, boolean minMode) {
        for(int[] row : tab) {
            int minMax = minMode ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            for(int value : row) {
                minMax = minMode ? Math.min(minMax, value) : Math.max(minMax, value);
            }
            for(int i = 0; i < row.length; i++) {
                row[i] = row[i] - minMax;
            }
        }
        for(int i = 0; i < tab.length; i++) {
            int minMax = minMode ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            for (int[] row : tab) {
                minMax = minMode ? Math.min(minMax, row[i]) : Math.max(minMax, row[i]);
            }
            if (minMax != 0) {
                for (int[] row : tab) {
                    row[i] = row[i] - minMax;
                }
            }
        }
    }

    /*Determine the smallest or greatest entry not covered by any line. Subtract this entry from each uncovered row,
     and then add it to each covered column. */
    private static void subtractMinOrMaxEntry(int[][] tab, List<CrossedLine> crossedLines, boolean minMode) {
        Map<Integer, CrossedLine> crossedRowsMap = crossedLines.stream()
                .filter(line -> line.type.equals(LineType.ROW))
                .collect(Collectors.toMap(line -> line.idx, Function.identity()));

        Map<Integer, CrossedLine> crossedColumnsMap = crossedLines.stream()
                .filter(line -> line.type.equals(LineType.COLUMN))
                .collect(Collectors.toMap(line -> line.idx, Function.identity()));

        int minMax = minMode ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for(int i = 0; i < tab.length; i++) {
            if(!crossedRowsMap.containsKey(i)) {
                int[] row = tab[i];
                for(int j = 0; j < row.length; j++) {
                    if(!crossedColumnsMap.containsKey(j)) {
                        minMax = minMode ? Math.min(minMax, row[j]) : Math.max(minMax, row[j]);
                    }
                }
            }
        }

        for(int i = 0; i < tab.length; i++) {
            if(!crossedRowsMap.containsKey(i)) {
                int[] row = tab[i];
                for(int j = 0; j < row.length; j++) {
                    row[j] = row[j] - minMax;
                }
            }
        }

        for(int i = 0; i < tab.length; i++) {
            if(crossedColumnsMap.containsKey(i)) {
                for (int[] row : tab) {
                    row[i] = row[i] + minMax;
                }
            }
        }
    }

    private static List<Coordinates> getOptimalAssignment(int[][] tab) {
        CrossedLine[] crossedColumns = new CrossedLine[tab.length];
        for(int i = 0; i < tab.length; i++) {
            int zeroesInColumns = 0;
            for (int[] row : tab) {
                if (row[i] == 0) {
                    zeroesInColumns++;
                }
            }
            crossedColumns[i] = new CrossedLine(LineType.COLUMN, zeroesInColumns, i);
        }

        List<Coordinates> result = new ArrayList<>();

        int columnMask = (int)Math.pow(2, tab.length) - 1;
        int rowMask = (int)Math.pow(2, tab.length) - 1;

        while(result.size() != tab.length) {
            CrossedLine minAssignmentLine = new CrossedLine(LineType.COLUMN, Integer.MAX_VALUE ,0);
            for(CrossedLine crossedColumn : crossedColumns) {
                if(((columnMask >> crossedColumn.idx) & 1) == 1 && minAssignmentLine.zeroes > crossedColumn.zeroes) {
                    minAssignmentLine = crossedColumn;
                }
            }

            columnMask = columnMask & ~(1 << minAssignmentLine.idx);
            int idx = minAssignmentLine.idx;

            //find first zero row index in min assignment column
            int rowIdx = 0;
            for(int i = 0; i < tab.length; i++) {
                if(((rowMask >> i) & 1) == 1 && tab[i][idx] == 0) {
                    rowIdx = i;
                    rowMask = rowMask & ~(1 << i);
                    break;
                }
            }

            //decrement number of zeroes in all not-selected columns that have zero in rowIdx row
            for(int i = 0; i < tab.length; i++) {
                if(((columnMask >> i) & 1) == 1 && tab[rowIdx][i] == 0){
                    crossedColumns[i].zeroes--;
                }
            }

            result.add(new Coordinates(rowIdx, minAssignmentLine.idx));
        }

        return result;
    }

    private static List<CrossedLine> getMinCrossedLines(int[][] tab) {
        List<CrossedLine> result = new ArrayList<>();

        CrossedLine[] crossedRows = new CrossedLine[tab.length];
        CrossedLine[] crossedColumns = new CrossedLine[tab.length];

        for(int i = 0; i < tab.length; i++) {
            int zeroesInColumns = 0;
            int zeroesInRows = 0;

            for (int[] row : tab) {
                if (row[i] == 0) {
                    zeroesInColumns++;
                }
            }

            for(int k = 0; k < tab.length; k++) {
                if(tab[i][k] == 0) {
                    zeroesInRows++;
                }
            }

            crossedColumns[i] = new CrossedLine(LineType.COLUMN, zeroesInColumns, i);
            crossedRows[i] = new CrossedLine(LineType.ROW, zeroesInRows, i);
        }

        int assignmentMask = 0, markedColumnMask = 0, markedRowsMask = 0;
        List<Integer> newlyMarkedRows = new ArrayList<>();
        List<Integer> newlyMarkedColumns = new ArrayList<>();

        //find all assigned rows
        AssignedRow[] assignedRows = new AssignedRow[tab.length];
        for(int i = 0; i < tab.length; i++) {
            int[] row = tab[i];
            AssignedRow assignedRow = new AssignedRow(i);
            for(int j = 0; j < row.length; j++) {
                if(row[j] == 0 && ((assignmentMask >> j) & 1) == 0) {
                    if(!assignedRow.assigned) {
                        assignedRow = new AssignedRow(i, j);
                        assignmentMask = assignmentMask | (1 << j);
                    }
                    assignedRow.zeroes++;
                }
            }
            assignedRows[i] = assignedRow;
            if(!assignedRow.assigned) {
                markedRowsMask = markedRowsMask | (1 << i);
                newlyMarkedRows.add(i);
            }
        }

        while(newlyMarkedRows.size() != 0 || newlyMarkedColumns.size() != 0) {
            //Mark all columns having zeros in newly marked row(s)
            newlyMarkedColumns.clear();
            for(int markedRow : newlyMarkedRows) {
                for(int j = 0; j < tab[markedRow].length ; j++) {
                    if(((markedColumnMask >> j) & 1) == 0 && tab[markedRow][j] == 0) {
                        markedColumnMask = markedColumnMask | (1 << j);
                        newlyMarkedColumns.add(j);
                    }
                }
            }

            //Mark all rows having assignments in newly marked column(s)
            newlyMarkedRows.clear();
            for(int markedColumn : newlyMarkedColumns) {
                for(int i = 0; i < assignedRows.length; i++) {
                    if(assignedRows[i].assigned) {
                        if (((markedRowsMask >> i) & 1) == 0 && markedColumn == assignedRows[i].columnIdx) {
                            markedRowsMask = markedRowsMask | (1 << i);
                            newlyMarkedRows.add(i);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < tab.length; i++) {
            if(((markedRowsMask >> i) & 1) == 0) {
                result.add(crossedRows[i]);
            }
        }
        for(int i = 0; i < tab.length; i++) {
            if(((markedColumnMask >> i) & 1) == 1) {
                result.add(crossedColumns[i]);
            }
        }

        return result;
    }

    enum LineType {ROW, COLUMN};

    private static class AssignedRow {
        int idx;
        int columnIdx;
        int zeroes;
        boolean assigned;

        public AssignedRow(int idx) {
            this.idx = idx;
        }

        public AssignedRow(int idx, int columnIdx) {
            this.idx = idx;
            this.columnIdx = columnIdx;
            this.assigned = true;
        }
    }

    private static class CrossedLine {
        LineType type;
        Integer zeroes;
        int idx;

        CrossedLine(LineType type, int zeroes, int idx) {
            this.type = type;
            this.zeroes = zeroes;
            this.idx = idx;
        }

        @Override
        public String toString() {
            return "CrossedLine{" +
                    "type=" + type +
                    ", zeroes=" + zeroes +
                    ", idx=" + idx +
                    '}';
        }
    }

    private static void printTable(int[][] t) {
        for (int[] row : t) {
            System.out.println(Arrays.toString(row));
        }
    }
}
