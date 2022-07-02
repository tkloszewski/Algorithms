package com.smart.tkl.euler.p96.resolver;

import com.smart.tkl.euler.p96.SudokuUtils;
import com.smart.tkl.euler.p96.element.CellKey;
import com.smart.tkl.euler.p96.element.SubSquare;
import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.util.*;
import java.util.stream.Collectors;

public class SubSquareCommonExclusionResolver extends SudokuResolver {

    public SubSquareCommonExclusionResolver(SudokuSquare square) {
        super(square);
    }

    @Override
    public Set<SudokuCell> resolveValues() {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                SubSquare subSquare = sudokuSquare.subSquares[i][j];
                Set<SudokuCell> resolvedCells = resolveSubSquareCellsByExclusion(subSquare);
                if(resolvedCells.size() > 0) {
                    result.addAll(resolvedCells);
                }
            }
        }
        return result;
    }

    @Override
    public ResolverType getType() {
        return ResolverType.SUB_SQUARE_COMMON_EXCLUSION;
    }

    private Set<SudokuCell> resolveSubSquareCellsByExclusion(SubSquare subSquare) {
        Set<SudokuCell> result = new LinkedHashSet<>();
        for(CellKey currentKey : new LinkedHashSet<>(subSquare.availableCellKeys)) {
            Set<Integer> commonExcludedValues = getSubSquareCommonExcludedValues(subSquare, currentKey);
            if(commonExcludedValues.size() == 1) {
                Integer commonExcludedValue = commonExcludedValues.iterator().next();

           //     System.out.println("SubSquareResolver: Found common excluded: " + commonExcludedValue + " at: " + currentKey);

                SudokuCell cell = sudokuSquare.getCell(currentKey);
                cellValueSet(cell, commonExcludedValue);
                result.add(cell);
            }
        }
        return result;
    }

    private Set<Integer> getSubSquareCommonExcludedValues(SubSquare subSquare, CellKey currentKey) {
        List<CoverageLine> coverageLines = getAvailableMinCoverage(subSquare, currentKey);
        List<Set<Integer>> excludedSets = new LinkedList<>();
        coverageLines.forEach(line -> {
            if(CoverageLineType.ROW.equals(line.type)) {
                excludedSets.add(subSquare.subRows[line.idx % 3].getExcluded());
            }
            else if(CoverageLineType.COLUMN.equals(line.type)) {
                excludedSets.add(subSquare.subColumns[line.idx % 3].getExcluded());
            }
        });
        return SudokuUtils.commonValuesSet(excludedSets);
    }

    private List<CoverageLine> getAvailableMinCoverage(SubSquare subSquare, CellKey currentKey) {
        Set<CellKey> availableKeys = new LinkedHashSet<>(subSquare.availableCellKeys);
        availableKeys.remove(currentKey);

        List<CoverageLine> result = new ArrayList<>();
        while (availableKeys.size() > 0) {
            CoverageLine maxCoverageLine = getFirstMaxCoverageLine(availableKeys, currentKey);
            result.add(maxCoverageLine);
            availableKeys.removeAll(maxCoverageLine.availableCellKeys);
        }

        return result;
    }

    private CoverageLine getFirstMaxCoverageLine(Set<CellKey> availableKeys, CellKey currentKey) {
        Map<Integer, List<CellKey>> rowMap = availableKeys.stream()
                .filter(cellKey -> cellKey.i != currentKey.i)
                .collect(Collectors.groupingBy(cellKey -> cellKey.i));

        Map<Integer, List<CellKey>> colMap = availableKeys.stream()
                .filter(cellKey -> cellKey.j != currentKey.j)
                .collect(Collectors.groupingBy(cellKey -> cellKey.j));

        List<CoverageLine> allLines = new ArrayList<>();
        rowMap.forEach((key, value) -> allLines.add(new CoverageLine(CoverageLineType.ROW, key, value)));
        colMap.forEach((key, value) -> allLines.add(new CoverageLine(CoverageLineType.COLUMN, key, value)));

        allLines.sort((line1, line2) -> -1 * line1.compareTo(line2));

        return allLines.get(0);
    }

    private enum CoverageLineType {
        ROW,
        COLUMN
    }

    private static class CoverageLine implements Comparable<CoverageLine>{
        CoverageLineType type;
        int idx;
        Set<CellKey> availableCellKeys;

        CoverageLine(CoverageLineType type, int idx, Collection<CellKey> availableCellKeys) {
            this.type = type;
            this.idx = idx;
            this.availableCellKeys = new LinkedHashSet<>(availableCellKeys);
        }

        @Override
        public int compareTo(CoverageLine other) {
            return this.size().compareTo(other.size());
        }

        public Integer size() {
            return availableCellKeys.size();
        }

        @Override
        public String toString() {
            return "CoverageLine{" +
                    "type=" + type +
                    ", idx=" + idx +
                    ", availableCellKeys=" + availableCellKeys +
                    '}';
        }
    }
}
