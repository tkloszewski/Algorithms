package com.smart.tkl.euler.p184;

import com.smart.tkl.lib.utils.Fraction;
import java.util.TreeMap;

public class TrianglesContainingTheOrigin {

    private final int r;

    public TrianglesContainingTheOrigin(int r) {
        this.r = r;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        final int r = 105;
        TrianglesContainingTheOrigin trianglesContainingTheOrigin = new TrianglesContainingTheOrigin(r);
        long count = trianglesContainingTheOrigin.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long result = 0;
        TreeMap<Fraction, Integer> map = new TreeMap<>();
        for(int x = 1; x < r; x++) {
            for(int y = 0; y * y < r * r - x * x; y++) {
                Fraction fraction = new Fraction(y, x);
                if(map.containsKey(fraction)) {
                    map.put(fraction, map.get(fraction) + 1);
                }
                else {
                    map.put(fraction, 1);
                }
            }
        }

        int quarterSize = map.size();
        int halfSize = 2 * quarterSize;

        LineSegment[] segments = new LineSegment[4 * quarterSize];
        int ind = 0;
        for(Fraction fraction : map.keySet()) {
            int count = map.get(fraction);
            segments[ind++] = new LineSegment(fraction, count);
        }
        for(int k = ind; k < segments.length; k++) {
            int quarter = k / quarterSize;
            LineSegment lineSegment = segments[k % quarterSize];
            Fraction coefficient = lineSegment.coefficient;
            if(quarter == 1 || quarter == 3) {
                coefficient = coefficient.toInverted().toMinusSign();
            }
            segments[k] = new LineSegment(coefficient, lineSegment.count);
        }

        segments[0].cumulativeCount = segments[0].count;
        for(int k = 1; k < segments.length; k++) {
            segments[k].cumulativeCount = segments[k - 1].cumulativeCount + segments[k].count;
        }

        for(int i = 0; i < segments.length; i++) {
            for(int j = i + 2; j < segments.length; j++) {
                if(segments[i].coefficient.equals(segments[j].coefficient)) {
                    continue;
                }
                long betweenCount1 = segments[j].cumulativeCount - segments[j].count - segments[i].cumulativeCount;
                int k = (i + halfSize) % segments.length;
                int l = (j + halfSize) % segments.length;
                int max = Math.max(k, l);
                int min = Math.min(k, l);
                long betweenCount2 = segments[max].cumulativeCount - segments[max].count - segments[min].cumulativeCount;

                long minBetween = Math.min(betweenCount1, betweenCount2);
                long trianglesBetweenSegments = minBetween * segments[i].count * segments[j].count;
                result += trianglesBetweenSegments;
            }
        }

        return result / 3;
    }

    private static class LineSegment {
        Fraction coefficient;
        int count;
        int cumulativeCount = 0;

        public LineSegment(Fraction coefficient, int count) {
            this.coefficient = coefficient;
            this.count = count;
        }

        @Override
        public String toString() {
            return "{" +
                    "coefficient=" + coefficient +
                    ", count=" + count +
                    ", cumulativeCount=" + cumulativeCount +
                    '}';
        }
    }

}
