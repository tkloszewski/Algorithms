package com.smart.tkl.euler.p219;

public class SkewCostCoding {

    private final long targetSize;
    private final int a, b;
    private final long[] sameCosts;

    public SkewCostCoding(long size, int a, int b) {
        this.targetSize = size;
        this.a = a;
        this.b = b;
        this.sameCosts = new long[1000];
    }

    public static void main(String[] args) {
        long size = 1000000000L;

        SkewCostCoding skewCostCoding = new SkewCostCoding(size, 1, 4);
        long time1 = System.currentTimeMillis();
        long cost = skewCostCoding.calculateCostFast();
        long time2 = System.currentTimeMillis();
        System.out.println("Cost (fast): " + cost);
        System.out.println("Time in ms (fast): " + (time2 - time1));
    }

    /*
    The code can be obtained by taking out cheapest code
    and appending 0 or 1 to it. There are many codes with
    the same cost so it's better to keep unique cost counter
    */
    public long calculateCostFast() {
        if(this.targetSize == 1) {
            return 1;
        }
        long codesSize = 2;
        long totalCost = a + b;

        sameCosts[a] = 1;
        sameCosts[b] = 1;
        if(a == b) {
            sameCosts[a] = 2;
        }

        while (codesSize < targetSize) {
            int minCost = 0;

            while (sameCosts[minCost] == 0) {
                minCost++;
            }

            int cost1 = minCost + a;
            int cost2 = minCost + b;

            long count = sameCosts[minCost];
            if(count > targetSize - codesSize) {
                count = targetSize - codesSize;
            }

            sameCosts[minCost] -= count;
            sameCosts[cost1] += count;
            sameCosts[cost2] += count;

            totalCost += count * (minCost + a + b);

            codesSize += count;
        }
        return totalCost;
    }

    public long calculateCostSlow() {
        if(this.targetSize == 1) {
            return 1;
        }
        long totalCost = 5;
        MinHeap heap = new MinHeap((int)this.targetSize);
        heap.insert(1L);
        heap.insert(4L);

        long minValue = 0 ;
        while (heap.size() < targetSize) {
            minValue = heap.deleteFirst();
            totalCost -= minValue;
            long cost1 = minValue + 1;
            long cost2 = minValue + 4;
            totalCost += (cost1 + cost2);
            heap.insert(cost1);
            heap.insert(cost2);
        }

        return totalCost;
    }
}
