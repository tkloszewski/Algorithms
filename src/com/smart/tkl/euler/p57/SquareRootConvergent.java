package com.smart.tkl.euler.p57;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.List;

public class SquareRootConvergent {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<Integer> iterations = getIterations(10000);
        long time2 = System.currentTimeMillis();
        System.out.println(iterations);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static List<Integer> getIterations(int N) {
        List<Integer> iterations = new ArrayList<>();

        List<Integer> numerator = List.of(1);
        List<Integer> denominator = List.of(1);

        for(int iteration = 1; iteration <= N; iteration++) {
            List<Integer> prevNumerator = numerator;
            numerator = MathUtils.multipleByDigit(denominator, 2);
            numerator = MathUtils.writtenAddition(numerator, prevNumerator);
            denominator = MathUtils.writtenAddition(denominator, prevNumerator);
            if(numerator.size() > denominator.size()) {
               iterations.add(iteration);
            }
        }

        return iterations;
    }
}
