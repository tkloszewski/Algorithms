package com.smart.tkl.euler.p93;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class GenericExpressionEvaluator extends BaseExpressionEvaluator {

    private static final double EPS = Math.pow(10, -6);

    public GenericExpressionEvaluator(double[] values, int[] operators) {
        super(values, operators);
    }

    @Override
    public Set<Integer> evaluateValidValues() {
        Set<Integer> result = new TreeSet<>();
        if(values.length == 1) {
            result.add((int)values[0]);
        }
        else if(values.length == 2) {
            double a = values[0];
            double b = values[1];
            int operator = operators[0];
            if(operator != '/' || b != 0) {
                Optional<Integer> evaluated = getValidValue(ExpressionUtils.evaluate(a, b, operator));
                evaluated.ifPresent(result::add);
            }
        }
        else {
            Set<Double> evaluatedValues = evaluateValues(0, values.length - 1);
            for(double evaluatedValue : evaluatedValues) {
                if(evaluatedValue > 0 && Math.abs(Math.round(evaluatedValue) - evaluatedValue) < EPS) {
                    result.add((int)Math.round(evaluatedValue));
                }
            }
        }
        return result;
    }

    private Set<Double> evaluateValues(int from, int to) {
        if(to == from) {
            return Set.of(values[from]);
        }
        else if(to - from == 1) {
            Double evaluated = evaluateValue(values[from], values[to], operators[from]);
            if(evaluated != null && evaluated == 407) {
               System.out.println("407!!!!");
            }
            return evaluated != null ? Set.of(evaluated) : Set.of();
        }
        else {
            Set<Double> result = new HashSet<>();

            Optional<Double> evaluated = ExpressionUtils.evaluate(this.values, this.operators, from, to);
            evaluated.ifPresent(result::add);

            if(allOperatorsEquals(operators, from, to, '+') || allOperatorsEquals(operators, from, to, '*')) {
                return result;
            }

            for(int split = from; split < to; split++) {
                Set<Double> evaluatedValues1 = evaluateValues(from, split);
                if(!evaluatedValues1.isEmpty()) {
                    Set<Double> evaluatedValues2 = evaluateValues(split + 1, to);
                    if(!evaluatedValues2.isEmpty()) {
                        int operator = operators[split];
                        for(double evaluatedValue1 : evaluatedValues1) {
                            for(double evaluatedValue2 : evaluatedValues2) {
                                Double evaluatedValue = evaluateValue(evaluatedValue1, evaluatedValue2, operator);
                                if(evaluatedValue != null) {
                                    result.add(evaluatedValue);
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    private Double evaluateValue(double a, double b, int operator) {
        if(operator != '/' || b != 0) {
            return ExpressionUtils.evaluate(a, b, operator);
        }
        return null;
    }

    private static boolean allOperatorsEquals(int[] operators, int from, int to, int oper) {
        for(int i = from; i < to; i++) {
            if(operators[i] != oper) {
                return false;
            }
        }
        return true;
    }
}
