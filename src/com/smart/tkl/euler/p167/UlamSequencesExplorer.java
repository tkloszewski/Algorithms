package com.smart.tkl.euler.p167;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UlamSequencesExplorer {

   public static void main(String[] args) {
       long time1 = System.currentTimeMillis();
       long k = (long)Math.pow(10, 11);
       long sum = getSum(k);
       long time2 = System.currentTimeMillis();
       System.out.println("Sum: " + sum);
       System.out.println("Time in ms: " + (time2 - time1));
   }

   public static long getSum(long k) {
       long sum = 0;
       for(int n = 2; n <= 10; n++) {
           int b = 2 * n + 1;
           PeriodicUlamSequence ulamSequence = findSequence(b);
           long ulamValue = ulamSequence.valueAt(k);
           sum += ulamValue;
       }
       return sum;
   }

   private static PeriodicUlamSequence findSequence(int b) {
       int length = 2000;
       int[] sequence = new int[length];
       sequence[0] = 2;
       sequence[1] = b;

       int testSum = b + 1;
       int period = 1, startPeriodPosition = -1, pos = 2;

       Map<Integer, Boolean> usedValueMap = new HashMap<>();
       usedValueMap.put(2, true);
       usedValueMap.put(b, true);

       while (true) {
             int numOfSums = 0;

             if(startPeriodPosition == -1) {
                int i = 0, x = sequence[i], y = testSum - x;
                while (x < y) {
                    if(usedValueMap.containsKey(y)) {
                        numOfSums++;
                        if(numOfSums >= 2) {
                            break;
                        }
                    }
                    x = sequence[++i];
                    y = testSum - x;
                }
             }
             else {
                 if(usedValueMap.containsKey(testSum - sequence[0])) {
                     numOfSums++;
                 }
                 if(usedValueMap.containsKey(testSum - sequence[startPeriodPosition - 1])) {
                     numOfSums++;
                 }
             }

             if(numOfSums == 1) {
                //usedValues[testSum] = true;
                sequence[pos] = testSum;
                usedValueMap.put(testSum, true);

                if(testSum % 2 == 0) {
                   startPeriodPosition = pos + 1;
                   testSum--; //Next ulam term is odd and we now increase by 2.
                }

                pos++;

                if(pos == length) {
                    if(startPeriodPosition != -1) {
                        int[] diffs = toDiffs(sequence, startPeriodPosition);
                        int foundPeriod = findPeriod(diffs, period);
                        if(foundPeriod != -1) {
                           return new PeriodicUlamSequence(sequence, foundPeriod, startPeriodPosition);
                        }
                        else {
                            period = diffs.length / 2 + 1;
                            length *= 4;
                            sequence = Arrays.copyOf(sequence, length);
                        }
                    }
                }
             }
             testSum += startPeriodPosition != -1 ? 2 : 1;
       }
   }

   private static int findPeriod(int[] diffs, int startPeriod) {
       int maxPeriod = diffs.length / 2;
       for(int period = startPeriod; period <= maxPeriod; period++) {
           boolean periodFound = true;
           for(int pos = period; pos < diffs.length; pos++) {
               if(diffs[pos] != diffs[pos % period]) {
                  periodFound = false;
                  break;
               }
           }

           if(periodFound) {
              return period;
           }
       }
       return -1;
   }

   private static int[] toDiffs(int[] sequence, int startPeriodPos) {
       int[] diffs = new int[sequence.length - startPeriodPos - 1];
       for(int i = startPeriodPos; i < sequence.length - 1; i++) {
           diffs[i - startPeriodPos]  = sequence[i + 1] - sequence[i];
       }
       return diffs;
   }

}
