package com.smart.tkl.euler.p174;

import java.util.HashMap;
import java.util.Map;

public class HollowSquareLaminae2 {

    private final long limit;

    public HollowSquareLaminae2(long limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long limit = 1000000L;
        HollowSquareLaminae2 hollowSquareLaminae = new HollowSquareLaminae2(limit);
        long count = hollowSquareLaminae.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long result = 0;

        Map<Long, Long> frequencyMap = new HashMap<>();
        long tMax = (long)((Math.sqrt(1 + limit) - 1.0) / 2.0);

        for(long t = 1; t <= tMax; t++) {
           long hMax = (long) ((double)(limit) / (4 * t) - t);
           for(long h = 1; h <= hMax; h++) {
               long tiles = 4 * t * (t + h);
               if(frequencyMap.containsKey(tiles)) {
                  long value = frequencyMap.get(tiles);
                  if(value == 10) {
                     result--;
                  }
                  frequencyMap.put(tiles, value + 1);
               }
               else {
                   result++;
                   frequencyMap.put(tiles, 1L);
               }
           }
           result += hMax;
        }

        return result;
    }
}
