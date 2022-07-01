package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuSquare;

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

    private final DeductionSudokuSolver deductionSolver = new DeductionSudokuSolver();
    private final TrialAndErrorSudokuSolver trialAndErrorSolver = new TrialAndErrorSudokuSolver(deductionSolver);

    public static void main(String[] args) {
        SudokuSolver sudokuSolver = new DelegateSudokuSolver();
        SudokuSolverResult solverResult = sudokuSolver.solve(new SudokuSquare(expert1));
        System.out.println("Solved sudoku expert: \n" + solverResult.getSquare());
        solverResult = sudokuSolver.solve(new SudokuSquare(evil1));
        System.out.println("Solved sudoku evil: \n" + solverResult.getSquare());
        solverResult = sudokuSolver.solve(new SudokuSquare(wikiExample));
        System.out.println("Solved sudoku wiki example: \n" + solverResult.getSquare());

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
}
