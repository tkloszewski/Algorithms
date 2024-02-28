package com.smart.tkl.euler.p101;

import com.smart.tkl.lib.linear.equations.EquationsSolution;
import com.smart.tkl.lib.linear.equations.LinearEquationsSolver;
import com.smart.tkl.lib.linear.polynomial.NaturalPolynomial;

import java.util.ArrayList;
import java.util.List;

public class OptimumPolynomial {

    private final NaturalPolynomial generatingPolynomial;

    public static void main(String[] args) {
        OptimumPolynomial op = new OptimumPolynomial(new NaturalPolynomial(List.of(1L, 1L, 1L, 1L, 3L, 5L, 10L)));
        System.out.println("Optimum Polynomial sum of FIT " + op.sumOfFirstIncorrectTerms());

        long time1 = System.currentTimeMillis();
        op = new OptimumPolynomial(new NaturalPolynomial(List.of(1L, -1L, 1L, -1L, 1L, -1L, 1L, -1L, 1L, -1L, 1L)));
        long time2 = System.currentTimeMillis();
        System.out.println("Optimum Polynomial sum of FIT " + op.sumOfFirstIncorrectTerms());
        System.out.println("Found solution in ms: " + (time2 - time1));
    }

    public OptimumPolynomial(NaturalPolynomial generatingPolynomial) {
        this.generatingPolynomial = generatingPolynomial;
    }

    public long sumOfFirstIncorrectTerms() {
         List<Long> fits = getFirstIncorrectTerms();
         return fits.stream().reduce(0L, Long::sum);
    }

    public List<Long> getFirstIncorrectTerms() {
        List<Long> fits = new ArrayList<>();
        int maxDegree = generatingPolynomial.getDegree();
        for(int degree = 1; degree <= maxDegree; degree++) {
            NaturalPolynomial approximatedPolynomial = resolvePolynomial(degree, generatingPolynomial);
            if (degree < maxDegree) {
                int n = 1;
                long referenceValue = 1, resolvedValue = 1;
                while(referenceValue == resolvedValue) {
                     referenceValue = generatingPolynomial.getValueFor(n);
                     resolvedValue = approximatedPolynomial.getValueFor(n);
                     n++;
                }
                fits.add(resolvedValue);
            }
        }
        return fits;
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
        long currVal = 1;
        for(int i = 0; i < degree; i++) {
            result[i] = currVal;
            currVal *= n;
        }
        result[degree] = polynomial.getValueFor(n);
        return result;
    }

}
