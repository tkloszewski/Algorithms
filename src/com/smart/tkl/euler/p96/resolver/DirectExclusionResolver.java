package com.smart.tkl.euler.p96.resolver;

import com.smart.tkl.euler.p96.SudokuUtils;
import com.smart.tkl.euler.p96.element.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class DirectExclusionResolver extends SudokuResolver {

    public DirectExclusionResolver(SudokuSquare square) {
        super(square);
    }

    @Override
    public Set<SudokuCell> resolveValues() {
        Set<SudokuCell> resolvedCells = new LinkedHashSet<>();
        boolean valueSet = true;
        while (valueSet) {
            valueSet = false;
            Set<CellKey> workingCells = new LinkedHashSet<>(sudokuSquare.availableCellKeys);
            for (CellKey currentCellKey : workingCells) {
                SudokuCell currentCell = sudokuSquare.getCell(currentCellKey);
                Set<Integer> candidates = resolveCandidates(currentCell);
                if (candidates.size() == 1) {
                    Integer value = candidates.iterator().next();

                    System.out.println("Direct exclusion resolver found: " + value + " at: " + currentCellKey);

                    resolvedCells.add(currentCell);
                    cellValueSet(currentCell, value);
                    valueSet = true;
                    break;
                }
            }
        }
        return resolvedCells;
    }

    @Override
    public ResolverType getType() {
        return ResolverType.DIRECT_EXCLUSION;
    }

    private Set<Integer> resolveCandidates(SudokuCell cell) {
        Set<Integer> candidates = new TreeSet<>(SudokuUtils.ALL_VALUES);
        candidates.removeAll(getRowExcludedValues(cell));
        candidates.removeAll(getColumnExcludedValues(cell));
        candidates.removeAll(cell.subSquare.values);
        return candidates;
    }

    private Set<Integer> getRowExcludedValues(SudokuCell cell) {
        int currentM = cell.key.i / 3;
        int currentN = cell.key.j / 3;
        int subRowIdx = cell.key.i % 3;

        SubSquare currentSubSquare = sudokuSquare.subSquares[currentM][currentN];
        SubSquare subSquare1 = sudokuSquare.subSquares[currentM][(currentN + 1) % 3];
        SubSquare subSquare2 = sudokuSquare.subSquares[currentM][(currentN + 2) % 3];
        SubRow subRow1 = subSquare1.subRows[subRowIdx];
        SubRow subRow2 = subSquare2.subRows[subRowIdx];

        return SudokuUtils.sumValuesSet(subRow1.getGuaranteedValues(), subRow2.getGuaranteedValues());
    }

    private Set<Integer> getColumnExcludedValues(SudokuCell cell) {
        int currentM = cell.key.i / 3;
        int currentN = cell.key.j / 3;
        int subColIdx = cell.key.j % 3;

        SubSquare currentSubSquare = sudokuSquare.subSquares[currentM][currentN];
        SubSquare subSquare1 = sudokuSquare.subSquares[(currentM + 1) % 3][currentN];
        SubSquare subSquare2 = sudokuSquare.subSquares[(currentM + 2) % 3][currentN];
        SubColumn subColumn1 = subSquare1.subColumns[subColIdx];
        SubColumn subColumn2 = subSquare2.subColumns[subColIdx];

        return SudokuUtils.sumValuesSet(subColumn1.getGuaranteedValues(), subColumn2.getGuaranteedValues());
    }
}
