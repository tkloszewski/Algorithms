package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.List;

public class DelegateSudokuSolver implements SudokuSolver {

    private static final int[][] wikiExample = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9},
    };

    private static final int[][] expert1 = {
            {0, 0, 0, 0, 0, 3, 0, 0, 0},
            {2, 3, 0, 6, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 2, 0, 1, 0},
            {0, 0, 5, 7, 9, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 7, 0},
            {0, 0, 1, 0, 0, 0, 8, 0, 0},
            {0, 0, 0, 0, 6, 5, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 3, 8, 2},
            {0, 0, 0, 0, 4, 0, 0, 0, 6},
    };

    private static final int[][] evil1 = {
            {5, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 7, 0, 0, 2, 0, 0},
            {4, 0, 9, 0, 1, 0, 0, 7, 0},
            {9, 0, 3, 0, 0, 4, 6, 0, 0},
            {0, 5, 0, 0, 0, 0, 0, 9, 0},
            {0, 2, 0, 0, 3, 0, 0, 0, 0},
            {7, 0, 1, 0, 4, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 6, 0, 0, 8},
            {0, 3, 0, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] evil2 = {
            {0, 0, 2, 0, 1, 7, 0, 4, 0},
            {0, 6, 0, 0, 5, 0, 0, 0, 2},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 5, 0, 0, 0, 0, 0},
            {0, 0, 6, 0, 2, 4, 0, 1, 0},
            {8, 0, 0, 0, 0, 0, 9, 0, 0},
            {0, 0, 0, 6, 0, 0, 0, 5, 0},
            {0, 9, 0, 0, 7, 5, 1, 0, 0},
            {0, 0, 7, 3, 0, 0, 0, 0, 0},
    };

    private static final int[][] extreme1 = {
            {0, 0, 0, 2, 1, 0, 0, 0, 0},
            {0, 0, 7, 3, 0, 0, 0, 0, 0},
            {0, 5, 8, 0, 0, 0, 0, 0, 0},
            {4, 3, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0, 0, 0, 7, 6},
            {0, 0, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 7, 3, 0, 0},
            {0, 0, 0, 0, 9, 8, 0, 0, 0},
    };

    private static final int[][] toughest = {
            {0, 0, 5, 3, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 0, 0, 2, 0},
            {0, 7, 0, 0, 1, 0, 5, 0, 0},
            {4, 0, 0, 0, 0, 5, 3, 0, 0},
            {0, 1, 0, 0, 7, 0, 0, 0, 6},
            {0, 0, 3, 2, 0, 0, 0, 8, 0},
            {0, 6, 0, 5, 0, 0, 0, 0, 9},
            {0, 0, 4, 0, 0, 0, 0, 3, 0},
            {0, 0, 0, 0, 0, 9, 7, 0, 0},
    };

    private static final int[][] automorphic = {
            {1, 0, 0, 2, 0, 0, 3, 0, 0},
            {2, 0, 0, 3, 0, 0, 4, 0, 0},
            {3, 0, 0, 4, 0, 0, 5, 0, 0},
            {4, 0, 0, 5, 0, 0, 6, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 0, 0, 4, 0, 0, 5},
            {0, 0, 4, 0, 0, 5, 0, 0, 6},
            {0, 0, 5, 0, 0, 6, 0, 0, 7},
            {0, 0, 6, 0, 0, 7, 0, 0, 8},
    };

    private static final List<NamedSudokuSquare> namedSquares = List.of(
            new NamedSudokuSquare(evil2, "evil2"),
            new NamedSudokuSquare(toughest, "toughest"),
            new NamedSudokuSquare(extreme1, "extreme"),
            new NamedSudokuSquare(automorphic, "automorphic")
    );

    private final DeductionSudokuSolver deductionSolver = new DeductionSudokuSolver();
    private final TrialAndErrorSudokuSolver trialAndErrorSolver = new TrialAndErrorSudokuSolver(deductionSolver);

    public static void main(String[] args) {
        for (NamedSudokuSquare namedSquare : namedSquares) {
            solveNamedSudoku(namedSquare);
        }
    }

    private static void solveNamedSudoku(NamedSudokuSquare namedSudokuSquare) {
        SudokuSolver sudokuSolver = new DelegateSudokuSolver();
        SudokuSquare sudokuSquare = new SudokuSquare(namedSudokuSquare.square);
        SudokuSolverResult result = sudokuSolver.solve(sudokuSquare);
        System.out.println("Solved " + namedSudokuSquare.name + " sudoku with " + result.getTrialsNumber() + " trials: \n" + result.getSquare());
    }

    public SudokuSolverResult solve(SudokuSquare sudokuSquare) {
        SudokuSolverResult result = deductionSolver.solve(sudokuSquare);
        if(!result.isSolved()) {
            System.out.println("Partially solved: \n" + result.getSquare());
            SudokuSolverResult trialAndErrorResult = trialAndErrorSolver.solve(sudokuSquare);
            if(trialAndErrorResult.isSolved()) {
                return trialAndErrorResult;
            }
        }
        return result;
    }

    private static class NamedSudokuSquare {
        final int[][] square;
        final String name;

        public NamedSudokuSquare(int[][] square, String name) {
            this.square = square;
            this.name = name;
        }
    }
}
