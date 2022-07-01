package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.euler.p96.element.SudokuSquare;
import com.smart.tkl.euler.p96.resolver.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {

    private final SudokuSquare sudokuSquare;
    private final List<SudokuResolver> valueResolvers;

    public SudokuSolver(SudokuSquare sudokuSquare) {
        this.sudokuSquare = sudokuSquare;
        this.valueResolvers = List.of(
          new SubSquareCommonExclusionResolver(sudokuSquare),
          new RowCommonExclusionResolver(sudokuSquare),
          new ColumnCommonExclusionResolver(sudokuSquare),
          new DirectExclusionResolver(sudokuSquare)
        );
    }

    public SudokuSolverResult solve() {
        Set<SudokuCell> allResolved = new LinkedHashSet<>();

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
