package com.smart.tkl.euler.p96.element;

import java.util.Set;

public abstract class UniqueValuesSudokuElement extends SudokuElement {

    public abstract Set<Integer> getAvailableValues();

    public abstract Set<CellKey> getAvailableCellKeys();
}
