package com.smart.tkl.euler.p101;

import com.smart.tkl.linear.equations.EquationsSolution;
import com.smart.tkl.linear.equations.LinearEquationsSolver;
import com.smart.tkl.linear.polynomial.NaturalPolynomial;

import java.util.ArrayList;
import java.util.List;

public class OptimumPolynomial {

    private final NaturalPolynomial generatingPolynomial;

    public static void main(String[] args) {
        OptimumPolynomial op = new OptimumPolynomial(new NaturalPolynomial(List.of(0L, 0L, 0L, 1L)));
        System.out.println("Optimum Polynomial sum of FIT " + op.sumOfFirstIncorrectTerms());

        op = new OptimumPolynomial(new NaturalPolynomial(List.of(1L, -1L, 1L, -1L, 1L, -1L, 1L, -1L, 1L, -1L, 1L)));
        System.out.println("Optimum Polynomial sum of FIT " + op.sumOfFirstIncorrectTerms());
    }

    public OptimumPolynomial(NaturalPolynomial generatingPolynomial) {
        this.generatingPolynomial = generatingPolynomial;
    }

    public long sumOfFirstIncorrectTerms() {
        long result = 0;
        int maxDegree = generatingPolynomial.getDegree();
        for(int degree = 1; degree <= maxDegree; degree++) {
            NaturalPolynomial resolvedPolynomial = resolvePolynomial(degree, generatingPolynomial);
            if (degree < maxDegree) {
                int n = 1;
                long referenceValue = 1, resolvedValue = 1;
                while(referenceValue == resolvedValue) {
                     referenceValue = generatingPolynomial.getValueFor(n);
                     resolvedValue = resolvedPolynomial.getValueFor(n);
                     n++;
                }
                result += resolvedValue;
            }
        }
        return result;
    }

    private NaturalPolynomial resolvePolynomial(int degree, NaturalPolynomial polynomial) {
        return new NaturalPolynomial(resolveCoefficient(degree, polynomial));
    }

    private List<Long> resolveCoefficient(int degree, NaturalPolynomial polynomial) {
        List<Long> coefficients = new ArrayList<>(degree);
        double[][] equationCoefficients = new double[degree][degree + 1];
        for(int n = 1; n <= degree; n++) {
            equationCoefficients[n - 1] = getEquationCoefficientsFor(degree, n, polynomial);
        }
        EquationsSolution equationsSolution = new LinearEquationsSolver(equationCoefficients).solve();
        for(Double solved : equationsSolution.getSolutions()) {
            coefficients.add(Math.round(solved));
        }
        return coefficients;
    }

    private double[] getEquationCoefficientsFor(int degree, int n, NaturalPolynomial polynomial) {
        double[] result = new double[degree + 1];
        if(n == 1) {
           for(int i = 0; i < degree; i++) {
               result[i] = 1;
           }
        }
        else {
            long currVal = 1;
            for(int i = 0; i < degree; i++) {
                result[i] = currVal;
                currVal *= n;
            }
        }
        result[degree] = polynomial.getValueFor(n);
        return result;
    }

}