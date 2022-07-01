package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SudokuColumn {

    public final SudokuSquare square;
    public final int pos;
    public final Set<Integer> values = new TreeSet<>();
    public final Set<CellKey> availableCellKeys = new LinkedHashSet<>();

    public SudokuColumn(SudokuSquare square, int pos) {
        this.square = square;
        this.pos = pos;
        initAvailableCells();
    }

    public boolean addValueAt(Integer value, int i) {
        boolean added = this.values.add(value);
        availableCellKeys.remove(new CellKey(i, pos));
        return added;
    }

    public Set<Integer> getValues() {
        return values;
    }

    public Set<Integer> getExcluded() {
        return SudokuUtils.toExcluded(values);
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

    public int getPos() {
        return pos;
    }

    private void initAvailableCells() {
        for(int i = 0; i < 9; i++) {
            availableCellKeys.add(new CellKey(i, pos));
        }
    }
}
