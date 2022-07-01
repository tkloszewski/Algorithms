package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.DuplicateValueException;
import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SudokuRow extends UniqueValuesSudokuElement {

    public final SudokuSquare square;
    public final int pos;

    private final Set<Integer> values = new TreeSet<>();
    private final Set<Integer> trialValues = new TreeSet<>();

    private final Set<CellKey> availableCellKeys = new LinkedHashSet<>();
    private final Set<CellKey> removedTrialAvailableCellKeys = new LinkedHashSet<>();

    public SudokuRow(SudokuSquare square, int pos) {
        this.square = square;
        this.pos = pos;
        initAvailableCells();
    }

    public void addValueAt(Integer value, int j) {
        this.values.add(value);
        this.availableCellKeys.remove(new CellKey(pos, j));
    }

    @Override
    public void tryValueAt(Integer value, int i, int j) {
        if(!validCandidateValue(value)) {
            throw new DuplicateValueException(new CellKey(i, j), value);
        }
        this.values.add(value);
        this.trialValues.add(value);
        this.availableCellKeys.remove(new CellKey(pos, j));
        this.removedTrialAvailableCellKeys.add(new CellKey(pos, j));
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

    public Set<Integer> getValues() {
        return values;
    }

    public Set<Integer> getExcluded() {
        return SudokuUtils.toExcluded(values);
    }

    public Set<Integer> getExcludedForColumn(int j) {
        Set<Integer> result = new TreeSet<>();

        int subSquareRowPos = pos / 3;

        SubSquare currentSubSquare = square.subSquares[subSquareRowPos][j / 3];
        SubSquare subSquare1 = square.subSquares[(subSquareRowPos + 1) % 3][j / 3];
        SubSquare subSquare2 = square.subSquares[(subSquareRowPos + 2) % 3][j / 3];
        SubColumn subColumn1 = subSquare1.subColumns[j % 3];
        SubColumn subColumn2 = subSquare2.subColumns[j % 3];

        Set<Integer> guaranteedValues1 = subColumn1.getGuaranteedValues();
        Set<Integer> guaranteedValues2 = subColumn2.getGuaranteedValues();

        result.addAll(currentSubSquare.values);
        result.addAll(guaranteedValues1);
        result.addAll(guaranteedValues2);
        result.removeAll(this.values);
        return result;
    }

    public int getPos() {
        return pos;
    }

    public boolean validCandidateValue(Integer value) {
        return !this.values.contains(value);
    }

    public Set<CellKey> getAvailableCellKeys() {
        return availableCellKeys;
    }

    private void initAvailableCells() {
        for(int j = 0; j < 9; j++) {
            availableCellKeys.add(new CellKey(pos, j));
        }
    }
}
