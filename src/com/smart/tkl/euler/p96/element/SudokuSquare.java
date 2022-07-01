package com.smart.tkl.euler.p96.element;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SudokuSquare {

    public final int[][] originalSquare;
    public final SudokuCell[][] cells = new SudokuCell[9][9];
    public final SudokuRow[] rows = new SudokuRow[9];
    public final SudokuColumn[] columns = new SudokuColumn[9];
    public final SubSquare[][] subSquares = new SubSquare[3][3];

    public final Set<SudokuCell> availableCells = new LinkedHashSet<>();

    public SudokuSquare(int[][] originalSquare) {
        this.originalSquare = originalSquare;
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
            for(int j = 0; j < 9; j++) {
                int value = originalSquare[i][j];
                SubSquare subSquare = subSquares[i / 3][j / 3];
                cells[i][j] = new SudokuCell(i, j, subSquare, subSquare.subRows[i % 3], subSquare.subColumns[j % 3], rows[i], columns[j]);
                if(value == 0) {
                   availableCells.add(cells[i][j]);
                }
                else {
                    cells[i][j].value = value;
                    rows[i].addValueAt(value, j);
                    columns[j].addValueAt(value, i);
                    subSquare.addValueAt(value, i, j);
                }
            }
        }
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
}
