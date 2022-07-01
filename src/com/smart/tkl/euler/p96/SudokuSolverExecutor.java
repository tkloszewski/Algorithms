package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolverExecutor {

    private static final int[][] empty = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

    private static final int[][] sudoku1 = {
            {0, 0, 3, 0, 2, 0, 6, 0, 0},
            {9, 0, 0, 3, 0, 5, 0, 0, 1},
            {0, 0, 1, 8, 0, 6, 4, 0, 0},
            {0, 0, 8, 1, 0, 2, 9, 0, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 8},
            {0, 0, 6, 7, 0, 8, 2, 0, 0},
            {0, 0, 2, 6, 0, 9, 5, 0, 0},
            {8, 0, 0, 2, 0, 3, 0, 0, 9},
            {0, 0, 5, 0, 1, 0, 3, 0, 0},
    };

    private static final int[][] easy1 = {
            {0, 0, 0, 9, 0, 0, 0, 0, 3},
            {3, 4, 0, 0, 1, 0, 6, 0, 0},
            {0, 5, 6, 4, 0, 0, 0, 0, 8},
            {1, 3, 2, 6, 5, 8, 0, 0, 0},
            {0, 9, 0, 7, 4, 3, 0, 6, 0},
            {0, 6, 4, 2, 9, 1, 8, 0, 0},
            {0, 2, 0, 0, 8, 0, 3, 1, 9},
            {0, 0, 0, 0, 2, 0, 0, 8, 0},
            {0, 8, 0, 0, 0, 9, 4, 5, 0},
    };

    private static final int[][] medium1 = {
            {0, 0, 0, 7, 6, 3, 4, 1, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 6},
            {3, 0, 6, 0, 0, 0, 0, 0, 7},
            {0, 2, 0, 0, 0, 0, 7, 5, 0},
            {0, 5, 4, 2, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 5, 8, 2, 0, 1},
            {0, 6, 0, 0, 8, 0, 0, 0, 0},
            {0, 3, 0, 9, 0, 6, 0, 2, 5},
            {0, 0, 7, 0, 2, 4, 6, 0, 0},
    };

    private static final int[][] hard1 = {
            {6, 0, 7, 0, 3, 1, 0, 0, 4},
            {0, 0, 3, 0, 4, 0, 2, 0, 0},
            {0, 9, 0, 8, 0, 0, 0, 0, 0},
            {4, 0, 0, 9, 0, 0, 0, 7, 8},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 2, 0, 0, 4, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 6, 0, 0, 0, 0, 0, 4, 0},
            {0, 4, 8, 7, 0, 0, 1, 0, 6},
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

    private static final int[][] expert12 = {
            {0, 0, 0, 0, 0, 3, 0, 0, 0},
            {2, 3, 0, 6, 0, 0, 0, 0, 0},
            {8, 0, 0, 0, 0, 2, 0, 1, 0},
            {3, 0, 5, 7, 9, 1, 0, 0, 4},
            {0, 6, 0, 8, 3, 4, 0, 7, 0},
            {0, 0, 1, 5, 2, 6, 8, 3, 0},
            {0, 0, 0, 0, 6, 5, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 3, 8, 2},
            {0, 0, 0, 0, 4, 0, 0, 0, 6},
    };

    private static final int[][] grid50 = {
            {3, 0, 0, 2, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 7, 0, 0, 0},
            {7, 0, 6, 0, 3, 0, 5, 0, 0},
            {0, 7, 0, 0, 0, 9, 0, 8, 0},
            {9, 0, 0, 0, 2, 0, 0, 0, 4},
            {0, 1, 0, 8, 0, 0, 0, 5, 0},
            {0, 0, 9, 0, 4, 0, 3, 0, 1},
            {0, 0, 0, 7, 0, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 8, 0, 0, 6}
    };

    private static final String FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p96\\p096_sudoku.txt";


    public static void main(String[] args) throws Exception {
        SudokuReader sudokuReader = new SudokuReader(FILE_PATH);
        SudokuSolver sudokuSolver = new DelegateSudokuSolver();
        List<SudokuSquare> sudokuSquares = sudokuReader.read();
        List<SudokuSquare> unsolvedSquares = new ArrayList<>();

        int squareNum = 1;
        int sumOfAllTopLeftNumbers = 0;
        for(SudokuSquare sudokuSquare : sudokuSquares) {
            System.out.println("Built and read square : " + squareNum + " \n" + sudokuSquare);
            SudokuSolverResult solverResult = sudokuSolver.solve(sudokuSquare);
            System.out.println("Solved sudoku: " + squareNum + " \n" + solverResult.getSquare());
            if(!solverResult.isSolved()) {
               unsolvedSquares.add(solverResult.getSquare());
            }
            else {
               int topLeftNumber = solverResult.getTopLeft3DigitNumber();
               System.out.println("Top left number: " + topLeftNumber);
               sumOfAllTopLeftNumbers += topLeftNumber;
            }
            squareNum++;
        }

        System.out.println("Unsolved sudoku squares: " + unsolvedSquares.size());
        System.out.println("Sum of all top left numbers: " + sumOfAllTopLeftNumbers);

        /*try {
            SudokuSquare sudokuSquare = new SudokuSquare(grid50);
            //sudokuSquare.setTrialMode(true);
            System.out.println("Built square: \n" + sudokuSquare);
            SudokuSolver sudokuSolver = new DelegateSolver();
            SudokuSolverResult solverResult = sudokuSolver.solve(sudokuSquare);
            System.out.println("Solved sudoku: \n" + solverResult.getSquare());

        } catch (DuplicateValueException e) {
            System.out.println("Duplicate value at: " + e.getCellKey() + " value: " + e.getValue());
        }*/
    }
}
