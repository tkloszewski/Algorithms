package com.smart.tkl.euler.p224;

import com.smart.tkl.lib.tree.pythagorean.PythagoreanTriple;
import com.smart.tkl.lib.tree.pythagorean.PythagoreanTripleTree;
import java.util.List;

public class AlmostRightAngledTriangle2 {

    public static void main(String[] args) {
        int limit = 75000000;
        long time1 = System.currentTimeMillis();
        List<PythagoreanTriple> initialTriples = List.of(
                new PythagoreanTriple(2, 2, 3));
        long count = new PythagoreanTripleTree(limit, initialTriples).countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Fast count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }
}
