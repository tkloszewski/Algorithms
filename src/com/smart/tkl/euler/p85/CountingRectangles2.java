package com.smart.tkl.euler.p85;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountingRectangles2 {

    private final int limit;
    private final List<RectCount> rectangles;

    public static void main(String[] args) {
        int limit = 15000;
        CountingRectangles2 countingRectangles = new CountingRectangles2(limit);
        System.out.println(countingRectangles.rectangles);
        int foundArea = countingRectangles.find(limit);
        System.out.println("Found area: " + foundArea);
    }

    public CountingRectangles2(int limit) {
        this.limit = 2 * limit;
        this.rectangles = createSortedList();
    }

    public int find(int target) {
        int maxArea = 0;
        List<RectCount> filtered = find(this.rectangles, 0, this.rectangles.size() - 1, target);
        for(RectCount rectCount : filtered) {
            if(rectCount.area > maxArea) {
                maxArea = rectCount.area;
            }
        }
        return maxArea;
    }

    private List<RectCount> createSortedList() {
        List<RectCount> result = new ArrayList<>();
        int mMax = (int)((-1.0 + Math.sqrt(1 + 8 * limit) / 2));

        for(int m = 1; m <= mMax; m++) {
           double delta = Math.sqrt((1.0 + (16.0 * limit) / ((double)m * (m + 1))));
           int nMax = (int)((-1 + delta) / 2.0);
           for(int n = m; n <= nMax; n++) {
               result.add(new RectCount(m, n));
           }
        }

        Collections.sort(result);

        return result;
    }

    private static List<RectCount> find(List<RectCount> list, int left, int right, int target) {
        if(right - left <= 1) {
            List<RectCount> result = new ArrayList<>();

           int diff1 = Math.abs(target - list.get(left).count);
           int diff2 = Math.abs(target - list.get(right).count);
           int diff = Math.min(diff1, diff2);

           for(int i = left; i >= 0; i--) {
               RectCount rectCount = list.get(i);
               if(Math.abs(rectCount.count - target) == diff) {
                  result.add(rectCount);
               }
               else {
                  break;
               }
           }

           if(right == left) {
              right++;
           }

           for(int i = right; i < list.size(); i++) {
               RectCount rectCount = list.get(i);
                if(Math.abs(rectCount.count - target) == diff) {
                    result.add(rectCount);
                }
                else {
                    break;
                }
           }

           return result;
        }

        int middle = (right + left) / 2;
        if(target <= list.get(middle).count) {
           return find(list, left, middle, target);
        }
        else {
           return find(list, middle, right, target);
        }
    }

    private static class RectCount implements Comparable<RectCount> {
        int m, n;
        int count;
        int area;

        public RectCount(int m, int n) {
            this.m = m;
            this.n = n;
            this.count = (m * (m + 1) * n * (n + 1)) / 4;
            this.area = m * n;
        }

        @Override
        public int compareTo(RectCount o) {
            return Integer.compare(this.count, o.count);
        }

        @Override
        public String toString() {
            return "{" +
                    "m=" + m +
                    ", n=" + n +
                    ", count=" + count +
                    ", area=" + area +
                    '}';
        }
    }

}
