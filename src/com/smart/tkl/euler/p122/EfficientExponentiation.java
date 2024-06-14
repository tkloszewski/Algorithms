package com.smart.tkl.euler.p122;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EfficientExponentiation {

    private final int limit;
    private final Multiplications[] multiplications;
    private final AdditionChainElement[] chain;

    public EfficientExponentiation(int limit) {
        this.limit = limit;
        this.multiplications = new Multiplications[limit + 1];
        this.chain = new AdditionChainElement[limit + 1];
        for(int i = 1; i <= limit; i++) {
            multiplications[i] = toBinaryMultiplication(i);
            chain[i] = new AdditionChainElement(null, i, multiplications[i].count);
        }
    }

    public static void main(String[] args) {
        int limit = 275;
        EfficientExponentiation efficientExponentiation = new EfficientExponentiation(limit);
        long time1 = System.currentTimeMillis();
        int minNumberOfMultiplications = efficientExponentiation.solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Minimum number of multiplications: " + minNumberOfMultiplications);
        System.out.println("Solution took ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        efficientExponentiation = new EfficientExponentiation(limit);
        efficientExponentiation.solveAll();
        time2 = System.currentTimeMillis();
        System.out.println("Solution took ms: " + (time2 - time1));
    }

    public int solve() {
        int result = 0;
        resolveMultiplications(new AdditionChainElement(null, 1, 0));
        for(int i = 1; i < this.multiplications.length; i++) {
            result += multiplications[i].count;
        }
        return result;
    }

    public void solveAll() {
        resolveMultiplications(new AdditionChainElement(null, 1, 0));
        for(int i = 2; i < this.multiplications.length; i++) {
            if(this.multiplications[i].optimized) {
               List<String> formulas = toFormulas(this.chain[i]);
               this.multiplications[i] = new Multiplications(formulas, true);
            }
        }
    }

    public List<String> getFormulas(int k) {
        Multiplications multiplications = this.multiplications[k];
        return multiplications.formulas;
    }

    private void resolveMultiplications(AdditionChainElement currentElement) {
        int value = currentElement.value;
        int level = currentElement.level;
        AdditionChainElement prevElement = currentElement;

        if(this.multiplications[value].count > level) {
            this.multiplications[value].count = level;
            this.multiplications[value].optimized = true;
            this.chain[value] = currentElement;
        }
        if(this.multiplications[value].count < level || value == limit) {
            return;
        }

        while (prevElement != null) {
            int newValue = value + prevElement.value;
            if(newValue <= limit) {
                AdditionChainElement nextElement = new AdditionChainElement(currentElement, newValue, level + 1);
                resolveMultiplications(nextElement);
            }
            prevElement = prevElement.previous;
        }
    }

    private static List<String> toFormulas(AdditionChainElement chainElement) {
        LinkedList<String> result = new LinkedList<>();
        while (chainElement != null) {
            String formula = chainElement.toFormula();
            if (!formula.isEmpty()) {
                result.addFirst(formula);
            }
            chainElement = chainElement.previous;
        }
        return result;
    }

    private static Multiplications toBinaryMultiplication(int n) {
        if(n == 0) {
            return new Multiplications(List.of());
        }
        if(n == 1) {
            return new Multiplications(List.of("n=n"));
        }

        int pow = 1;
        List<Integer> reminderPowers = new ArrayList<>();
        List<String> formulas = new ArrayList<>();

        int left = n;
        while (2 * pow <= n) {
            if((left & 1) == 1) {
                reminderPowers.add(pow);
            }
            formulas.add(toFormula(pow, pow));
            pow *= 2;
            left = left >> 1;
        }

        for(int i = reminderPowers.size() - 1; i >= 0; i--) {
            int reminderPow = reminderPowers.get(i);
            formulas.add(toFormula(pow, reminderPow));
            pow += reminderPow;
        }

        return new Multiplications(formulas);
    }

    private static String toFormula(int a, int b) {
        return "n^" + a + " * " + "n^" + b + " = n^" + (a + b);
    }

    private static class AdditionChainElement {
        AdditionChainElement previous;
        int value;
        int level;

        public AdditionChainElement(AdditionChainElement previous, int value, int level) {
            this.previous = previous;
            this.value = value;
            this.level = level;
        }

        public String toFormula() {
            if(level == 0) {
                return "";
            }
            if(value == 1 || previous == null) {
                return "n";
            }
            int diff = value - previous.value;
            return "n^" + previous.value + " * n^" + diff + " = n^" + value;
        }

        @Override
        public String toString() {
            return "AdditionChainElement{" +
                    "previous=" + previous +
                    ", value=" + value +
                    ", level=" + level +
                    '}';
        }
    }

    private static class Multiplications {
        int count;
        List<String> formulas;
        boolean optimized;

        public Multiplications(List<String> formulas) {
            this.count = formulas.size();
            this.formulas = formulas;
        }

        public Multiplications(List<String> formulas, boolean optimized) {
            this(formulas);
            this.optimized = optimized;
        }

        @Override
        public String toString() {
            return "Multiplications{" +
                    "count=" + count +
                    ", formulas=" + formulas +
                    ", optimized=" + optimized +
                    '}';
        }
    }
}
