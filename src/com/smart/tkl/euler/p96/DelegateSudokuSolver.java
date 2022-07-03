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

    private static final int[][] evil3 = {
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 7, 2, 0, 0},
            {5, 0, 8, 9, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 9, 0, 0, 5, 0},
            {4, 0, 6, 5, 0, 0, 0, 0, 8},
            {3, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 0, 6, 0, 0, 0, 0, 0},
            {6, 0, 1, 0, 2, 0, 4, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 1},
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

    private static final int[][] inhuman1 = {
            {0, 0, 0, 7, 0, 0, 0, 4, 0},
            {1, 0, 9, 0, 0, 0, 6, 0, 0},
            {7, 0, 2, 0, 0, 0, 0, 0, 0},
            {0, 6, 4, 8, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 5, 7, 0, 0},
            {0, 0, 7, 0, 0, 0, 8, 0, 0},
            {0, 0, 0, 0, 3, 8, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 0, 8, 3},
            {0, 0, 8, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] insane1 = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 8, 7, 0, 0, 0},
            {0, 0, 0, 0, 0, 9, 4, 7, 0},
            {0, 4, 0, 0, 0, 0, 0, 0, 2},
            {6, 2, 7, 4, 1, 3, 9, 5, 8},
            {0, 8, 0, 0, 0, 2, 0, 0, 5},
            {0, 0, 0, 1, 5, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 0, 1, 0, 0},
    };

    private static final int[][] evil4 = {
            {0, 0, 0, 0, 0, 0, 5, 9, 1},
            {6, 7, 0, 5, 8, 1, 4, 0, 3},
            {2, 0, 0, 9, 3, 0, 8, 6, 7},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 3, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 2, 0, 4},
            {4, 9, 0, 0, 0, 0, 1, 0, 6},
            {0, 0, 0, 4, 0, 0, 0, 0, 0},
    };

    private static final int[][] inhuman2 = {
            {0, 0, 0, 7, 0, 0, 0, 4, 0},
            {1, 0, 9, 0, 0, 0, 6, 0, 0},
            {7, 0, 2, 0, 0, 0, 0, 0, 0},
            {0, 6, 4, 8, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 5, 7, 0, 0},
            {0, 0, 7, 0, 0, 0, 8, 0, 0},
            {0, 0, 0, 0, 3, 8, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 0, 8, 3},
            {0, 0, 8, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] cells_17_1 = {
            {0, 0, 0, 7, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 3, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 6},
            {0, 0, 0, 5, 0, 9, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 4, 1, 8},
            {0, 0, 0, 0, 8, 1, 0, 0, 0},
            {0, 0, 2, 0, 0, 0, 0, 5, 0},
            {0, 4, 0, 0, 0, 0, 3, 0, 0},
    };

    private static final int[][] artoInkala = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0},
    };


    private static final List<NamedSudokuSquare> namedSquares = List.of(
            /*new NamedSudokuSquare(evil2, "evil2"),
            new NamedSudokuSquare(evil3, "evil3"),
            new NamedSudokuSquare(evil4, "evil4"),
            new NamedSudokuSquare(toughest, "toughest"),
            new NamedSudokuSquare(extreme1, "extreme"),
            new NamedSudokuSquare(automorphic, "automorphic"),
            new NamedSudokuSquare(inhuman1, "inhuman1"),
            new NamedSudokuSquare(insane1, "insane1")*/
            //new NamedSudokuSquare(inhuman2, "inhuman2"),
            new NamedSudokuSquare(cells_17_1, "cells_17_1"),
            new NamedSudokuSquare(artoInkala, "artoInkala")

    );

    private final DeductionSudokuSolver deductionSolver = new DeductionSudokuSolver();
    private final DFSTrialAndErrorSudokuSolver dfsTrialAndErrorSolver = new DFSTrialAndErrorSudokuSolver(deductionSolver);

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
            SudokuSolverResult trialAndErrorResult = dfsTrialAndErrorSolver.solve(sudokuSquare);
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
