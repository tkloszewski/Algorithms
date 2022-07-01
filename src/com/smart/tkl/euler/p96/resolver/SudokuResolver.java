package com.smart.tkl.euler.p96.resolver;

import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.Set;

public abstract class SudokuResolver {

    protected SudokuSquare sudokuSquare;

    public SudokuResolver(SudokuSquare square) {
        this.sudokuSquare = square;
    }

    public abstract Set<SudokuCell> resolveValues();

    public abstract ResolverType getType();

    protected void cellValueSet(SudokuCell cell) {
        cell.subSquare.addValueAt(cell.value, cell.key.i, cell.key.j);
        cell.row.addValueAt(cell.value, cell.key.j);
        cell.column.addValueAt(cell.value, cell.key.i);
        sudokuSquare.availableCells.remove(cell);
    }
}
