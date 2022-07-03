package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.DuplicateValueException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SudokuSquare extends SudokuElement {

    public final int[][] originalSquare;
    public final SudokuCell[][] cells = new SudokuCell[9][9];
    public final SudokuRow[] rows = new SudokuRow[9];
    public final SudokuColumn[] columns = new SudokuColumn[9];
    public final SubSquare[][] subSquares = new SubSquare[3][3];

    public final Set<CellKey> availableCellKeys = new LinkedHashSet<>();
    private final Set<CellKey> removedTrialAvailableCellKeys = new LinkedHashSet<>();

    private boolean trialMode;

    public SudokuSquare(int[][] originalSquare) {
        this.originalSquare = originalSquare;
        initSquare(originalSquare);
    }

    public SudokuCell getCell(CellKey cellKey) {
        return this.cells[cellKey.i][cellKey.j];
    }

    public boolean validCandidateValueAt(Integer value, int i, int j) {
        SudokuRow row = this.rows[i];
        SudokuColumn column = this.columns[j];
        SubSquare subSquare = this.subSquares[i / 3][j / 3];

        boolean valid = row.validCandidateValue(value);
        valid = valid && column.validCandidateValue(value);
        return valid && subSquare.validCandidateValueAt(value, i, j);
    }

    public void setValue(SudokuCell cell, Integer value) {
        if(isTrialMode()) {
            tryValueAt(value, cell.key.i, cell.key.j);
        }
        else {
            cell.setValue(value);
            cell.subSquare.addValueAt(value, cell.key.i, cell.key.j);
            cell.row.addValueAt(value, cell.key.j);
            cell.column.addValueAt(value, cell.key.i);
            this.availableCellKeys.remove(cell.key);
        }
    }

    @Override
    public void tryValueAt(Integer value, int i, int j) {
        SudokuCell cell = getCell(new CellKey(i, j));
        this.availableCellKeys.remove(cell.key);
        this.removedTrialAvailableCellKeys.add(cell.key);
        cell.tryValueAt(value, -1, -1);
        cell.subSquare.tryValueAt(value, cell.key.i, cell.key.j);
        cell.row.tryValueAt(value, cell.key.i, cell.key.j);
        cell.column.tryValueAt(value, cell.key.i, cell.key.j);

    }

    @Override
    public void rollbackTrial() {
        for(SubSquare[] subSquareRow : subSquares) {
            for(SubSquare subSquare : subSquareRow) {
                subSquare.rollbackTrial();
            }
        }
        for(SudokuRow row : rows) {
            row.rollbackTrial();
        }
        for(SudokuColumn column : columns) {
            column.rollbackTrial();
        }
        for(CellKey removedTrialAvailableKey : removedTrialAvailableCellKeys) {
            SudokuCell cell = getCell(removedTrialAvailableKey);
            cell.rollbackTrial();
        }
        this.availableCellKeys.addAll(this.removedTrialAvailableCellKeys);
        this.removedTrialAvailableCellKeys.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(SudokuCell[] cellRow : cells) {
            sb.append(Arrays.toString(cellRow));
            sb.append("\n");
        }
        return sb.toString();
    }

    public SudokuSquare toTrialModeSquare() {
        int[][] square = new int[9][9];
        for(SudokuCell[] cellRow : cells) {
            for(SudokuCell cell : cellRow) {
                square[cell.key.i][cell.key.j] = cell.getValue();
            }
        }
        SudokuSquare trialModeSquare = new SudokuSquare(square);
        trialModeSquare.setTrialMode(true);
        return trialModeSquare;
    }

    public boolean isTrialMode() {
        return trialMode;
    }

    public void setTrialMode(boolean trialMode) {
        this.trialMode = trialMode;
    }

    private void initSquare(int[][] originalSquare) {
        for(int i = 0; i < 9; i++) {
            rows[i] = new SudokuRow(this, i);
            columns[i] = new SudokuColumn(this, i);
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                subSquares[i][j] = new SubSquare(i, j, this);
                for(int k = 0; k < 3; k++) {
                    subSquares[i][j].subRows[k] = new SubRow(k, rows[i * 3 + k], subSquares[i][j]);
                    subSquares[i][j].subColumns[k] = new SubColumn(k, columns[j * 3 + k], subSquares[i][j]);
                }
            }
        }
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SubSquare subSquare = subSquares[i / 3][j / 3];
                cells[i][j] = new SudokuCell(i, j, subSquare, subSquare.subRows[i % 3], subSquare.subColumns[j % 3], rows[i], columns[j]);
                availableCellKeys.add(cells[i][j].key);
            }
        }

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int value = originalSquare[i][j];
                if(value != 0) {
                    if(!validCandidateValueAt(value, i, j)) {
                        throw new DuplicateValueException(new CellKey(i, j), value);
                    }
                    setValue(cells[i][j], value);
                }
            }
        }
    }
}
