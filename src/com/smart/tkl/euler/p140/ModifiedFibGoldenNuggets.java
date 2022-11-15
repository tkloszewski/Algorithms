package com.smart.tkl.euler.p140;

import com.smart.tkl.euler.p137.FibGoldenNuggets;

import java.math.BigInteger;
import java.util.List;

public class ModifiedFibGoldenNuggets {

    public static void main(String[] args) {
        BigInteger sum = BigInteger.ZERO;
        FibGoldenNuggets fibGoldenNuggets = new FibGoldenNuggets(30);
        List<BigInteger> goldenNuggets = fibGoldenNuggets.findGoldenNuggets();
        System.out.println(goldenNuggets);
        for(BigInteger goldenNugget : goldenNuggets) {
            sum = sum.add(goldenNugget);
        }
        System.out.println("Sum= " + sum);
    }


}
