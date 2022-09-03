package com.smart.tkl.linear.equations;

import java.util.List;

public class EquationsSolution {

    private final List<Double> solutions;
    private final boolean infinitelyMany;
    private final boolean inconsistent;

    public EquationsSolution(List<Double> solutions) {
        this(solutions, false, false);
    }

    private EquationsSolution(List<Double> solutions, boolean infinitelyMany, boolean inconsistent) {
        assert solutions != null && !solutions.isEmpty();
        this.solutions = solutions;
        this.infinitelyMany = infinitelyMany;
        this.inconsistent = inconsistent;
    }

    public static EquationsSolution inconsistent() {
        return new EquationsSolution(List.of(), false, true);
    }

    public static EquationsSolution infinitelyMany() {
        return new EquationsSolution(List.of(), true, false);
    }

    public List<Double> getSolutions() {
        return solutions;
    }

    public boolean isInfinitelyMany() {
        return infinitelyMany;
    }

    public boolean isInconsistent() {
        return inconsistent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (!this.solutions.isEmpty()) {
            for(int i = 0; i < this.solutions.size() - 1; i++) {
                sb.append(String.format("%5.2f", this.solutions.get(i)));
                sb.append(", ");
            }
            sb.append(String.format("%5.2f", this.solutions.get(this.solutions.size() - 1)));
        }
        sb.append("]");
        return sb.toString();
    }
}
