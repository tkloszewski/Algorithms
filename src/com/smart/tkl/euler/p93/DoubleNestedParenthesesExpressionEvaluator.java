package com.smart.tkl.euler.p93;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DoubleNestedParenthesesExpressionEvaluator extends BaseParenthesesExpressionEvaluator {

    private final int nestedSize;

    public DoubleNestedParenthesesExpressionEvaluator(double[] values, int[] operators, int size, int nestedSize) {
        super(values, operators, size);
        this.nestedSize = nestedSize;
    }

    @Override
    protected List<Integer> evaluateRawValues() {
        List<Integer> result = new ArrayList<>();

        double[] newValues = new double[values.length - this.size + 1];
        int[] newOperators = new int[operators.length - this.size + 1];

        for(int pos = 0; pos <= values.length - this.size; pos++) {
            Optional<Double> valueOpt = getValidSubValue(pos);

            if(valueOpt.isEmpty()) {
                continue;
            }

            fillNewValues(newValues, valueOpt.get(), pos);
            fillNewOperators(newOperators, pos);

            Set<Integer> nestedValues = getNestedValues(newValues, newOperators);
            if(!nestedValues.isEmpty()) {
                result.addAll(nestedValues);
            }
        }

        return result;
    }

    private Set<Integer> getNestedValues(double[] newValues, int[] newOperators) {
        NestedParenthesesExpressionEvaluator evaluator = new NestedParenthesesExpressionEvaluator(newValues, newOperators, this.nestedSize, this.nestedSize - 1);
        return evaluator.evaluateValidValues();
    }
}
