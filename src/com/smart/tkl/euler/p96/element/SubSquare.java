package com.smart.tkl.euler.p96.element;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SubSquare {

    public final int m,n;

    public final SudokuSquare square;
    public final Set<Integer> values = new TreeSet<>();
    public final SubRow[] subRows = new SubRow[3];
    public final SubColumn[] subColumns = new SubColumn[3];
    public final Set<CellKey> availableCellKeys = new LinkedHashSet<>();

    public SubSquare(int m, int n, SudokuSquare square) {
        this.m = m;
        this.n = n;
        this.square = square;
        initAvailableCells();
    }

    public boolean addValueAt(Integer value, int i, int j) {
        boolean result = true;
        boolean added = this.values.add(value);
        if(!added) {
           System.out.println("Duplicate added to subsquare: " + value + " at [" + i + "," + j + "]");
           result = false;
        }
        added = this.subRows[i % 3].addValue(value);
        if(!added) {
            System.out.println("Duplicate added to subrow: " + value + " at [" + i + "," + j + "]");
            System.out.println("Subrow values: " + this.subRows[i % 3]);
            result = false;
        }
        added = this.subColumns[j % 3].addValue(value);
        if(!added) {
            System.out.println("Duplicate added to subcolumn: " + value + " at [" + i + "," + j + "]");
            System.out.println("Subrow values: " + this.subColumns[j % 3]);
            result = false;
        }

        this.availableCellKeys.remove(new CellKey(i, j));

        return result;
    }

    public Set<Integer> getValues() {
        return values;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    private void initAvailableCells() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                availableCellKeys.add(new CellKey(3 * m + i, 3 * n + j));
            }
        }
    }
}
