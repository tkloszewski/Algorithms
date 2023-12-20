package com.smart.tkl.euler.p192;

import java.util.ArrayList;
import java.util.List;

public class BestApproximations {

    private final long denominatorLimit;
    private final int range;

    public BestApproximations(long denominatorLimit, int range) {
        this.denominatorLimit = denominatorLimit;
        this.range = range;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long denominatorLimit = (long) Math.pow(10, 12);
        int range = 100000;
        BestApproximations bestApproximations = new BestApproximations(denominatorLimit, range);
        long sum = bestApproximations.sumAllDenominators();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sumAllDenominators() {
        long result = 0;
        for(long n = 2; n <= range; n++) {
            double sqrt = Math.sqrt(n);
            if(sqrt == (int)sqrt) {
               continue;
            }
            long denominator = calcDenominator(n, (long)sqrt);
            result += denominator;

        }
        return result;
    }

    /*
    * a represents coefficients of continued fraction [a0; a1, a2, a3, a4....]
    * */
    private long calcDenominator(long n, long sqrt) {
        List<Long> preSeq = new ArrayList<>();

        long d0 = 0, d1 = 1, d;
        Triplet triplet = new Triplet(sqrt, 0, 1);
        while (true) {
            triplet = triplet.next(n, sqrt);

            d = triplet.a * d1 + d0;

            if(d >= denominatorLimit) {
               if(d == denominatorLimit) {
                  return d;
               }
               else {
                   long x = (denominatorLimit - d0) / d1;
                   long max = x * d1 + d0;
                   if(2 * x > triplet.a) {
                      return max;
                   }
                   else if(2 * x < triplet.a){
                      return  d1;
                   }
                   else {
                     List<Long> postSeq = new ArrayList<>();
                     Triplet next = triplet.next(n, sqrt);
                     for(int i = 0; i < preSeq.size(); i++) {
                         postSeq.add(next.a);
                         next = next.next(n, sqrt);
                     }

                     if(isHalfCoefficientAllowed(preSeq, postSeq)) {
                        return max;
                     }
                     else {
                         return d1;
                     }
                   }
               }
            }

            d0 = d1;
            d1 = d;
            preSeq.add(triplet.a);
        }

    }

    /*
      [0;a_(n+1), a_(n+2), a_(n+3), ....] < [0; a_(n-1), a_(n-2),....,a1] =>
      [a_(n+1), a_(n+2), a_(n+3), ....] > [a_(n-1), a_(n-2),....,a1]
    */
    private static boolean isHalfCoefficientAllowed(List<Long> preSeq, List<Long> postSeq) {
        for(int i = 0; i < preSeq.size(); i++) {
            long preTerm = preSeq.get(preSeq.size() - 1 - i);
            long postTerm = postSeq.get(i);
            if(postTerm > preTerm) {
                return i % 2 == 0;
            }
            else if(postTerm < preTerm) {
                return i % 2 == 1;
            }
        }
        return preSeq.size() % 2 == 1;
    }

    /*In the form of (S^0.5 + m)/n*/
    private static class Triplet {
        long a;
        long m;
        long d;

        public Triplet(long a, long m, long d) {
            this.a = a;
            this.m = m;
            this.d = d;
        }

        public Triplet next(long n, long sqrt) {
            long nextM = a * d - m;
            long nextD = (n - nextM * nextM) / d;
            long nextA = (sqrt + nextM) / nextD;
            return new Triplet(nextA, nextM, nextD);
        }
    }

}
