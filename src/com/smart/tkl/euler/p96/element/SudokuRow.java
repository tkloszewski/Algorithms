package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SudokuRow {

    public final SudokuSquare square;
    public final int pos;
    public final Set<Integer> values = new TreeSet<>();
    public final Set<CellKey> availableCellKeys = new LinkedHashSet<>();

    public SudokuRow(SudokuSquare square, int pos) {
        this.square = square;
        this.pos = pos;
        initAvailableCells();
    }

    public boolean addValueAt(Integer value, int j) {
        boolean added = this.values.add(value);
        this.availableCellKeys.remove(new CellKey(pos, j));
        return added;
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

    private void initAvailableCells() {
        for(int j = 0; j < 9; j++) {
            availableCellKeys.add(new CellKey(pos, j));
        }
    }
}
