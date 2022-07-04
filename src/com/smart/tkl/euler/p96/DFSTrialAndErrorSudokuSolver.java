package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.*;
import com.smart.tkl.euler.p96.permutation.CellValue;
import com.smart.tkl.euler.p96.permutation.CellValuePermutation;
import com.smart.tkl.euler.p96.permutation.ElementPermutationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DFSTrialAndErrorSudokuSolver implements SudokuSolver {

    private static int DEFAULT_MAX_DEPTH_LEVEL = 3;

    private final int maxDepthLevel;
    private final SudokuSolver deductionSolver;

    public DFSTrialAndErrorSudokuSolver(SudokuSolver deductionSolver) {
        this.deductionSolver = deductionSolver;
        this.maxDepthLevel = DEFAULT_MAX_DEPTH_LEVEL;
    }

    public DFSTrialAndErrorSudokuSolver(int maxDepthLevel, SudokuSolver deductionSolver) {
        this.maxDepthLevel = maxDepthLevel;
        this.deductionSolver = deductionSolver;
    }

    @Override
    public SudokuSolverResult solve(SudokuSquare sudokuSquare) {
        List<ElementPermutationGenerator> permutationGenerators = createPermutationGenerators(sudokuSquare);
        Collections.sort(permutationGenerators);

        sudokuSquare.setTrialMode(true);

        int numOfTrials = 0;
        for(ElementPermutationGenerator permutationGenerator : permutationGenerators) {
            while (permutationGenerator.hasNextPermutation()) {
                CellValuePermutation permutation = permutationGenerator.getNextPermutation();
                if(isValidPermutation(sudokuSquare, permutation)) {
                    numOfTrials++;
                    SudokuSolverResult result = null;
                    try {
                        try {
                            applyPermutation(sudokuSquare, permutation);
                            result = deductionSolver.solve(sudokuSquare);
                        } catch (DuplicateValueException e) {
                            System.out.println("Duplicate value at: " + e.getCellKey() + " value: " + e.getValue());
                        }
                        if(result != null) {
                            if(result.isSolved()) {
                                System.out.println("Solved with number of at first level tries: " + numOfTrials);
                                return new SudokuSolverResult(result.getSquare(), result.getResolvedCells(), numOfTrials);
                            }
                            else {
                                System.out.println("Trying DFS for sudoku: \n" + sudokuSquare);
                                SudokuSquare newSquare = sudokuSquare.toTrialModeSquare();
                                result = trySolveByTheDepthFirstSearch(newSquare, numOfTrials, 1);
                                if(result.isSolved()) {
                                    return result;
                                }
                                else {
                                    numOfTrials += result.getTrialsNumber();
                                }
                            }
                        }
                    } finally {
                        // rollback trial values if there was a duplicate or sudoku was not solved
                        if (result == null || !result.isSolved()) {
                            sudokuSquare.rollbackTrial();
                        }
                    }
                }
            }
        }
        return new SudokuSolverResult(sudokuSquare, Set.of());
    }

    private SudokuSolverResult trySolveByTheDepthFirstSearch(SudokuSquare sudokuSquare, int currentNumOfTrails,  int level) {
        List<ElementPermutationGenerator> permutationGenerators = createPermutationGenerators(sudokuSquare);
        ElementPermutationGenerator permutationGenerator = permutationGenerators.get(0);
        int numOfTrials = currentNumOfTrails;
        while (permutationGenerator.hasNextPermutation()) {
            CellValuePermutation permutation = permutationGenerator.getNextPermutation();
            if(isValidPermutation(sudokuSquare, permutation)) {
                numOfTrials++;
                SudokuSolverResult result = null;

                try {
                    applyPermutation(sudokuSquare, permutation);
                    result = deductionSolver.solve(sudokuSquare);
                    if(result != null) {
                        if(result.isSolved()) {
                            System.out.println("Solved with number of tries: " + numOfTrials);
                            return new SudokuSolverResult(result.getSquare(), result.getResolvedCells(), numOfTrials);
                        }
                        else {
                            if(level < maxDepthLevel) {
                                System.out.println("Trying deeper level " + level + " for sudoku square: \n" + sudokuSquare);
                                SudokuSquare newSquare = sudokuSquare.toTrialModeSquare();
                                result = trySolveByTheDepthFirstSearch(newSquare, numOfTrials, level + 1);
                                if(result.isSolved()) {
                                    return result;
                                }
                                else {
                                    numOfTrials += result.getTrialsNumber();
                                }
                            }
                        }
                    }

                } catch (DuplicateValueException e) {
                    sudokuSquare.rollbackTrial();
                } finally {
                    if(result != null && !result.isSolved()) {
                        sudokuSquare.rollbackTrial();
                    }
                }
            }
        }

        return new SudokuSolverResult(sudokuSquare, Set.of());
    }

    private boolean isValidPermutation(SudokuSquare sudokuSquare, CellValuePermutation permutation) {
        for(CellValue cellValue : permutation.getCellValues()) {
            CellKey cellKey = cellValue.getKey();
            if(!sudokuSquare.validCandidateValueAt(cellValue.getValue(), cellKey.i, cellKey.j)) {
                return false;
            }
        }
        return true;
    }

    private void applyPermutation(SudokuSquare sudokuSquare, CellValuePermutation permutation) {
        for(CellValue cellValue : permutation.getCellValues()) {
            CellKey cellKey = cellValue.getKey();
            sudokuSquare.tryValueAt(cellValue.getValue(), cellKey.i, cellKey.j);
        }
    }

    private List<ElementPermutationGenerator> createPermutationGenerators(SudokuSquare sudokuSquare) {
        List<ElementPermutationGenerator> result = new ArrayList<>();
        for(SubSquare[] subSquareRow : sudokuSquare.subSquares) {
            for(SubSquare subSquare : subSquareRow) {
                if (subSquare.getAvailableValues().size() > 0) {
                    result.add(new ElementPermutationGenerator(subSquare));
                }
            }
        }
        for(SudokuRow row : sudokuSquare.rows) {
            if (row.getAvailableValues().size() > 0) {
                result.add(new ElementPermutationGenerator(row));
            }
        }
        for(SudokuColumn column : sudokuSquare.columns) {
            if (column.getAvailableValues().size() > 0) {
                result.add(new ElementPermutationGenerator(column));
            }
        }
        return result;
    }
}
