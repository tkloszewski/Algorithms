package com.smart.tkl.euler.p141;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class SquareProgressiveNumber2 {

    private final int maxK;
    private final long maxL;

    List<ProgressiveNumber> progressiveNumbers;
    private Segment root1, root2;

    public SquareProgressiveNumber2(int maxK, long maxL, List<Long> queryNumbers, List<Integer> queryDistances) {
        this.maxK = maxK;
        this.maxL = maxL;
        init(maxK, maxL, queryNumbers, queryDistances);
    }

    private void init(int maxK, long maxL, List<Long> queryNumbers, List<Integer> queryDistances) {
        this.progressiveNumbers = getProgressiveNumbers(maxK, maxL);
        List<ProgressiveNumber> list = new ArrayList<>(progressiveNumbers);
        root1 = buildTree(list, 0, list.size() - 1);
        root2 = buildTree(list, queryNumbers, queryDistances);
    }

    public static void main(String[] args) {
        long L = 100000000000L;
        int K = 1000000;

        int T = 1000;
        Random r = new Random();

        List<Long> queryNumbers = new ArrayList<>();
        Set<Integer> distancesSet = new TreeSet<>();
        List<Query> queries = new ArrayList<>(T + 1);

        for(int i = 0; i < T; i++) {
            int d = r.nextInt(10) + 1;
            long number = Math.abs(r.nextLong()) % L;
            queries.add(new Query(number, d));
            queryNumbers.add(number);
            distancesSet.add(d);
        }

        queries.add(new Query(L, K));
        queries.add(new Query(L, 0));
        queryNumbers.add(L);
        queryNumbers.add(100000L);
        queryNumbers.add(1000000L);
        distancesSet.add(K);
        distancesSet.add(0);
        distancesSet.add(1);

        Collections.sort(queryNumbers);

        List<Integer> queryDistances = new ArrayList<>(distancesSet);
        SquareProgressiveNumber2 squareProgressiveNumber = new SquareProgressiveNumber2(K, L, queryNumbers, queryDistances);

        squareProgressiveNumber.findBruteForceSum(1, 1000000);

        for(Query query : queries) {
            long sum1 = squareProgressiveNumber.findSum(query.distance, query.number);
            long sum2 = squareProgressiveNumber.findSum2(query.distance, query.number);
            long sum3 = squareProgressiveNumber.findBruteForceSum(query.distance, query.number);

            System.out.println("Sum1: " + sum1);
            System.out.println("Sum2: " + sum2);
            System.out.println("Sum3: " + sum2);
            System.out.println("-------------------");
            if(sum1 != sum2 || sum1 != sum3) {
                System.out.println("DIFF!!!: " + "sum1: " + sum1 + ", sum2: " + sum2 + ", sum3: " + sum3);
                break;
            }
        }
    }

    public long findSum(int distance, long limit) {
        return findSum(this.root1, distance, limit);
    }

    public long findSum2(int distance, long limit) {
        return findSum(this.root2, distance, limit);
    }

    public long findBruteForceSum(int distance, long limit) {
        long sum = 0;
        for(ProgressiveNumber progressiveNumber : progressiveNumbers) {
            if(progressiveNumber.value >= limit) {
                break;
            }
            if(progressiveNumber.distance <= distance) {
               sum += progressiveNumber.value;
            }
        }
        return sum;
    }

    private Segment buildTree() {
        long tt1 = System.currentTimeMillis();
        List<ProgressiveNumber> progressiveNumbers = getProgressiveNumbers(this.maxK, this.maxL);
        long tt2 = System.currentTimeMillis();
        System.out.println("List built time: " + (tt2 - tt1));
        System.out.println("List size: " + progressiveNumbers.size());

        long t1 = System.currentTimeMillis();
        Segment tree = buildTree(progressiveNumbers, 0, progressiveNumbers.size() - 1);
        long t2 = System.currentTimeMillis();
        System.out.println("Built tree time: " + (t2 - t1));
        return tree;
    }

    private static List<ProgressiveNumber> getProgressiveNumbers(int maxDistance, long limit) {
        long aLimit = (long) Math.pow(limit, 0.33334);
        List<ProgressiveNumber> progressiveNumbers = new ArrayList<>();

        for(long a = 1; a <= aLimit; a++) {
            for(long b = 1; b < a; b++) {
                if(a * a * a * b + b * b >= limit) {
                    break;
                }
                if(MathUtils.GCD(a, b) != 1) {
                    continue;
                }
                for(long m = 1;;m++) {
                    long n = a * a * a * m * m * b + m * b * b;
                    if(n >= limit) {
                        break;
                    }
                    int d = getDistance(n);
                    if(d <= maxDistance) {
                        progressiveNumbers.add(new ProgressiveNumber(n, d));
                    }
                }
            }
        }

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

    private static long findNearestSquare(List<Long> squares, long value) {
        return findNearestSquare(squares, value, 0, squares.size() - 1);
    }

    private static long findNearestSquare(List<Long> squares, long value, int l, int r) {
        if(l == r) {
           long square = squares.get(l);
           if(value == square) {
              return square;
           }
           else if(value < square) {
              if(l == 0) {
                 return square;
              }
              else {
                  long lowerSquare = squares.get(l - 1);
                  long distance1 = square - value;
                  long distance2 = value - lowerSquare;
                  return distance1 < distance2 ? square : lowerSquare;
              }
           }
           else {
                 if(r == squares.size() - 1) {
                    return square;
                 }
                 else {
                     long biggerSquare = squares.get(r + 1);
                     long distance1 = value - square;
                     long distance2 = biggerSquare - value;
                     return distance1 < distance2 ? square : biggerSquare;
                 }
           }
        }
        else {
            int middle = (l + r) / 2;
            long middleValue = squares.get(middle);
            if(value == middleValue) {
               return middleValue;
            }
            else if(value < middleValue) {
               return findNearestSquare(squares, value, l, middle);
            }
            else {
               return findNearestSquare(squares, value, middle + 1, r);
            }
        }
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

    private static Segment buildTree(List<ProgressiveNumber> progressiveNumbers, List<Long> queryNumbers, List<Integer> queryDistances) {
        if(progressiveNumbers.isEmpty() || queryNumbers.isEmpty()) {
            return null;
        }

        long t1 = System.currentTimeMillis();
        long prevQueryNumber = 0;
        int pos1 = 0, pos2 = 0;
        Segment head = null, prevSegment = null;
        while (pos1 < progressiveNumbers.size() && pos2 < queryNumbers.size()) {
            long queryNumber = queryNumbers.get(pos2++);
            if(queryNumber == prevQueryNumber) {
                continue;
            }
            prevQueryNumber = queryNumber;

            int startPos = pos1;
            while (pos1 < progressiveNumbers.size() && progressiveNumbers.get(pos1).value <= queryNumber) {
                pos1++;
            }
            if(startPos == pos1) {
                System.out.println("Ignoring query number: " + queryNumber);
                continue;
            }
            List<ProgressiveNumber> subList = progressiveNumbers.subList(startPos, pos1);
            long minNumber = subList.get(0).value;
            long maxNumber = subList.get(subList.size() - 1).value;
            subList.sort(Comparator.comparingInt(progressiveNumber -> progressiveNumber.distance));

            Segment segment = createSegment(subList, queryDistances, minNumber, maxNumber);
            if(head == null) {
                head = segment;
            }
            if(prevSegment != null) {
                prevSegment.next = segment;
            }
            prevSegment = segment;
        }

        long t2 = System.currentTimeMillis();
        System.out.println("First segments build time: " + (t2 - t1));

        t1 = System.currentTimeMillis();
        Segment root = buildTree(head);
        t2 = System.currentTimeMillis();
        System.out.println("Tree build time: " + (t2 - t1));

        return root;
    }

    private static Segment buildTree(Segment head) {
        if(head.next == null) {
            return head;
        }
        Segment segment = head;
        Segment newHead = null;
        Segment prevMerged = null;

        while (segment != null) {
            if(segment.next == null) {
                prevMerged.next = segment;
                break;
            }

            Segment merged = merge(segment, segment.next);
            merged.left = segment;
            merged.right = segment.next;

            if(prevMerged != null) {
                prevMerged.next = merged;
            }

            if(newHead == null) {
                newHead = merged;
            }

            prevMerged = merged;



            segment = segment.next.next;
        }

        return buildTree(newHead);
    }

    private static Segment createSegment(List<ProgressiveNumber> progressiveNumbers, List<Integer> queryDistances, long minNumber, long maxNumber) {
        int minDistance = progressiveNumbers.get(0).distance;
        int maxDistance = progressiveNumbers.get(progressiveNumbers.size() - 1).distance;

        int index1 = findDistanceIndex(queryDistances, minDistance);
        int index2 = findDistanceIndex(queryDistances, maxDistance);
        int size1 = index2 - index1 + 1;

        int distance = -1;
        int size2 = 0;
        for (ProgressiveNumber progressiveNumber : progressiveNumbers) {
            if (distance != progressiveNumber.distance) {
                size2++;
            }
            distance = progressiveNumber.distance;
        }

        if(size1 <= size2) {
            return partitionByQueryDistance(progressiveNumbers, queryDistances, minNumber, maxNumber, index1, index2, size1);
        }
        else {
            return partitionByProgressiveNumbersDistance(progressiveNumbers, minNumber, maxNumber, minDistance, maxDistance, size2);
        }
    }

    private static Segment partitionByQueryDistance(List<ProgressiveNumber> progressiveNumbers, List<Integer> queryDistances, long minNumber, long maxNumber,
                                                    int index1, int index2, int size) {
        int minDistance = queryDistances.get(index1);
        int maxDistance = queryDistances.get(index2);

        int[] distances = new int[size];
        long[] partialSums = new long[size];
        long[] sums = new long[size];

        int j = 0, pos = 0;
        long sum = 0;
        for (int i = index1; i <= index2; i++) {
            int queryDistance = queryDistances.get(i);

            long partialSum = 0;
            while (j < progressiveNumbers.size() && progressiveNumbers.get(j).distance <= queryDistance) {
                ProgressiveNumber progressiveNumber = progressiveNumbers.get(j);
                partialSum += progressiveNumber.value;
                j++;
            }

            sum += partialSum;

            distances[pos] = queryDistance;
            partialSums[pos] = partialSum;
            sums[pos] = sum;

            if (j == progressiveNumbers.size()) {
                break;
            }
            pos++;
        }

        return new Segment(distances, partialSums, sums, minDistance, maxDistance, minNumber, maxNumber, sum, size);
    }

    private static Segment partitionByProgressiveNumbersDistance(List<ProgressiveNumber> progressiveNumbers, long minNumber, long maxNumber,
                                                                 int minDistance, int maxDistance, int size) {
        int[] distances = new int[size];
        long[] partialSums = new long[size];
        long[] sums = new long[size];

        long sum = 0;

        int i = 0, pos = 0;
        while (i < progressiveNumbers.size()) {
            ProgressiveNumber progressiveNumber = progressiveNumbers.get(i++);
            int distance = progressiveNumber.distance;
            long partialSum = progressiveNumber.value;
            while (i < progressiveNumbers.size() && (progressiveNumber = progressiveNumbers.get(i)).distance == distance) {
                partialSum += progressiveNumber.value;
                i++;
            }

            sum += partialSum;
            distances[pos] = distance;
            partialSums[pos] = partialSum;
            sums[pos++] = sum;
        }

        return new Segment(distances, partialSums, sums, minDistance, maxDistance, minNumber, maxNumber, sum, size);
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

    private static int findDistanceIndex(List<Integer> distances, int distance) {
        return findDistanceIndex(distances, distance, 0, distances.size() - 1);
    }

    private static int findDistanceIndex(List<Integer> distances, int distance, int l, int r) {
        if(l == r) {
            return l;
        }
        else {
            int middle = (l + r) / 2;
            int middleValue = distances.get(middle);
            if(distance == middleValue) {
                return middle;
            }
            else if(distance < middleValue) {
                return findDistanceIndex(distances, distance, l, middle);
            }
            else {
                return findDistanceIndex(distances, distance, middle + 1, r);
            }
        }
    }

    private static class Segment {
        int[] distances;
        long[] partialSums;
        long[] sums;
        int minDistance, maxDistance;
        long minNumber, maxNumber;
        long sum;
        int size;

        Segment left, right, next;

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

    private static class ProgressiveNumber implements Comparable<ProgressiveNumber> {
        long value;
        int distance;

        public ProgressiveNumber(long value, int distance) {
            this.value = value;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProgressiveNumber that = (ProgressiveNumber) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public int compareTo(ProgressiveNumber other) {
            return Long.compare(this.value, other.value);
        }

        @Override
        public String toString() {
            return "ProgressiveNumber{" +
                    "value=" + value +
                    ", distance=" + distance +
                    '}';
        }
    }

    private static class Query {
        long number;
        int distance;

        public Query(long number, int distance) {
            this.number = number;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "{" +
                    "new Query(" + number +
                    ", distance=, " + distance +
                    '}';
        }
    }
}
