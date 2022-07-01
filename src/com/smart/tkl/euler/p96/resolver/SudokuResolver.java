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

    protected void cellValueSet(SudokuCell cell, Integer value) {
        sudokuSquare.setValue(cell, value);
    }
}
