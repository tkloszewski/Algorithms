package com.smart.tkl.euler.p141;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SquareProgressiveNumber2 {

    private final int maxK;
    private final long maxL;

    private final Segment root;

    public SquareProgressiveNumber2(int maxK, long maxL) {
        this.maxK = maxK;
        this.maxL = maxL;
        this.root = buildTree();
    }

    public static void main(String[] args) {
        long L = 100000000000L;
        int K = 1000000;

        long time1 = System.currentTimeMillis();
        SquareProgressiveNumber2 squareProgressiveNumber = new SquareProgressiveNumber2(K, L);

        long sum1 = squareProgressiveNumber.findSum(0, 100000);
        long sum2 = squareProgressiveNumber.findSum(1, 100000);
        long sum3 = squareProgressiveNumber.findSum(0, 1000000);
        long sum4 = squareProgressiveNumber.findSum(1, 1000000);
        long sum5 = squareProgressiveNumber.findSum(K, L);

        Random r = new Random();
        int T = 100000;
        for(int i = 0; i < T; i++) {
            int distance = r.nextInt(K + 1);
            long limit = Math.abs(r.nextLong()) % L + 1;
            squareProgressiveNumber.findSum(distance, limit);
        }

        long time2 = System.currentTimeMillis();
        System.out.println("Sum1: " + sum1);
        System.out.println("Sum2: " + sum2);
        System.out.println("Sum3: " + sum3);
        System.out.println("Sum4: " + sum4);
        System.out.println("Sum5: " + sum5);
        System.out.println("Solution took in ms: " + (time2 - time1));

        System.out.println("Max distance: " + squareProgressiveNumber.root.maxDistance);
        System.out.println("Max size: " + squareProgressiveNumber.root.size);
    }

    public long findSum(int distance, long limit) {
        return findSum(this.root, distance, limit);
    }

    private Segment buildTree() {
        long tt1 = System.currentTimeMillis();
        List<ProgressiveNumber> progressiveNumbers = getProgressiveNumbers(this.maxK, this.maxL);
        long tt2 = System.currentTimeMillis();
        System.out.println("List built time: " + (tt2 - tt1));

        long t1 = System.currentTimeMillis();
        Segment tree = buildTree(progressiveNumbers, 0, progressiveNumbers.size() - 1);
        long t2 = System.currentTimeMillis();
        System.out.println("Built tree time: " + (t2 - t1));
        return tree;
    }

    private static List<ProgressiveNumber> getProgressiveNumbers(int maxDistance, long limit) {
        long aLimit = (long) Math.pow(limit, 0.33334);
        List<ProgressiveNumber> progressiveNumbers = new ArrayList<>();

        boolean[] distanceUsed = new boolean[maxDistance + 1];

        for(long a = 1; a <= aLimit; a++) {
            for(long b = 1; b < a; b++) {
                if(a * a * a * b + b * b > limit) {
                    break;
                }
                if(MathUtils.GCD(a, b) != 1) {
                    continue;
                }
                for(long m = 1;;m++) {
                    long n = a * a * a * m * m * b + m * b * b;
                    if(n > limit) {
                        break;
                    }
                    int d = getDistance(n);
                    if(d <= maxDistance) {
                        distanceUsed[d] = true;
                        progressiveNumbers.add(new ProgressiveNumber(n, d));
                    }
                }
            }
        }

        int count = 0;
        for(boolean used : distanceUsed) {
            if(used) {
               count++;
            }
        }
        System.out.println("Distance used: " + count);

        progressiveNumbers.sort(Comparator.comparingLong(pn -> pn.value));

        List<ProgressiveNumber> uniqueProgressiveNumbers = new ArrayList<>(progressiveNumbers.size());
        long number = 0;
        for (ProgressiveNumber progressiveNumber : progressiveNumbers) {
            long n = progressiveNumber.value;
            if (n == number) {
                continue;
            }
            uniqueProgressiveNumbers.add(progressiveNumber);
            number = n;
        }

        return uniqueProgressiveNumbers;
    }

    private static int getDistance(long n) {
        long sqrt = (long) Math.sqrt(n);
        long sq1 = sqrt * sqrt;
        long sq2 = (sqrt + 1) * (sqrt + 1);
        return (int)Math.min(n - sq1, sq2 - n);
    }

    private static boolean isSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (long)sqrt;
    }

    private static long findSum(Segment segment, int distance, long L) {
        if(L <= segment.minNumber) {
            return 0;
        }
        else if(L > segment.maxNumber) {
            return findSum(segment, distance);
        }
        else {
            Segment left = segment.left;
            Segment right = segment.right;
            if(left != null && right != null) {
                long sum = findSum(segment.left, distance, L);
                if(L > right.minNumber) {
                    long rightSum = findSum(segment.right, distance, L);
                    sum += rightSum;
                }
                return sum;
            }
            else {
                return 0;
            }
        }
    }

    private static long findSum(Segment segment, int distance) {
        return findSum(segment.distances, segment.sums, distance, 0, segment.size - 1);
    }

    private static long findSum(int[] distances, long[] sums, int distance, int left, int right) {
        if(left == right) {
            if(distance >= distances[left]) {
                return sums[left];
            }
            else {
                return left - 1 >= 0 ? sums[left - 1] : 0;
            }
        }
        else {
            int middle = (left + right) / 2;
            double middleValue = distances[middle];
            if(distance == middleValue) {
               return sums[middle];
            }
            else if(distance > middleValue) {
                return findSum(distances, sums, distance,middle + 1, right);
            }
            else {
                return findSum(distances, sums, distance, left, middle);
            }
        }
    }

    private static Segment buildTree(List<ProgressiveNumber> progressiveNumbers, int l, int r) {
        if(l == r) {
            ProgressiveNumber number = progressiveNumbers.get(l);
            return new Segment(new int[]{number.distance}, new long[]{number.value}, new long[]{number.value},
                    number.distance, number.distance,
                    number.value, number.value, number.value, 1);
        }
        else {
            int m = (l + r) / 2;
            Segment leftSegment = buildTree(progressiveNumbers, l, m);
            Segment rightSegment = buildTree(progressiveNumbers, m + 1, r);
            Segment merged = merge(leftSegment, rightSegment);
            merged.left = leftSegment;
            merged.right = rightSegment;
            return merged;
        }
    }

    private static Segment merge(Segment left, Segment right) {
        int[] distances1 = left.distances;
        int[] distances2 = right.distances;

        long[] partialSums1 = left.partialSums;
        long[] partialSums2 = right.partialSums;

        int size1 = left.size;
        int size2 = right.size;

        long sum = 0;
        int i = 0, j = 0;
        int pos = 0;

        int size = 0;

        while(i < size1 && j < size2) {
            if(distances1[i] == distances2[j]) {
                i++;
                j++;
            }
            else if(distances1[i] < distances2[j]) {
                i++;
            }
            else {
                j++;
            }
            size++;
        }
        while(i < size1) {
            size++;
            i++;
        }
        while(j < size2) {
            size++;
            j++;
        }

        int[] distances = new int[size];
        long[] partialSums = new long[size];
        long[] sums = new long[size];

        i = 0;
        j = 0;

        while(i < size1 && j < size2) {
            if(distances1[i] == distances2[j]) {
               sum += partialSums1[i];
               sum += partialSums2[j];
               partialSums[pos] = partialSums1[i] + partialSums2[j];
               sums[pos] = sum;
               distances[pos] = distances1[i];
               i++;
               j++;
            }
            else if(distances1[i] < distances2[j]) {
                sum += partialSums1[i];
                partialSums[pos] = partialSums1[i];
                sums[pos] = sum;
                distances[pos] = distances1[i];
                i++;
            }
            else {
                sum += partialSums2[j];
                partialSums[pos] = partialSums2[j];
                sums[pos] = sum;
                distances[pos] = distances2[j];
                j++;
            }
            pos++;
        }
        while(i < size1) {
            sum += partialSums1[i];
            partialSums[pos] = partialSums1[i];
            distances[pos] = distances1[i++];
            sums[pos++] = sum;
        }
        while(j < size2) {
            sum += partialSums2[j];
            partialSums[pos] = partialSums2[j];
            distances[pos] = distances2[j++];
            sums[pos++] = sum;
        }

        int minDistance = Math.min(left.minDistance, right.minDistance);
        int maxDistance = Math.max(left.maxDistance, right.maxDistance);
        long minNumber = Math.min(left.minNumber, right.minNumber);
        long maxNumber = Math.max(left.maxNumber, right.maxNumber);

        return new Segment(distances, partialSums, sums, minDistance, maxDistance, minNumber, maxNumber, sum, size);
    }

    private static class Segment {
        int[] distances;
        long[] partialSums;
        long[] sums;
        int minDistance, maxDistance;
        long minNumber, maxNumber;
        long sum;
        int size;

        Segment left, right;

        public Segment(int[] distances, long[] partialSums, long[] sums, int minDistance, int maxDistance, long minNumber, long maxNumber, long sum, int size) {
            this.distances = distances;
            this.partialSums = partialSums;
            this.sums = sums;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.sum = sum;
            this.size = size;
        }
    }

    private static class ProgressiveNumber {
        long value;
        int distance;

        public ProgressiveNumber(long value, int distance) {
            this.value = value;
            this.distance = distance;
        }
    }

}
