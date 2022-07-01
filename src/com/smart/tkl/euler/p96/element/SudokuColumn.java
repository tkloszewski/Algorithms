package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.DuplicateValueException;
import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SudokuColumn extends UniqueValuesSudokuElement {

    public final SudokuSquare square;
    public final int pos;

    private final Set<Integer> values = new TreeSet<>();
    private final Set<Integer> trialValues = new TreeSet<>();

    private final Set<CellKey> availableCellKeys = new LinkedHashSet<>();
    private final Set<CellKey> removedTrialAvailableCellKeys = new LinkedHashSet<>();

    public SudokuColumn(SudokuSquare square, int pos) {
        this.square = square;
        this.pos = pos;
        initAvailableCells();
    }

    public void addValueAt(Integer value, int i) {
        this.values.add(value);
        this.availableCellKeys.remove(new CellKey(i, pos));
    }

    @Override
    public void tryValueAt(Integer value, int i, int j) {
        if(!validCandidateValue(value)) {
            throw new DuplicateValueException(new CellKey(i, j), value);
        }
        this.values.add(value);
        this.trialValues.add(value);
        this.availableCellKeys.remove(new CellKey(i, pos));
        this.removedTrialAvailableCellKeys.add(new CellKey(i, pos));
    }

    @Override
    public void rollbackTrial() {
        if (trialValues.size() > 0) {
            this.values.removeAll(trialValues);
            this.trialValues.clear();
        }
        if (removedTrialAvailableCellKeys.size() > 0) {
            this.availableCellKeys.addAll(this.removedTrialAvailableCellKeys);
            this.removedTrialAvailableCellKeys.clear();
        }
    }

    public boolean validCandidateValue(Integer value) {
        return !this.values.contains(value);
    }

    public Set<Integer> getExcludedForRow(int i) {
        Set<Integer> result = new TreeSet<>();

        int subSquareColPos = pos / 3;

        SubSquare currentSubSquare = square.subSquares[i / 3][subSquareColPos];
        SubSquare subSquare1 = square.subSquares[i / 3][(subSquareColPos + 1) % 3];
        SubSquare subSquare2 = square.subSquares[i / 3][(subSquareColPos + 2) % 3];
        SubRow subRow1 = subSquare1.subRows[i % 3];
        SubRow subRow2 = subSquare2.subRows[i % 3];

        result.addAll(currentSubSquare.values);
        result.addAll(subRow1.getGuaranteedValues());
        result.addAll(subRow2.getGuaranteedValues());
        result.removeAll(this.values);
        return result;
    }

    @Override
    public Set<Integer> getAvailableValues() {
        return SudokuUtils.toExcluded(this.values);
    }

    public int getPos() {
        return pos;
    }

    public Set<Integer> getValues() {
        return values;
    }

    public Set<CellKey> getAvailableCellKeys() {
        return availableCellKeys;
    }

    private void initAvailableCells() {
        for(int i = 0; i < 9; i++) {
            availableCellKeys.add(new CellKey(i, pos));
        }
    }
}
