package com.smart.tkl.euler.p137;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibGoldenNuggets {

    private final int nuggetsCount;

    public FibGoldenNuggets(int nuggetsCount) {
        this.nuggetsCount = nuggetsCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FibGoldenNuggets goldenNuggets = new FibGoldenNuggets(15);
        BigInteger goldenNugget = goldenNuggets.findLastGoldenNugget();
        long time2 = System.currentTimeMillis();
        System.out.println("Found golden nugget: " + goldenNugget);
        System.out.println("Time elapsed in ms: " + (time2 - time1));
    }

    /*Generate pythagorean triplet using fibonacci sequence
    * (2h_{{n+1}}h_{{n+2}},h_{n}h_{{n+3}},2h_{{n+1}}h_{{n+2}}+h_{n}^{2})*/

    public BigInteger findLastGoldenNugget() {
        List<BigInteger> goldenNuggets = findGoldenNuggets();
        return goldenNuggets.get(goldenNuggets.size() - 1);
    }

    public List<BigInteger> findGoldenNuggets() {
        List<BigInteger> goldenNuggets = new ArrayList<>();
        BigInteger f1 = BigInteger.ONE, f2 = BigInteger.ONE, f3 = BigInteger.valueOf(2), f4 = BigInteger.valueOf(3);
        while (true) {
            BigInteger a = BigInteger.valueOf(2).multiply(f2).multiply(f3);
            BigInteger b = f1.multiply(f4);
            //long c = 2 * f2 * f3 + f1 * f1;

            BigInteger max = a.compareTo(b) > 0 ? a : b;
            BigInteger min = a.compareTo(b) > 0 ? b : a;
            BigInteger r = BigInteger.valueOf(2).multiply(min).subtract(BigInteger.valueOf(2));

            if(max.equals(r)) {
                BigInteger n = max.divide(BigInteger.valueOf(2));
                goldenNuggets.add(n);
                if(goldenNuggets.size() == this.nuggetsCount) {
                    break;
                }
            }

            BigInteger sum = f3.add(f4);
            f1 = f2;
            f2 = f3;
            f3 = f4;
            f4 = sum;
        }

        return goldenNuggets;
    }
}
