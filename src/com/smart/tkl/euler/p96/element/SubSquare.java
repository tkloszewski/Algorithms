package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.DuplicateValueException;
import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SubSquare extends UniqueValuesSudokuElement {

    public final int m,n;

    public final SudokuSquare square;
    public final SubRow[] subRows = new SubRow[3];
    public final SubColumn[] subColumns = new SubColumn[3];

    public final Set<Integer> values = new TreeSet<>();
    private final Set<Integer> trialValues = new TreeSet<>();
    public final Set<CellKey> availableCellKeys = new LinkedHashSet<>();
    private final Set<CellKey> removedTrialAvailableCellKeys = new LinkedHashSet<>();

    public SubSquare(int m, int n, SudokuSquare square) {
        this.m = m;
        this.n = n;
        this.square = square;
        initAvailableCells();
    }

    public void addValueAt(Integer value, int i, int j) {
        this.values.add(value);
        this.subRows[i % 3].addValue(value);
        this.subColumns[j % 3].addValue(value);
        this.availableCellKeys.remove(new CellKey(i, j));
    }

    public boolean validCandidateValueAt(Integer value, int i, int j) {
        boolean result = !this.values.contains(value);
        /*if(result) {
            Set<Integer> subRowExcluded = this.subRows[i % 3].getExcluded();
            Set<Integer> subColumnExcluded = this.subColumns[j % 3].getExcluded();
            if(subRowExcluded.contains(value) || subColumnExcluded.contains(value)){
                result = false;
            }
        }*/
        return result;
    }

    @Override
    public void tryValueAt(Integer value, int i, int j) {
        if(!validCandidateValueAt(value, i, j)) {
            throw new DuplicateValueException(new CellKey(i, j), value);
        }
        this.values.add(value);
        this.trialValues.add(value);
        this.subRows[i % 3].tryValueAt(value, i ,j);
        this.subColumns[j % 3].tryValueAt(value, i, j);
        this.availableCellKeys.remove(new CellKey(i, j));
        this.removedTrialAvailableCellKeys.add(new CellKey(i, j));
    }

    @Override
    public void rollbackTrial() {
        this.values.removeAll(trialValues);
        this.trialValues.clear();
        this.availableCellKeys.addAll(this.removedTrialAvailableCellKeys);
        this.removedTrialAvailableCellKeys.clear();
    }

    @Override
    public Set<Integer> getAvailableValues() {
        return SudokuUtils.toExcluded(this.values);
    }

    @Override
    public Set<CellKey> getAvailableCellKeys() {
        return this.availableCellKeys;
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
