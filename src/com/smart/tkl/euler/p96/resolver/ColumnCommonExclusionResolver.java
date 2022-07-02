package com.smart.tkl.euler.p96.resolver;

import com.smart.tkl.euler.p96.SudokuUtils;
import com.smart.tkl.euler.p96.element.CellKey;
import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuColumn;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.*;

public class ColumnCommonExclusionResolver extends SudokuResolver {

    public ColumnCommonExclusionResolver(SudokuSquare sudokuSquare) {
        super(sudokuSquare);
    }

    @Override
    public Set<SudokuCell> resolveValues() {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(SudokuColumn column : sudokuSquare.columns) {
            Set<SudokuCell> rowResolvedCells = resolveColumnCellsByExclusion(column);
            if(rowResolvedCells.size() > 0) {
                result.addAll(rowResolvedCells);
            }
        }
        return result;
    }

    @Override
    public ResolverType getType() {
        return ResolverType.COLUMN_COMMON_EXCLUSION;
    }

    private Set<SudokuCell> resolveColumnCellsByExclusion(SudokuColumn column) {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(CellKey currentKey : new LinkedHashSet<>(column.getAvailableCellKeys())) {
            Optional<SudokuCell> resolved = resolveColumnCellsByExclusionForCell(column, currentKey);
            resolved.ifPresent(result::add);
        }
        return result;
    }

    private Optional<SudokuCell> resolveColumnCellsByExclusionForCell(SudokuColumn column, CellKey currentKey) {
        Optional<SudokuCell> result = Optional.empty();
        Set<Integer> commonExcludedValues = getColumnCommonExcludedValuesForCell(column, currentKey);
        if(commonExcludedValues.size() == 1) {
            Integer commonExcludedValue = commonExcludedValues.iterator().next();

        //    System.out.println("ColumnExclusionResolver: Found common excluded: " + commonExcludedValue + " at: " + currentKey);

            SudokuCell cell = sudokuSquare.getCell(currentKey);
            cellValueSet(cell, commonExcludedValue);
            result = Optional.of(cell);
        }
        return result;
    }

    private Set<Integer> getColumnCommonExcludedValuesForCell(SudokuColumn column, CellKey currentKey) {
        Set<CellKey> availableKeys = new LinkedHashSet<>(column.getAvailableCellKeys());
        availableKeys.remove(currentKey);
        List<Set<Integer>> excludedSets = new LinkedList<>();
        for(CellKey cellKey : availableKeys) {
            Set<Integer> excludedSet = column.getExcludedForRow(cellKey.i);
            excludedSets.add(excludedSet);
        }
        return SudokuUtils.commonValuesSet(excludedSets);
    }
}
