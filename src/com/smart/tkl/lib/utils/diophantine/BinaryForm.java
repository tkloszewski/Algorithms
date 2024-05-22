package com.smart.tkl.lib.utils.diophantine;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinaryForm {

    private final long a;
    private final long b;
    private final long c;
    private final long delta;

    public BinaryForm(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.delta = b * b - 4 * a * c;
    }

    public static void main(String[] args) {
        System.out.println(new BinaryForm(651, 2718, 2837).toReduced());
        System.out.println(new BinaryForm(178, -3520, 14835).toReduced());
        System.out.println(new BinaryForm(26, -381, 1396).toReduced());
        System.out.println(new BinaryForm(133, 108, 22).toReduced());

        System.out.println(getReduced(-3));
        System.out.println(getReduced(-4));
        System.out.println(getReduced(-20));
        System.out.println(getReduced(-31));
        System.out.println(numberOfQuadraticResidues(31));
    }

    public static List<BinaryForm> getReduced(long delta) {
        if(delta >= 0) {
           return List.of();
        }

        List<BinaryForm> reducedForms = new ArrayList<>();

        long maxA = (long) Math.sqrt(-delta / 3.0);
        for(long a = -maxA; a <= maxA; a++) {
            if(a == 0) {
               continue;
            }
            long maxB = Math.abs(a);
            for(long b = -maxB; b <= maxB; b++) {
                if(Math.abs(b) > a || (b < 0 && Math.abs(b) == a)) {
                   continue;
                }
                long c = b * b - delta;
                if(c % (4 * a) != 0) {
                   continue;
                }
                c = c / (4 * a);
                reducedForms.add(new BinaryForm(a, b, c));
            }
        }

        return reducedForms;
    }

    public Optional<BinaryForm> toReduced() {
        if(delta >= 0 || a <= 0) {
           return Optional.empty();
        }

        long a = this.a;
        long b = this.b;
        long c = this.c;

        while (!isReduced(a, b, c)) {
            if(Math.abs(b) > a) {
                double r = b / (2.0 * a);
                long k = Math.round(r);
                long newB = b - 2 * a * k;
                c = a * k * k - b * k + c;
                b = newB;
            }
            else {
                long temp = a;
                a = c;
                c = temp;
            }
        }

        return Optional.of(new BinaryForm(a, b, c));
    }

    public static long numberOfQuadraticResidues(long p) {
        if(p % 4 == 3 && MathUtils.isPrime(p)) {
           long quadraticResiduesSum = 0, nonQuadraticResidueSum = 0;
           for(long x = 1; x < p; x++) {
               if(QuadraticCongruenceSolver.isQuadraticResidue(x, p)) {
                  quadraticResiduesSum += x;
               }
               else {
                  nonQuadraticResidueSum += x;
               }
           }
           return (nonQuadraticResidueSum - quadraticResiduesSum) / p;
        }
        else {
            return getReduced(-p).size();
        }
    }

    private static boolean isReduced(long a, long b, long c) {
        if(Math.abs(b) <= a && a <= c) {
           if(a == c || Math.abs(b) == a) {
              return b > 0;
           }
           return true;
        }
        return false;
    }

    public long getA() {
        return a;
    }

    public long getB() {
        return b;
    }

    public long getC() {
        return c;
    }

    public long getDelta() {
        return delta;
    }

    @Override
    public String toString() {
        return "BinaryForm{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", delta=" + delta +
                '}';
    }
}
