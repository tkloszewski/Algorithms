package com.smart.tkl.euler.p165;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntersectionExplorer {

    private final int segmentsCount;

    public IntersectionExplorer(int segmentsCount) {
        this.segmentsCount = segmentsCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        IntersectionExplorer intersectionExplorer = new IntersectionExplorer(5000);
        long count = intersectionExplorer.countDistinctIntersections();
        long time2 = System.currentTimeMillis();
        System.out.println("Distinct intersections: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countDistinctIntersections() {
        List<LineSegment> segments = new ArrayList<>(segmentsCount);

        int k = 0;
        long s = 290797;
        long[] t = new long[4];
        while (k < 4 * segmentsCount) {
            s = (s * s) % 50515093;
            t[k++ % 4] = s % 500;
            if(k % 4 == 0) {
               segments.add(new LineSegment(t[0], t[1], t[2], t[3]));
            }
        }
        return countIntersections(segments);
    }

    public static long countIntersections(List<LineSegment> segments) {
        Set<Intersection> intersections = new HashSet<>();

        segments.sort(Comparator.comparingLong(segment -> segment.x1));

        for(int i = 0; i < segments.size(); i++) {
            LineSegment segment1 = segments.get(i);
            for(int j = i + 1; j < segments.size(); j++) {
                LineSegment segment2 = segments.get(j);
                if(segment2.x1 >= segment1.x2) {
                    break;
                }
                Intersection intersection = segment1.getIntersection(segment2);
                if(intersection != null) {
                    intersections.add(intersection);
                }
            }
        }

        return intersections.size();
    }
}
