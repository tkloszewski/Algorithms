package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.Set;
import java.util.TreeSet;

import static com.smart.tkl.euler.p96.SudokuUtils.*;

public class SubColumn extends SudokuElement {

    final int pos;
    final SudokuColumn column;
    final SubSquare subSquare;
    final Set<Integer> values = new TreeSet<>();
    final Set<Integer> trialValues = new TreeSet<>();

    private Set<Integer> guaranteedValues;

    public SubColumn(int pos, SudokuColumn column, SubSquare subSquare) {
        this.pos = pos;
        this.column = column;
        this.subSquare = subSquare;
    }

    public void addValue(Integer value) {
        this.values.add(value);
    }

    @Override
    public void tryValueAt(Integer value, int i, int j) {
        this.values.add(value);
        this.trialValues.add(value);
    }

    @Override
    public void rollbackTrial() {
        this.values.removeAll(trialValues);
        this.trialValues.clear();
    }

    @SuppressWarnings("unchecked")
    public Set<Integer> getGuaranteedValues() {
        return getGuaranteedValues(2);
    }

    public Set<Integer> getGuaranteedValues(int level) {
        Set<Integer> result;
        if(values.size() == 3) {
            result = new TreeSet<>(values);
        }
        else {
            int pos1 = (pos + 1) % 3;
            int pos2 = (pos + 2) % 3;

            Set<Integer> set1 = getSubColumnValuesOrGuaranteedValues(subSquare, pos1, level);
            Set<Integer> set2 = getSubColumnValuesOrGuaranteedValues(subSquare, pos2, level);
            Set<Integer> set3 = getSubColumnValuesOrGuaranteedValues((subSquare.m + 1) % 3, subSquare.n, pos1, level);
            Set<Integer> set4 = getSubColumnValuesOrGuaranteedValues((subSquare.m + 2) % 3, subSquare.n, pos1, level);
            Set<Integer> set5 = getSubColumnValuesOrGuaranteedValues((subSquare.m + 1) % 3, subSquare.n, pos2, level);
            Set<Integer> set6 = getSubColumnValuesOrGuaranteedValues((subSquare.m + 2) % 3, subSquare.n, pos2, level);

            Set<Integer> guaranteedValues = new TreeSet<>();
            if(set1.size() == 3 && set2.size() == 3) {
                guaranteedValues = toExcluded(set1, set2);
            }
            else if(set1.size() == 3) {
                guaranteedValues = subtractSet(sumValuesSet(set5, set6), set1);
            }
            else if(set2.size() == 3) {
                guaranteedValues = subtractSet(sumValuesSet(set3, set4), set2);
            }

            if(guaranteedValues.size() < 3) {
                Set<Integer> sum1 = sumValuesSet(set3, set4);
                Set<Integer> sum2 = sumValuesSet(set5, set6);
                Set<Integer> intersection = commonValuesSet(sum1, sum2);
                if(intersection.size() > guaranteedValues.size()) {
                    guaranteedValues = intersection;
                }
            }

            result = SudokuUtils.sumValuesSet(guaranteedValues, new TreeSet<>(values));
        }
        guaranteedValues = result;
        return result;
    }

    public Set<Integer> getExcluded() {
        int subSquareColPos1 = (subSquare.m + 1) % 3;
        int subSquareColPos2 = (subSquare.m + 2) % 3;

        SubColumn subColumn1 = getSubColumnForSubSquare(subSquareColPos1, subSquare.n);
        SubColumn subColumn2 = getSubColumnForSubSquare(subSquareColPos2, subSquare.n);
        Set<Integer> guaranteedValues1 = subColumn1.getGuaranteedValues();
        Set<Integer> guaranteedValues2 = subColumn2.getGuaranteedValues();

        Set<Integer> result = SudokuUtils.sumValuesSet(guaranteedValues1, guaranteedValues2);
        result.removeAll(this.subSquare.values);
        return result;
    }

    public int getPos() {
        return pos;
    }

    public Set<Integer> getValues() {
        return values;
    }

    public SudokuColumn getColumn() {
        return column;
    }

    public SubSquare getSubSquare() {
        return subSquare;
    }

    private SubColumn getSubColumnForSubSquare(int m, int n) {
        return getSubSquare(m, n).subColumns[pos];
    }

    private SubSquare getSubSquare(int m, int n) {
        return subSquare.square.subSquares[m][n];
    }

    private Set<Integer> getSubColumnValuesOrGuaranteedValues(int m, int n, int pos, int level) {
        return getSubColumnValuesOrGuaranteedValues(subSquare.square.subSquares[m][n], pos, level);
    }

    private Set<Integer> getSubColumnValuesOrGuaranteedValues(SubSquare subSquare, int pos, int level) {
        return level > 0 ? getSubColumnGuaranteedValues(subSquare, pos, level) :
                getSubColumnValues(subSquare, pos);
    }

    private Set<Integer> getSubColumnGuaranteedValues(SubSquare subSquare, int pos, int level) {
        return subSquare.subColumns[pos].getGuaranteedValues(level - 1);
    }

    private Set<Integer> getSubColumnValues(SubSquare subSquare, int pos) {
        return subSquare.subColumns[pos].values;
    }
}
