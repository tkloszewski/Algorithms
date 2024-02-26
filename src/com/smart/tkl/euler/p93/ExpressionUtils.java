package com.smart.tkl.euler.p93;

import java.util.Optional;

public class ExpressionUtils {

    public static Optional<Double> evaluate(double[] values, int[] operators) {
        return evaluate(values, operators, 0, operators.length);
    }

    public static Optional<Double> evaluate(double[] values, int[] operators, int from, int to) {
        double value = values[from];

        for(int i = from; i < to; i++) {
            int sign = operators[i];
            if(values[i + 1] == 0 && sign == '/') {
                return Optional.empty();
            }
            if(i + 1 < to && (sign == '+' || sign == '-') && (operators[i + 1] == '*' || operators[i + 1] == '/')) {
                int pos = i + 1;
                double accumulatedValue = values[pos];
                while (pos < to && (operators[pos] == '*' || operators[pos] == '/')) {
                    if(values[pos + 1] == 0 && operators[pos] == '/') {
                        return Optional.empty();
                    }
                    accumulatedValue = ExpressionUtils.evaluate(accumulatedValue, values[pos + 1], operators[pos]);
                    pos++;
                }
                value = ExpressionUtils.evaluate(value, accumulatedValue, sign);
                i = pos - 1;
            }
            else {
                value = ExpressionUtils.evaluate(value, values[i + 1], sign);
            }
        }
        return Optional.of(value);
    }
    
    public static double evaluate(double a, double b, int sign) {
        double value = 0;
        switch (sign) {
            case '+':
                value = a + b;
                break;
            case '-':
                value = a - b;
                break;
            case '*':
                value = a * b;
                break;
            case '/':
                value = a / b;
                break;
        }
        return value;
    }
    
}
