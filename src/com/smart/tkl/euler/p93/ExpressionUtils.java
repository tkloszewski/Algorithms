package com.smart.tkl.euler.p93;

public class ExpressionUtils {
    
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
