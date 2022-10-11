package com.smart.tkl.euler.p122;

public class EfficientExponentiation {

    private final int limit;
    private final int[] multiplications;

    public EfficientExponentiation(int limit) {
        this.limit = limit;
        this.multiplications = new int[limit + 1];
        for(int i = 1; i <= limit; i++) {
            multiplications[i] = binaryMultiplications(i);
        }
    }

    public static void main(String[] args) {
        EfficientExponentiation efficientExponentiation = new EfficientExponentiation(200);
        long time1 = System.currentTimeMillis();
        int minNumberOfMultiplications = efficientExponentiation.solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Minimum number of multiplications: " + minNumberOfMultiplications);
        System.out.println("Solution took ms: " + (time2 - time1));
    }

    public int solve() {
        int result = 0;
        resolveMultiplications(new AdditionChainElement(null, 1, 0));
        for(int i = 1; i < this.multiplications.length; i++) {
            result += multiplications[i];
        }
        return result;
    }

    private void resolveMultiplications(AdditionChainElement currentElement) {
        int value = currentElement.value;
        int level = currentElement.level;
        AdditionChainElement prevElement = currentElement;

        if(this.multiplications[value] > level) {
            this.multiplications[value] = level;
        }
        if(this.multiplications[value] < level || value == limit) {
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

    private int binaryMultiplications(int n) {
        int bitsSet = 0;
        int mostSignificantBit = 0;
        while (n > 0) {
            if(n > 1 && (n & 1) == 1) {
               bitsSet++;
            }
            mostSignificantBit++;
            n = n >> 1;
        }
        return Math.max(0, bitsSet + mostSignificantBit - 1);
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
    }
}
