package com.smart.tkl.euler.p106;

import java.util.ArrayList;
import java.util.List;

public class SubsetMetaTesting {


    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int count = countSubsetPairs(6);
        long time2 = System.currentTimeMillis();
        System.out.println("Number of subset pairs to be tested: " + count);
        System.out.println("Solution took ms: " + (time2 - time1));
    }

    private static int countSubsetPairs(int size) {
        int count = 0;
        int maxMask = (int)Math.pow(2, size) - 1;
        for(int mask1 = 1; mask1 <= maxMask; mask1++) {
            for(int mask2 = mask1 + 1; mask2 <= maxMask; mask2++) {
                if((mask1 & mask2) == 0) {
                    List<Integer> bitsPosition1 = getBitsPosition(mask1, size);
                    List<Integer> bitsPosition2 = getBitsPosition(mask2, size);
                    if(bitsPosition1.size() == bitsPosition2.size()) {
                       boolean include = false;
                       if(bitsPosition1.get(0) > bitsPosition2.get(0)) {
                          for(int i = 1; i < bitsPosition1.size(); i++) {
                              if(bitsPosition1.get(i) < bitsPosition2.get(i)) {
                                 include = true;
                                 break;
                              }
                          }
                       }
                       else {
                           for(int i = 1; i < bitsPosition1.size(); i++) {
                               if(bitsPosition1.get(i) > bitsPosition2.get(i)) {
                                   include = true;
                                   break;
                               }
                           }
                       }
                       if(include) {
                          count++;
                       }
                    }

                }
            }
        }
        return count;
    }

    private static List<Integer> getBitsPosition(int mask, int size) {
        List<Integer> result = new ArrayList<>();
        int pos = size;
        while (mask > 0) {
            if((mask & 1) == 1) {
               result.add(pos);
            }
            pos--;
            mask = mask >> 1;
        }
        return result;
    }
}
