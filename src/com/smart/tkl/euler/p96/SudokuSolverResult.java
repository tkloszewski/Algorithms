package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.Set;

public class SudokuSolverResult {

    private final SudokuSquare square;
    private final Set<SudokuCell> resolvedCells;

    public SudokuSolverResult(SudokuSquare square, Set<SudokuCell> resolvedCells) {
        this.square = square;
        this.resolvedCells = resolvedCells;
    }

    @Override
    public String toString() {
        return "SudokuSolverResult{" +
                "square=" + square +
                ", resolvedCells=" + resolvedCells +
                '}';
    }

    public SudokuSquare getSquare() {
        return square;
    }

    public Set<SudokuCell> getResolvedCells() {
        return resolvedCells;
    }
}
