package com.smart.tkl.euler.p223;

import com.smart.tkl.lib.tree.pythagorean.PythagoreanTriple;
import com.smart.tkl.lib.tree.pythagorean.PythagoreanTripleTree;
import java.util.List;

public class AlmostRightAngledTriangle1 {



    public static void main(String[] args) {
        int limit = 25000000;
        long time1 = System.currentTimeMillis();
        List<PythagoreanTriple> initialTriples = List.of(
                new PythagoreanTriple(1, 1, 1), new PythagoreanTriple(1, 2, 2));
        long count = new PythagoreanTripleTree(limit, initialTriples).countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Fast force count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }
}
