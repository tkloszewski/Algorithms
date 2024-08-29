package com.smart.tkl.euler.p143;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TorricelliPoint {

    private final int distanceLimit;

    public TorricelliPoint(int distanceLimit) {
        this.distanceLimit = distanceLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        TorricelliPoint point = new TorricelliPoint(120000);
        long distanceSum = point.getSumOfDistances();
        long time2 = System.currentTimeMillis();
        System.out.println("Distance sum: " + distanceSum);
        System.out.println("Solution took in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        point = new TorricelliPoint(400000);
        List<TorricelliTriangle> triangles = point.getSortedTriangles();
        time2 = System.currentTimeMillis();
        System.out.println("Time in ms for triangles: " + (time2 - time1));

        long sum = 0;
        for(TorricelliTriangle triangle : triangles) {
            if(triangle.distance <= 120000) {
                sum += triangle.distance;
            }
        }
        System.out.println("Sum: " + sum);

        long t1 = System.currentTimeMillis();
        int distanceLimit = 400000;
        TorricelliPoint point2 = new TorricelliPoint(distanceLimit);
        List<TorricelliTriangle> uniqueTriangles = point2.getSortedUniqueTriangles();
        long t2 = System.currentTimeMillis();

        System.out.println("Got unique triangles: " + uniqueTriangles.size());
        System.out.println("Time in ms: " + (t2 - t1));

    }

    public Long getSumOfDistances() {
        long sum = 0;
        List<TorricelliTriangle> triangles = getTrianglesWithDistinctDistance();
        for(TorricelliTriangle triangle : triangles) {
            sum += triangle.distance;
        }
        return sum;
    }

    public List<TorricelliTriangle> getSortedTriangles() {
        Comparator<TorricelliTriangle> comparator = getTriangleComparator();
        return new ArrayList<>(getTrianglesWithDistinctDistance(comparator));
    }

    public List<TorricelliTriangle> getTrianglesWithDistinctDistance() {
        return getTrianglesWithDistinctDistance(Comparator.comparingLong(t -> t.distance));
    }

    public List<TorricelliTriangle> getTrianglesWithDistinctDistance(Comparator<TorricelliTriangle> comparator) {
        List<TorricelliTriangle> triangles = new ArrayList<>();

        boolean[] usedDistances = new boolean[distanceLimit + 1];

        List<Pair> pairs = getPairs();
        Map<Long, List<Pair>> xPairsMap = new HashMap<>();

        for(Pair pair : pairs) {
            if(xPairsMap.containsKey(pair.x)) {
                xPairsMap.get(pair.x).add(pair);
            }
            else {
                List<Pair> list = new ArrayList<>();
                list.add(pair);
                xPairsMap.put(pair.x, list);
            }
        }

        List<Pair> pairList = new ArrayList<>(pairs);
        Collections.sort(pairList);

        for(int k = 0; k < pairList.size(); k++) {
            Pair pair = pairList.get(k);
            long x = pair.x;

            if (xPairsMap.containsKey(x)) {
                List<Pair> xPairs = xPairsMap.get(x);
                for(int i = 0; i < xPairs.size(); i++) {
                    Pair pair1 = xPairs.get(i);
                    for(int j = i + 1; j < xPairs.size(); j++) {
                        Pair pair2 = xPairs.get(j);
                        long q = pair1.y, r = pair2.y;
                        long c2 = q * q + r * r + q * r;
                        double sqrt = Math.sqrt(c2);
                        long c = (long)sqrt;
                        if(c == sqrt) {
                            int distance = (int)(x + pair1.y + pair2.y);
                            if(distance <= distanceLimit && !usedDistances[distance]) {
                                TorricelliTriangle triangle = getTriangle(x, pair1.y, pair2.y, distance);
                                triangles.add(triangle);
                                usedDistances[distance] = true;
                            }
                        }
                    }
                }

                k += (xPairs.size() - 1);

            }
        }

        triangles.sort(comparator);

        return triangles;
    }

    public List<TorricelliTriangle> getSortedUniqueTriangles() {
        List<TorricelliTriangle> triangles = new ArrayList<>();

        List<Pair> pairs = getPairs();
        Collections.sort(pairs);


        for(int i = 0; i < pairs.size(); i++)  {
            Pair pair1 = pairs.get(i);
            long x = pair1.x;
            for(int j = i + 1; j < pairs.size(); j++) {
                Pair pair2 = pairs.get(j);
                if(x == pair2.x) {
                    long q = pair1.y, r = pair2.y;
                    long c2 = q * q + r * r + q * r;
                    double sqrt = Math.sqrt(c2);
                    long c = (long)sqrt;
                    if(c == sqrt) {
                        long distance = x + pair1.y + pair2.y;
                        if (distance <= distanceLimit) {
                            TorricelliTriangle triangle = getTriangle(pair1.x, pair1.y, pair2.y, distance);
                            triangles.add(triangle);
                        }
                    }
                }
                else {
                    break;
                }
            }
        }

        Comparator<TorricelliTriangle> triangleComparator = getTriangleComparator();
        triangles.sort(triangleComparator);

        return triangles;
    }

    private static Comparator<TorricelliTriangle> getTriangleComparator() {
        return  (o1, o2) -> {
            int result = Long.compare(o1.a, o2.a);
            if(result == 0) {
                result = Long.compare(o1.b, o2.b);
                if(result == 0) {
                    result = Long.compare(o1.c, o2.c);
                }
            }
            return result;
        };
    }


    private List<Pair> getPairs() {
        List<Pair> result = new ArrayList<>();
        double delta = Math.sqrt(distanceLimit + 1);
        long mLimit = (long)delta - 1;

        for(long m = 2; m <= mLimit; m++) {
            for(long n = 1; n < m; n++) {
                if((m - n) % 3 != 0 && MathUtils.GCD(m, n) == 1) {
                    long a = m * m - n * n;
                    long b = 2 * m * n + n * n;
                    long c = m * m + n * n + m * n;

                    long sum = a + b;

                    if(sum >= distanceLimit) {
                        break;
                    }

                    long p = Math.min(a, b);
                    long q = Math.max(a, b);

                    int k = 1;
                    while (sum < distanceLimit) {
                        int pk = (int)(p * k);
                        int qk = (int)(q * k);

                        Pair pair = new Pair(pk, qk, k * c);
                        result.add(pair);

                        sum = ++k * (a + b);
                    }
                }
            }
        }

        return result;
    }

    private TorricelliTriangle getTriangle(long x, long y, long z, long distance) {
        long a = (long)Math.sqrt(x * x + y * y + y * x);
        long b = (long)Math.sqrt(x * x + z * z + z * x);
        long c = (long)Math.sqrt(y * y + z * z + z * y);

        long[] sides = new long[]{a, b, c};
        Arrays.sort(sides);
        a = sides[0];
        b = sides[1];
        c = sides[2];

        return new TorricelliTriangle(a, b, c, x, y , z, distance);
    }

    private static class Pair implements Comparable<Pair> {
        final long x;
        final long y;
        final long z;

        public Pair(long v1, long v2, long v3) {
            this.x = v1;
            this.y = v2;
            this.z = v3;
        }

        @Override
        public int compareTo(Pair other) {
            int result = Long.compare(x, other.x);
            if(result == 0) {
                result = Long.compare(y, other.y);
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return (x == pair.x &&
                    y == pair.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y + ", z=" + z +"}";
        }
    }
}
