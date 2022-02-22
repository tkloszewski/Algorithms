package com.smart.tkl.euler.p345;

public class MatrixMinMaxSumTest {

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

    private static final int[][] MATRIX_1 = {
            {2500, 4000, 3500},
            {4000, 6000, 3500},
            {2000, 4000, 2500}
    };

    private static final int[][] MATRIX_2 = {
            {1500, 4000, 4500},
            {2000, 6000, 3500},
            {2000, 4000, 2500}
    };

    private static final int[][] MATRIX_3 = {
            {4,  2, 5,  7 },
            {8,  3, 10, 8 },
            {12, 5, 4,  5 },
            {6,  3, 7,  14}
    };

    private static final int[][] MATRIX_4 = {
            { 500,  500,  500},
            { 1000, 1000, 1000},
            { 2000, 2000, 2000}
    };

    private static final int[][] MATRIX_5 = {
            {17, 15, 9,  5,  12},
            {16, 16, 10, 5,  10},
            {12, 15, 14, 11, 5 },
            {4,  8,  14, 17, 13},
            {13, 9,  8,  12, 17}
    };

    private static final int[][] MATRIX_6 = {
            { 10,    10,    10},
            { 10,    10,    10},
            { 10,    10,    10}
    };

    public static void main(String[] args) {
        MatrixMinMaxSumTest matrixMinMaxSumTest = new MatrixMinMaxSumTest();
        matrixMinMaxSumTest.testMatrix1MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix2MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix3MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix4MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix5MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix6MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix7MaxSumHungarian();
        matrixMinMaxSumTest.testMatrix8MaxSumHungarian();
    }

    public void testMatrix1MaxSumHungarian() {
       MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_1);
       int result = matrixMinMaxSum.calcMaxSumHungarian();
       assert result == 11500;
    }

    public void testMatrix2MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_2);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 12500;
    }

    public void testMatrix3MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_3);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 38;
    }

    public void testMatrix4MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_4);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 3500;
    }

    public void testMatrix5MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_5);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 81;
    }

    public void testMatrix6MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_5);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 30;
    }

    public void testMatrix7MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(TEST_MATRIX);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 3315;
    }

    public void testMatrix8MaxSumHungarian() {
        MatrixMinMaxSum matrixMinMaxSum = new MatrixMinMaxSum(MATRIX_345);
        int result = matrixMinMaxSum.calcMaxSumHungarian();
        assert result == 13938;
    }
}
