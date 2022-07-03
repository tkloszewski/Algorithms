package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.CellKey;
import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.Set;

public class SudokuSolverResult {

    private final SudokuSquare square;
    private final Set<SudokuCell> resolvedCells;
    private final boolean solved;

    private int trialsNumber = 0;
    private Integer topLeft3DigitNumber;

    public SudokuSolverResult(SudokuSquare square, Set<SudokuCell> resolvedCells, int trialsNumber) {
        this(square, resolvedCells);
        this.trialsNumber = trialsNumber;
    }

    public SudokuSolverResult(SudokuSquare square, Set<SudokuCell> resolvedCells) {
        this.square = square;
        this.resolvedCells = resolvedCells;
        this.solved = square.availableCellKeys.isEmpty();
        this.topLeft3DigitNumber = this.solved ? resolveNumber() : null;
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

    public boolean isSolved() {
        return solved;
    }

    public Integer getTopLeft3DigitNumber() {
        return topLeft3DigitNumber;
    }

    public int getTrialsNumber() {
        return trialsNumber;
    }

    public void setTrialsNumber(int trialsNumber) {
        this.trialsNumber = trialsNumber;
    }

    private Integer resolveNumber() {
        StringBuilder sb = new StringBuilder();
        sb.append(square.getCell(new CellKey(0,0)).getValue());
        sb.append(square.getCell(new CellKey(0,1)).getValue());
        sb.append(square.getCell(new CellKey(0,2)).getValue());
        return Integer.parseInt(sb.toString());
    }
}
