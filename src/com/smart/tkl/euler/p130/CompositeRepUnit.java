package com.smart.tkl.euler.p130;

import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class CompositeRepUnit {

    private final int size;

    public CompositeRepUnit(int size) {
        this.size = size;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        CompositeRepUnit compositeRepUnit = new CompositeRepUnit(25);
        long sum = compositeRepUnit.getSumOfComposites();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum = " + sum);
        System.out.println("Solution took: " + (time2 - time1));
    }

    public long getSumOfComposites() {
        List<Long> composites = generateComposites();
        return composites.stream().reduce(0L, Long::sum);
    }

    public List<Long> generateComposites() {
        List<Long> result = new ArrayList<>(this.size);
        long number = 9;
        while (result.size() < this.size) {
            long k = A(number);
            if((number - 1) % k == 0) {
               result.add(number);
            }
            number += 2;
            while (number % 5 == 0 || MathUtils.isPrime(number)) {
                number += 2;
            }
        }
        return result;
    }

    private long A(long number) {
        long k = 1, n = 1;
        while (n != 0) {
            n = (10 * n + 1) % number;
            k++;
        }
        return k;
    }
}
