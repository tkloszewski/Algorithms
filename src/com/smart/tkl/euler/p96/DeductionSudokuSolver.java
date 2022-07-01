package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;
import com.smart.tkl.euler.p96.resolver.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class DeductionSudokuSolver implements SudokuSolver {



    public SudokuSolverResult solve(SudokuSquare sudokuSquare) {
        Set<SudokuCell> allResolved = new LinkedHashSet<>();

        List<SudokuResolver> valueResolvers = List.of(
                new SubSquareCommonExclusionResolver(sudokuSquare),
                new RowCommonExclusionResolver(sudokuSquare),
                new ColumnCommonExclusionResolver(sudokuSquare),
                new DirectExclusionResolver(sudokuSquare)
        );

        while (true) {
            Set<SudokuCell> currentResolved = new LinkedHashSet<>();
            for(SudokuResolver resolver : valueResolvers) {
                Set<SudokuCell> resolved = resolver.resolveValues();
                if(resolved.size() > 0) {
                    currentResolved.addAll(resolved);
                }
            }
            System.out.println("Current iteration resolved: " + currentResolved);
            if(currentResolved.size() == 0) {
                break;
            }
            allResolved.addAll(currentResolved);
        }

        return new SudokuSolverResult(sudokuSquare, allResolved);
    }
}
