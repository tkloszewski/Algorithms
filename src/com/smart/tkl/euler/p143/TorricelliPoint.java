package com.smart.tkl.euler.p143;

import com.smart.tkl.utils.MathUtils;

import java.util.*;

public class TorricelliPoint {

    private final long distanceLimit;

    public TorricelliPoint(long distanceLimit) {
        this.distanceLimit = distanceLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        TorricelliPoint point = new TorricelliPoint(120000);
        long distanceSum = point.getSumOfDistances();
        long time2 = System.currentTimeMillis();
        System.out.println("Distance sum: " + distanceSum);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public Long getSumOfDistances() {
        long sum = 0;
        Set<TorricelliTriangle> triangles = getTriangles();
        for(TorricelliTriangle triangle : triangles) {
            sum += triangle.distance;
        }
        return sum;
    }

    public Set<TorricelliTriangle> getTriangles() {
        Set<TorricelliTriangle> triangles = new TreeSet<>();
        Set<Long> distances = new HashSet<>();

        Set<Pair> pairs = getPairs();
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
                        Pair thirdPair = new Pair(pair1.y, pair2.y, -1);
                        if(pairs.contains(thirdPair)) {
                            long distance = x + pair1.y + pair2.y;
                            if(distance <= distanceLimit && !distances.contains(distance)) {
                                triangles.add(getTriangle(x, pair1.y, pair2.y, distance));
                                distances.add(distance);
                            }
                        }
                    }
                }

                k += (xPairs.size() - 1);

            }
        }

        return triangles;
    }


    public Set<Pair> getPairs() {
        Set<Pair> result = new LinkedHashSet<>();
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

                    int k = 1;
                    while (sum < distanceLimit) {
                        result.add(new Pair(k * a, k * b, k * c));
                        result.add(new Pair(k * b, k * a, k * c));
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

    private static class TorricelliTriangle implements Comparable<TorricelliTriangle> {
        final long a;
        final long b;
        final long c;
        final long p;
        final long q;
        final long r;
        final long distance;

        public TorricelliTriangle(long a, long b, long c, long p, long q, long r, long distance) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.p = p;
            this.q = q;
            this.r = r;
            this.distance = distance;
        }

        @Override
        public int compareTo(TorricelliTriangle other) {
            return Long.compare(this.distance, other.distance);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TorricelliTriangle that = (TorricelliTriangle) o;
            return a == that.a &&
                    b == that.b &&
                    c == that.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a * b * c);
        }

        @Override
        public String toString() {
            return "{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    ", p=" + p +
                    ", q=" + q +
                    ", r=" + r +
                    ", distance=" + distance +
                    '}';
        }
    }
}
