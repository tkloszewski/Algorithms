package com.smart.tkl.euler.p96.element;

public abstract class SudokuElement {

    public abstract void tryValueAt(Integer value, int i, int j);
    public abstract void rollbackTrial();
}
