package com.smart.tkl.euler.p96.element;

import com.smart.tkl.euler.p96.SudokuUtils;

import java.util.Set;
import java.util.TreeSet;

import static com.smart.tkl.euler.p96.SudokuUtils.*;

public class SubColumn {

    final int pos;
    final SudokuColumn column;
    final SubSquare subSquare;
    final Set<Integer> values = new TreeSet<>();

    private Set<Integer> guaranteedValues;

    public SubColumn(int pos, SudokuColumn column, SubSquare subSquare) {
        this.pos = pos;
        this.column = column;
        this.subSquare = subSquare;
    }

    public boolean addValue(Integer value) {
       return this.values.add(value);
    }

    @SuppressWarnings("unchecked")
    public Set<Integer> getGuaranteedValues() {
        Set<Integer> result;
        if(values.size() == 3) {
           result = new TreeSet<>(values);
        }
        else {
            int pos1 = (pos + 1) % 3;
            int pos2 = (pos + 2) % 3;

            Set<Integer> set1 = getSubColumnValues(subSquare, pos1);
            Set<Integer> set2 = getSubColumnValues(subSquare, pos2);
            Set<Integer> set3 = getSubColumnValues((subSquare.m + 1) % 3, subSquare.n, pos1);
            Set<Integer> set4 = getSubColumnValues((subSquare.m + 2) % 3, subSquare.n, pos1);
            Set<Integer> set5 = getSubColumnValues((subSquare.m + 1) % 3, subSquare.n, pos2);
            Set<Integer> set6 = getSubColumnValues((subSquare.m + 2) % 3, subSquare.n, pos2);

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

    private Set<Integer> getSubColumnValues(int m, int n, int pos) {
        return getSubColumnValues(subSquare.square.subSquares[m][n], pos);
    }

    private Set<Integer> getSubColumnValues(SubSquare subSquare, int pos) {
        return subSquare.subColumns[pos].values;
    }
}
