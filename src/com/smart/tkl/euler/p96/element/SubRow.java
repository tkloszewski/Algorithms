package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.Set;
import java.util.TreeSet;

import static com.smart.tkl.euler.p96.SudokuUtils.*;

public class SubRow extends SudokuElement {

    public final int pos;
    public final SudokuRow row;
    public final SubSquare subSquare;
    public final Set<Integer> values = new TreeSet<>();
    final Set<Integer> trialValues = new TreeSet<>();

    private Set<Integer> guaranteedValues;

    public SubRow(int pos, SudokuRow row, SubSquare subSquare) {
        this.pos = pos;
        this.row = row;
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
        if (trialValues.size() > 0) {
            this.values.removeAll(trialValues);
            this.trialValues.clear();
        }
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

            Set<Integer> set1 = getSubRowValuesOrGuaranteedValues(subSquare, pos1, level);
            Set<Integer> set2 = getSubRowValuesOrGuaranteedValues(subSquare, pos2, level);
            Set<Integer> set3 = getSubRowValuesOrGuaranteedValues(subSquare.m, (subSquare.n + 1) % 3, pos1, level);
            Set<Integer> set4 = getSubRowValuesOrGuaranteedValues(subSquare.m, (subSquare.n + 2) % 3, pos1, level);
            Set<Integer> set5 = getSubRowValuesOrGuaranteedValues(subSquare.m, (subSquare.n + 1) % 3, pos2, level);
            Set<Integer> set6 = getSubRowValuesOrGuaranteedValues(subSquare.m, (subSquare.n + 2) % 3, pos2, level);

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

    /*Logically this is equivalent of other subsquare rows candidates */
    public Set<Integer> getExcluded() {
        int subSquareRowPos1 = (subSquare.n + 1) % 3;
        int subSquareRowPos2 = (subSquare.n + 2) % 3;

        SubRow subRow1 = getSubRowForSubSquare(subSquare.m, subSquareRowPos1);
        SubRow subRow2 = getSubRowForSubSquare(subSquare.m ,subSquareRowPos2);
        Set<Integer> guaranteedValues1 = subRow1.getGuaranteedValues();
        Set<Integer> guaranteedValues2 = subRow2.getGuaranteedValues();

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

    public SudokuRow getRow() {
        return row;
    }

    public SubSquare getSubSquare() {
        return subSquare;
    }

    private SubRow getSubRowForSubSquare(int m, int n) {
        return getSubSquare(m, n).subRows[pos];
    }

    private SubSquare getSubSquare(int m, int n) {
        return subSquare.square.subSquares[m][n];
    }

    private Set<Integer> getSubRowValues(int m, int n, int pos) {
        return getSubRowValues(subSquare.square.subSquares[m][n], pos);
    }

    private Set<Integer> getSubRowValuesOrGuaranteedValues(int m, int n, int pos, int level) {
        return getSubRowValuesOrGuaranteedValues(subSquare.square.subSquares[m][n], pos, level);
    }

    private Set<Integer> getSubRowValuesOrGuaranteedValues(SubSquare subSquare, int pos, int level) {
        return level > 0 ? getSubRowGuaranteedValues(subSquare ,pos, level) :
                getSubRowValues(subSquare, pos);
    }

    private Set<Integer> getSubRowGuaranteedValues(SubSquare subSquare, int pos, int level) {
        return subSquare.subRows[pos].getGuaranteedValues(level - 1);
    }

    private Set<Integer> getSubRowValues(SubSquare subSquare, int pos) {
        return subSquare.subRows[pos].values;
    }
}
