package com.smart.tkl.euler.p101;

import com.smart.tkl.lib.linear.polynomial.NaturalPolynomial;
import java.util.ArrayList;
import java.util.List;

public class OptimumPolynomial2 {

    private final List<Long> coefficients;
    private final long mod;

    public OptimumPolynomial2(List<Long> coefficients, long mod) {
        this.coefficients = adjust(coefficients);
        this.mod = mod;
    }

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 9) + 7;
        List<Long> coefficients = List.of(1L, 1L, 1L, 1L, 1L, 1L, 2L, 1L, 3L, 2L);
        OptimumPolynomial2 op = new OptimumPolynomial2(coefficients, mod);
        List<Long> fits = op.findFITs();
        System.out.println("Fits: " + fits);

        OptimumPolynomial op2 = new OptimumPolynomial(new NaturalPolynomial(coefficients));
        fits = op2.getFirstIncorrectTerms();
        System.out.println("Original fits: " + fits);
    }

    /*
    * OP(k, k + 1) = sum (y[i] * triangle[i] * sign) for i = 1..k
    * Coefficients are taken from Pascal Triangle
    * */
    public List<Long> findFITs() {
        int maxDegree = coefficients.size() - 1;

        NaturalPolynomial polynomial = new NaturalPolynomial(coefficients);
        List<Long> values = new ArrayList<>(maxDegree);
        for(int x = 1; x <= maxDegree; x++) {
            long value = polynomial.getValueFor(x, mod);
            values.add(value);
        }

        long[][] triangles = new long[maxDegree + 1][];
        for(int i = 0; i < triangles.length;i++) {
            triangles[i] = new long[i + 1];
        }

        triangles[0][0] = 1;

        for(int i = 1; i < triangles.length;i++) {
            triangles[i][0] = 1;
            triangles[i][i] = 1;
            for(int j = 1; j < i; j++) {
                triangles[i][j] = (triangles[i - 1][j - 1] + triangles[i - 1][j]) % mod;
            }
        }

        List<Long> result = new ArrayList<>();

        for(int degree = 1, s = 1; degree <= maxDegree; degree++, s *= -1) {
            long[] triangle = triangles[degree];
            long[] y = new long[degree];
            for(int j = 0; j < degree; j++) {
                int x = j + 1;
                y[j] = getOrCalcValue(values, x, polynomial, mod);
            }

            long value = 0, target = 0;
            int x = degree + 1, diff = 0;

            while (value == target) {
                target = getOrCalcValue(values, x, polynomial, mod);
                long calculatedValue = 0;
                for(int j = 0, sign = s; j < degree; j++, sign *= -1) {
                    calculatedValue += sign * (triangle[j] + diff) * y[j];
                    calculatedValue = calculatedValue % mod;
                }
                if(calculatedValue < 0) {
                   calculatedValue = mod + calculatedValue;
                }
                value = calculatedValue;
                x++;
                diff++;
            }

            result.add(value);

        }

        return result;
    }

    private static List<Long> adjust(List<Long> coefficients) {
        int i = coefficients.size() - 1;
        while (i >= 0 && coefficients.get(i) == 0) {
            i--;
        }
        return coefficients.subList(0, i + 1);
    }

    private static long getOrCalcValue(List<Long> values, int x, NaturalPolynomial polynomial, long mod) {
        if(x > values.size()) {
           long value = polynomial.getValueFor(x, mod);
           values.add(value);
           return value;
        }
        return values.get(x - 1);
    }

    private static long val(long[] seq, int grade, int arg) {
        double sum = 0;
        for(int i = 1; i <= grade; i++) {
            long num = 1, den = 1;
            for(int j = 1; j <= grade; j++) {
                if(j != i) {
                   num *= arg - j;
                   den *= i - j;
                }
            }
            sum += ((double) num / (double) den) * seq[i - 1];
        }
        return Math.round(sum);
    }

}
