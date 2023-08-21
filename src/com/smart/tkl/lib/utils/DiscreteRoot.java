package com.smart.tkl.lib.utils;

import java.util.ArrayList;
import java.util.List;

public class DiscreteRoot {

    public static void main(String[] args) {
        List<Long> roots = findAllDiscreteRoots(4, 3, 7);
        System.out.println("Roots for (k=4, a=3, n=7) =>" + roots);
        roots = findAllDiscreteRoots(4, 4, 7);
        System.out.println("Roots for (k=4, a=4, n=7) =>" + roots);
    }

    public static List<Long> findAllDiscreteRoots(int k, long a, long n) {
        List<Long> result = new ArrayList<>();
        long root = PrimitiveRoot.generateRoot(n);
        if(root == -1) {
           return result;
        }
        long powRoot = MathUtils.moduloPower(root, k, n);
        long y = ModDiscreteLog.solve(powRoot, a, n);
        if(y == -1) {
           return result;
        }

        long delta = (n - 1) / MathUtils.GCD(n - 1, k);

        for(long pow = y % delta; pow < n - 1; pow += delta) {
            long x = MathUtils.moduloPower(root, pow, n);
            result.add(x);
        }

        return result;
    }

}
