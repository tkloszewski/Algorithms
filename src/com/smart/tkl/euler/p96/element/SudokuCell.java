package com.smart.tkl.euler.p96.element;

import java.util.Objects;
import java.util.Set;

public class SudokuCell {

    public final CellKey key;
    public final SubSquare subSquare;
    public final SubRow subRow;
    public final SubColumn subColumn;
    public final SudokuRow row;
    public final SudokuColumn column;
    public Set<Integer> candidates;

    public int value = 0;

    public static SudokuCell of(int i, int j) {
        return new SudokuCell(i, j);
    }

    public SudokuCell(int i, int j, SubSquare subSquare, SubRow subRow, SubColumn subColumn, SudokuRow row, SudokuColumn column) {
        this.key = new CellKey(i, j);
        this.subSquare = subSquare;
        this.subRow = subRow;
        this.subColumn = subColumn;
        this.row = row;
        this.column = column;
    }

    private SudokuCell(int i, int j) {
        this(i, j, null, null, null, null, null);
    }

    public int getI() {
        return key.i;
    }

    public int getJ() {
        return key.j;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuCell cell = (SudokuCell) o;
        return key.equals(cell.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public String toDetailedString() {
        return "[" + key.i + "][" + key.j + "]" + "=" + value;
    }
}
