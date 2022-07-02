package com.smart.tkl.euler.p96.resolver;

import com.smart.tkl.euler.p96.SudokuUtils;
import com.smart.tkl.euler.p96.element.CellKey;
import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuRow;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.*;

public class RowCommonExclusionResolver extends SudokuResolver {

    public RowCommonExclusionResolver(SudokuSquare square) {
        super(square);
    }

    @Override
    public Set<SudokuCell> resolveValues() {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(SudokuRow row : sudokuSquare.rows) {
            Set<SudokuCell> rowResolvedCells = resolveRowCellsByExclusion(row);
            if(rowResolvedCells.size() > 0) {
                result.addAll(rowResolvedCells);
            }
        }
        return result;
    }

    @Override
    public ResolverType getType() {
        return ResolverType.ROW_COMMON_EXCLUSION;
    }

    private Set<SudokuCell> resolveRowCellsByExclusion(SudokuRow row) {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(CellKey currentKey : new LinkedHashSet<>(row.getAvailableCellKeys())) {
            Optional<SudokuCell> resolved = resolveRowCellsByExclusionForCell(row, currentKey);
            resolved.ifPresent(result::add);
        }
        return result;
    }

    private Optional<SudokuCell> resolveRowCellsByExclusionForCell(SudokuRow row, CellKey currentKey) {
        Optional<SudokuCell> result = Optional.empty();
        Set<Integer> commonExcludedValues = getRowCommonExcludedValuesForCell(row, currentKey);
        if(commonExcludedValues.size() == 1) {
            Integer commonExcludedValue = commonExcludedValues.iterator().next();

        //    System.out.println("RowExclusionResolver: Found common excluded: " + commonExcludedValue + " at: " + currentKey);

            SudokuCell cell = sudokuSquare.getCell(currentKey);
            cellValueSet(cell, commonExcludedValue);
            result = Optional.of(cell);
        }
        return result;
    }

    private Set<Integer> getRowCommonExcludedValuesForCell(SudokuRow row, CellKey currentKey) {
        Set<CellKey> availableKeys = new LinkedHashSet<>(row.getAvailableCellKeys());
        availableKeys.remove(currentKey);
        List<Set<Integer>> excludedSets = new LinkedList<>();
        for(CellKey cellKey : availableKeys) {
            Set<Integer> excludedSet = row.getExcludedForColumn(cellKey.j);
            excludedSets.add(excludedSet);
        }
        return SudokuUtils.commonValuesSet(excludedSets);
    }
}
