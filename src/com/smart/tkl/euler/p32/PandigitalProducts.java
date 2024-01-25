package com.smart.tkl.euler.p32;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PandigitalProducts {

    public static void main(String[] args) {
        for(int n = 4; n <= 9; n++) {
            System.out.println("Sum for " + n + " :" +  getSum(n));
        }
    }

    private static int getSum(int N) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <=N; i++) {
            sb.append(i);
        }
        String s = sb.toString();

        Set<Integer> products = new TreeSet<>();

        for(int i = 2; i < 100; i++) {
            PandigitalResult pandigital1 = isPandigital(N, i);
            if(!pandigital1.pandigital) {
               continue;
            }
            for(int j = i + 1; j < 10000; j++) {
                PandigitalResult pandigital2 = isPandigital(N, j);
                if(!pandigital2.pandigital) {
                    continue;
                }
                int product = i * j;

                PandigitalResult pandigital3 = isPandigital(N, product);
                if(!pandigital3.pandigital) {
                    continue;
                }

                if(pandigital1.digits.size() + pandigital2.digits.size() + pandigital3.digits.size() != N) {
                   continue;
                }

                if(Collections.disjoint(pandigital1.digits, pandigital2.digits)
                        && Collections.disjoint(pandigital3.digits, pandigital1.digits)
                        && Collections.disjoint(pandigital3.digits, pandigital2.digits)) {
                        products.add(product);
                }
            }
        }
        return products.stream().reduce(0, Integer::sum);
    }

    private static PandigitalResult isPandigital(int N, long num) {
        int[] frequency = new int[11];
        Set<Integer> digits = new HashSet<>();
        while (num > 0) {
            int digit = (int)(num % 10);
            if(digit > N || digit == 0) {
                return new PandigitalResult();
            }
            frequency[digit]++;
            if(frequency[digit] > 1) {
               return new PandigitalResult();
            }
            digits.add(digit);
            num = num / 10;
        }

        return new PandigitalResult(digits, true);
    }

    private static class PandigitalResult {
        Set<Integer> digits;
        boolean pandigital;

        public PandigitalResult(Set<Integer> digits, boolean pandigital) {
            this.digits = digits;
            this.pandigital = pandigital;
        }

        public PandigitalResult() {
            this.pandigital = false;
        }
    }
}
