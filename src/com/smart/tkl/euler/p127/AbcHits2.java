package com.smart.tkl.euler.p127;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AbcHits2 {

    private final int limit;
    private final double rLimit;

    private final int[] radicals;
    private final int[] radicalsCount;
    private final int[][] radicalsList;
    private int[] radicalValues;

    private Segment root;

    public AbcHits2(int limit, double rLimit) {
        this.limit = limit;
        this.rLimit = rLimit;
        this.radicals = new int[limit + 1];
        this.radicalsCount = new int[limit + 1];
        radicalsList = new int[limit + 1][];
        init();
        buildTree();
    }

    public static void main(String[] args) {
        int limit = 150000;
        double rLimit = 1.5;
        long time1 = System.currentTimeMillis();
        AbcHits2 abcHits = new AbcHits2(limit, rLimit);
        long time2 = System.currentTimeMillis();

        System.out.println("Sum(100, 1): " + abcHits.findSum(100, 1));
        System.out.println("Sum(50, 1): " + abcHits.findSum(50, 1));
        System.out.println("Sum(50, 0.9): " + abcHits.findSum(50, 0.9));
        System.out.println("Sum(100, 0.9): " + abcHits.findSum(100, 0.9));
        System.out.println("Sum(100, 0.8): " + abcHits.findSum(100, 0.8));
        System.out.println("Sum(90, 0.9): " + abcHits.findSum(90, 0.9));
        System.out.println("Sum(90, 0.8): " + abcHits.findSum(90, 0.8));
        System.out.println("Sum(80, 0.9): " + abcHits.findSum(80, 0.9));
        System.out.println("Sum(80, 0.8): " + abcHits.findSum(80, 0.8));
        System.out.println("Sum(70, 0.9): " + abcHits.findSum(70, 0.9));
        System.out.println("Sum(70, 0.8): " + abcHits.findSum(70, 0.8));
        System.out.println("Sum(70, 0.7): " + abcHits.findSum(70, 0.7));
        System.out.println("Sum(1000, 1.5): " + abcHits.findSum(1000, 1.5));
        System.out.println("Sum(1000, 1): " + abcHits.findSum(1000, 1));
        System.out.println("Sum(100000, 1): " + abcHits.findSum(100000, 1));
        System.out.println("Sum(100000, 1): " + abcHits.findSum(100000, 1.5));
        System.out.println("Sum(100000, 0.5): " + abcHits.findSum(100000, 0.5));
        System.out.println("Sum(100000, 0.65): " + abcHits.findSum(100000, 0.65));
        System.out.println("Sum(100000, 0.64): " + abcHits.findSum(100000, 0.64));
        System.out.println("Sum(120000, 1): " + abcHits.findSum(120000, 1));

        int T = 2000000;
        double[] rValues = new double[]{0.7, 0.71, 0.075, 0.8, 0.85, 0.9, 0.95, 0.99, 1.0, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06,
                1.07, 1.08, 1.09, 1.1, 1.15, 1.2, 1.25, 1.3, 1.33, 1.35, 1.36, 1.37, 1.38, 1.39, 1.4, 1.41, 1.42, 1.43, 1.44, 1.45,
                1.46, 1.47, 1.48, 1.49, 1.5};

        System.out.println("Init time: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        for(int i = 0; i < T; i++) {
            double r = rValues[new Random().nextInt(rValues.length)];
            int L = new Random().nextInt(limit);
            abcHits.findSum(L, r);
        }
        time2 = System.currentTimeMillis();
        System.out.println("Time in ms for queries: " + (time2 - time1));
    }

    private void init() {
        Arrays.fill(radicals, 1);
        for(int n = 2; n <= limit; n++) {
            if (radicals[n] == 1) {
                for(int p = n; p <= limit; p += n) {
                    radicals[p] *= n;
                }
            }
        }

        int allRadicalValues = 0;
        for(int i = 1; i <= limit; i++) {
            if(radicalsCount[radicals[i]] == 0) {
                allRadicalValues++;
            }
            radicalsCount[radicals[i]]++;
        }

        radicalValues = new int[allRadicalValues];
        radicalValues[0] = 1;
        for(int i = 1, j = 1; i <= limit && j < allRadicalValues; i++) {
            if(radicals[i] > radicalValues[j - 1]) {
                radicalValues[j++] = radicals[i];
            }
        }

        for(int i = 1; i <= limit; i++) {
            radicalsList[i] = new int[radicalsCount[i]];
        }

        for(int i = 1; i <= limit; i++) {
            if(i == radicals[i]) {
                radicalsCount[i] = 0;
            }
            radicalsList[radicals[i]][radicalsCount[radicals[i]]++] = i;
        }
    }

    private void buildTree() {
        List<Hit> allHits = findAllHits();
        root = buildTree(allHits, 0, allHits.size() - 1);
    }

    public long findSum(int L, double r) {
        return findSum(root, L, r);
    }

    private List<Hit> findAllHits() {
        List<Hit> hits = new ArrayList<>();

        for(int c = 3; c < limit; c++) {
            double cPowExact = Math.pow(c, rLimit);
            int target = (int) cPowExact;
            if(cPowExact != (int)cPowExact) {
                target++;
            }

            for (int rada : radicalValues) {
                if ((long) rada * rada * radicals[c] >= target) {
                    break;
                }
                int maxRadB = (target - 1) / (rada * radicals[c]);
                if (MathUtils.GCD(rada, radicals[c]) == 1) {
                    for (int i = 0; i < radicalsList[rada].length; i++) {
                        int a = radicalsList[rada][i];
                        if (a >= c) {
                            break;
                        }
                        int b = c - a;
                        if (radicals[b] <= maxRadB && rada < radicals[b]) {
                            double logc = Math.log10(c);
                            double t = (Math.log10(radicals[a]) + Math.log10(radicals[b]) + Math.log10(radicals[c])) / logc;
                            hits.add(new Hit(c, t));
                        }
                    }
                }
            }
        }

        hits.sort(Comparator.comparingInt((Hit h) -> h.c));
        return hits;
    }

    private static long findSum(Segment segment, int L, double r) {
        if(r <= segment.hits[0].power) {
           return 0;
        }
        else if(L > segment.cMax) {
            return findSum(segment, r);
        }
        else {
            Segment left = segment.left;
            Segment right = segment.right;
            if(left != null && right != null) {
                long sum = findSum(segment.left, L, r);
                if(L > right.cMin) {
                    long rightSum = findSum(segment.right, L, r);
                    sum += rightSum;
                }
                return sum;
            }
            else {
                return 0;
            }
        }
    }

    private static long findSum(Segment segment, double r) {
        return findSum(segment.hits, segment.sums, r, 0, segment.hits.length - 1);
    }

    private static long findSum(Hit[] hits, long[] sums, double r, int left, int right) {
        if(left == right) {
            if(r > hits[left].power) {
                return sums[left];
            }
            else {
                return left - 1 >= 0 ? sums[left - 1] : 0;
            }
        }
        else {
            int middle = (left + right) / 2;
            double middleValue = hits[middle].power;
            if(r > middleValue) {
                return findSum(hits, sums, r,middle + 1, right);
            }
            else {
                return findSum(hits, sums, r, left, middle);
            }
        }
    }

    private static Segment buildTree(List<Hit> hits, int l, int r) {
        if(l == r) {
            Hit hit = hits.get(l);
            return new Segment(new Hit[]{hit}, new long[]{hit.c}, hit.c, hit.c, 1);
        }
        else {
            int m = (l + r) / 2;
            Segment leftSegment = buildTree(hits, l, m);
            Segment rightSegment = buildTree(hits, m + 1, r);
            Segment merged = merge(leftSegment.hits, rightSegment.hits);
            merged.cMin = Math.min(leftSegment.cMin, rightSegment.cMin);
            merged.cMax = Math.max(leftSegment.cMax, rightSegment.cMax);
            merged.size = leftSegment.size + rightSegment.size;
            merged.left = leftSegment;
            merged.right = rightSegment;
            return merged;
        }
    }

    private static Segment merge(Hit[] hits1, Hit[] hits2) {
        Hit[] hits = new Hit[hits1.length + hits2.length];
        long[] sums = new long[hits1.length + hits2.length];

        long sum = 0;
        int i = 0,j = 0;
        int pos = 0;
        while(i < hits1.length && j < hits2.length) {
            if(hits1[i].power < hits2[j].power) {
                sum += hits1[i].c;
                hits[pos] = hits1[i];
                sums[pos] = sum;
                i++;
            }
            else {
                sum += hits2[j].c;
                hits[pos] = hits2[j];
                sums[pos] = sum;
                j++;
            }
            pos++;
        }
        while(i < hits1.length) {
            sum += hits1[i].c;
            hits[pos] = hits1[i++];
            sums[pos++] = sum;
        }
        while(j < hits2.length) {
            sum += hits2[j].c;
            hits[pos] = hits2[j++];
            sums[pos++] = sum;
        }

        return new Segment(hits, sums);
    }

    private static class Segment {
        Hit[] hits;
        long[] sums;
        int cMin, cMax;
        int size;

        Segment left, right;

        public Segment(Hit[] hits, long[] sums) {
            this.hits = hits;
            this.sums = sums;
        }

        public Segment(Hit[] hits, long[] sums, int cMin, int cMax, int size) {
            this.hits = hits;
            this.sums = sums;
            this.cMin = cMin;
            this.cMax = cMax;
            this.size = size;
        }
    }

    private static class Hit {
        int c;
        double power;

        public Hit(int c, double power) {
            this.c = c;
            this.power = power;
        }

        @Override
        public String toString() {
            return "Hit{" +
                    "c=" + c +
                    ", power=" + power +
                    '}';
        }
    }
}
