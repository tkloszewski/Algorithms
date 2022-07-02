package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.*;
import com.smart.tkl.euler.p96.permutation.CellValue;
import com.smart.tkl.euler.p96.permutation.CellValuePermutation;
import com.smart.tkl.euler.p96.permutation.ElementPermutationGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TrialAndErrorSudokuSolver implements SudokuSolver {

    private final DeductionSudokuSolver deductionSolver;

    public TrialAndErrorSudokuSolver(DeductionSudokuSolver deductionSolver) {
        this.deductionSolver = deductionSolver;
    }

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
                        if(result != null && result.isSolved()) {
                            System.out.println("Solved with number of tries: " + numOfTrials);
                            return new SudokuSolverResult(result.getSquare(), result.getResolvedCells(), numOfTrials);
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
