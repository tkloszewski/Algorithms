package com.smart.tkl.euler.p100.hk;

import com.smart.tkl.lib.utils.XYPair;

public class ArrangedProbabilityOutput {

    XYPair solution;

    public ArrangedProbabilityOutput(XYPair solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return solution != null ? solution.getX() + " " + solution.getY() : "No solution";
    }
}
