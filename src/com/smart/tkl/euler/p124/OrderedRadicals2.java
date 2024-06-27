package com.smart.tkl.euler.p124;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OrderedRadicals2 {
    private final int radicalLimit;
    private final int smallLimit;

    private Radical[] radicals1;
    private Radical[] radicals2;
    private Segment root;

    public OrderedRadicals2(int radicalLimit, long smallLimit) {
        this.radicalLimit = radicalLimit;
        this.smallLimit = (int)smallLimit;
        init();
    }

    //264239
    public static void main(String[] args) {
        int T = 100;

        long[] limits = new long[T];
        int[] positions = new int[T];
        int maxPosition = 0;
        long maxSmallLimit = 0;

        long valueLimit = 1000000000000000000L;

        Random random = new Random();
        for(int i = 0; i < T; i++) {
            limits[i] = Math.abs(random.nextLong()) % valueLimit + 1;
            System.out.println("limits[i] " + limits[i]);
            int position = random.nextInt(200000) + 1;
            if(position > limits[i]) {
               position = (int)limits[i];
            }
            positions[i] = position;
            maxPosition = Math.max(maxPosition, positions[i]);
            if(limits[i] <= 1000000) {
                maxSmallLimit = Math.max(maxSmallLimit, limits[i]);
            }
        }

        OrderedRadicals2 orderedRadicals = new OrderedRadicals2(maxPosition, maxSmallLimit);
        for(int i = 0; i < T; i++) {
            long value = orderedRadicals.searchForValue(positions[i], limits[i]);
            System.out.println(value);
        }
    }

    private void init() {
        if(smallLimit > 0) {
            this.radicals1 = buildSieve(smallLimit);
            Arrays.sort(radicals1);
            root = buildTree(radicals1, 1, radicals1.length - 1);
        }
        this.radicals2 = buildSieve(radicalLimit);
        Arrays.sort(radicals2);
    }

    public long searchForValue(int position, long limit) {
        return limit <= 1000000 ? searchValueForSmallLimit(position, limit) : searchValueForLargeLimit(position, limit);
    }

    private long searchValueForSmallLimit(int position, long limit) {
        return findKthElement(this.radicals1, this.root, limit, position);
    }

    private long searchValueForLargeLimit(int position, long limit) {
        if(position == 1) {
           return 1;
        }
        int totalRadicalValuesCount = 1;
        for(int i = 2; i < radicals2.length; i++) {
            Radical radical = radicals2[i];
            if(radical.isPureRadical()) {
                List<Long> values = new ArrayList<>(100);
                fillValues(0, radical.n, limit, radical.factors, values);
                Collections.sort(values);
                if(totalRadicalValuesCount + values.size() >= position) {
                    int foundIdx = position - totalRadicalValuesCount - 1;
                    return values.get(foundIdx);
                }
                totalRadicalValuesCount += values.size();
            }
        }
        return -1L;
    }

    private Radical[] buildSieve(int radicalLimit) {
        Radical[] radicals = new Radical[radicalLimit + 1];
        for(int k = 0; k <= radicalLimit; k++) {
            radicals[k] = new Radical(k, 1);
        }
        for(int n = 2; n <= radicalLimit; n++) {
            if(radicals[n].product != 1) {
                continue;
            }
            for(int p = n; p <= radicalLimit; p += n) {
                radicals[p].product *= n;
                radicals[p].addFactor(n);
            }
        }
        return radicals;
    }

    private static void fillValues(int pos, long value, long limit, List<Long> factors, List<Long> values) {
        long factor = factors.get(pos);
        long newLimit = limit / value;
        if(pos == factors.size() - 1) {
            for(long poweredFactor = 1; poweredFactor <= newLimit; poweredFactor *= factor) {
                values.add(poweredFactor * value);
            }
        }
        else {
            for(long poweredFactor = 1; poweredFactor <= newLimit; poweredFactor *= factor) {
                long newValue = poweredFactor * value;
                fillValues(pos + 1, newValue, limit, factors, values);
            }
        }
    }

    private static long findKthElement(Radical[] radicals, Segment segment, long value, int k) {
        if(segment.max <= value && k <= segment.size) {
           return radicals[segment.leftIdx + k - 1].n;
        }
        else if(segment.size == 1 && k == 1) {
           return radicals[segment.leftIdx].n;
        }
        else {
            int leftCount = countLessOrEqual(segment.left, value);
            if(leftCount == 0) {
               return findKthElement(radicals, segment.right, value, k);
            }
            else if(leftCount >= k) {
               return findKthElement(radicals, segment.left, value, k);
            }
            else {
               return findKthElement(radicals, segment.right, value, k - leftCount);
            }
        }
    }


    private static int countLessOrEqual(Segment segment, long value) {
        if(segment == null) {
           return 0;
        }
        if(segment.value == value) {
            return segment.count;
        }

        int count = 0;

        if(segment.max <= value) {
           count = segment.size;
        }
        else if(segment.min > value) {
            count = 0;
        }
        else if(segment.min == value) {
            count = 1;
        }
        else {
            count = countLessOrEqual(segment.sorted, value);
        }

        segment.value = value;
        segment.count = count;
        return count;
    }

    private static int countLessOrEqual(long[] sorted, long value) {
        return countLessOrEqual(sorted, value, 0, sorted.length - 1);
    }

    private static int countLessOrEqual(long[] sorted, long value, int left, int right) {
        if(left == right) {
            if(sorted[left] == value) {
                return left + 1;
            }
            return left;
        }
        else {
            long rightValue = sorted[right];
            if(rightValue <= value) {
                return right + 1;
            }
            long leftValue = sorted[left];
            if(leftValue == value) {
                return left + 1;
            }
            int middle = (left + right) / 2;
            long middleValue = sorted[middle];
            if(middleValue < value) {
                return countLessOrEqual(sorted, value, middle + 1, right);
            }
            else {
                return countLessOrEqual(sorted, value, left, middle);
            }
        }
    }

    private static Segment buildTree(Radical[] radicals, int l, int r) {
        if(l == r) {
           return new Segment(l, r, radicals[l].n, radicals[r].n, new long[]{radicals[r].n});
        }
        else {
            int m = (l + r) / 2;
            Segment leftSegment = buildTree(radicals, l, m);
            Segment rightSegment = buildTree(radicals, m + 1, r);
            long min = Math.min(leftSegment.min, rightSegment.min);
            long max = Math.max(leftSegment.max, rightSegment.max);
            long[] merged = merge(leftSegment.sorted, rightSegment.sorted);
            return new Segment(l, r, min, max, merged, leftSegment, rightSegment);
        }
    }

    private static long[] merge(long[] t1, long[] t2) {
        long[] t = new long[t1.length + t2.length];

        int i = 0,j = 0;
        int pos = 0;
        while(i < t1.length && j < t2.length) {
            if(t1[i] < t2[j]) {
                t[pos++] = t1[i++];
            }
            else {
                t[pos++] = t2[j++];
            }
        }
        while(i < t1.length) {
            t[pos++] = t1[i++];
        }
        while(j < t2.length) {
            t[pos++] = t2[j++];
        }

        return t;
    }

    private static class Segment {
        final int leftIdx, rightIdx, size;
        final long min, max;
        final long[] sorted;
        Segment left, right;

        long value = 0;
        int count;

        public Segment(int leftIdx, int rightIdx, long min, long max, long[] sorted, Segment left, Segment right) {
            this.leftIdx = leftIdx;
            this.rightIdx = rightIdx;
            this.size = rightIdx - leftIdx + 1;
            this.min = min;
            this.max = max;
            this.sorted = sorted;
            this.left = left;
            this.right = right;
        }

        public Segment(int leftIdx, int rightIdx, long min, long max, long[] sorted) {
            this(leftIdx, rightIdx, min, max, sorted, null, null);
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "leftIdx=" + leftIdx +
                    ", rightIdx=" + rightIdx +
                    ", min=" + min +
                    ", max=" + max +
                    '}';
        }
    }

    private static class Radical implements Comparable<Radical> {
        long n;
        long product;
        List<Long> factors = new ArrayList<>(10);

        public Radical(long n, long product) {
            this.n = n;
            this.product = product;
        }

        void addFactor(long factor) {
            factors.add(factor);
        }

        boolean isPureRadical() {
            return n == product;
        }

        @Override
        public int compareTo(Radical o) {
            int compareResult = Long.compare(this.product, o.product);
            if(compareResult == 0) {
               compareResult = Long.compare(this.n, o.n);
            }
            return compareResult;
        }

        @Override
        public String toString() {
            return "Radical{" +
                    "n=" + n +
                    ", product=" + product +
                    ", factors=" + factors +
                    '}';
        }
    }


}
