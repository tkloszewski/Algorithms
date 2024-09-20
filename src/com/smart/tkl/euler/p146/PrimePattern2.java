package com.smart.tkl.euler.p146;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PrimePattern2 {

    private final int maxPrime = 97;
    private final List<Long> primes;

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        PrimePattern2 primePattern = new PrimePattern2();
        long sum1 = primePattern.getSum(1, 3, 7, 9, 13, 27, 10);
        long sum2 = primePattern.getSum(1, 3, 7, 9, 13, 27, 11);
        long sum3 = primePattern.getSum(1, 3, 7, 9, 13, 27, 1000000);
        long sum4 = primePattern.getSum(1, 3, 7, 9, 13, 27, 10000000);
        long sum5 = primePattern.getSum(1, 2, 4, 6, 10, 12, 10000000);
        long t2 = System.currentTimeMillis();
        System.out.println("Sum1: " + sum1);
        System.out.println("Sum2: " + sum2);
        System.out.println("Sum3: " + sum3);
        System.out.println("Sum4: " + sum4);
        System.out.println("Sum5: " + sum5);
        System.out.println("Time in ms :" + (t2 - t1));

    }

    public PrimePattern2() {
        this.primes = PrimesSieve.generatePrimesUpTo(maxPrime);
    }

    public long getSum(long a1, long a2, long a3, long a4, long a5, long a6, long limit) {
        return getSum(List.of(a1, a2, a3, a4, a5, a6), limit);
    }

    public long getSum(List<Long> values, long limit) {
        boolean[] usedValues = new boolean[41];
        for(long value : values) {
            usedValues[(int) value] = true;
        }
        Set<Long>[] excludedMap = new Set[maxPrime + 1];
        long finalStep = 1;
        boolean allPrimeRemindersExcluded = false;
        for(long prime : primes) {
            Set<Long> excludedReminders = getExcludedReminders(values, prime);
            if(excludedReminders.size() == prime - 1 && !excludedReminders.contains(0L)) {
                finalStep *= prime;
            }
            else if(excludedReminders.size() == prime) {
                allPrimeRemindersExcluded = true;
            }
            excludedMap[(int)prime] = excludedReminders;
        }

        long sum = 0;
        long step = 1;
        for(long number = 1; number < limit; number += step) {
            boolean valid = true;
            for(long prime : primes) {
                if(number > prime) {
                    Set<Long> excludedReminders = excludedMap[(int) prime];
                    if (excludedReminders.contains(number % prime)) {
                        valid = false;
                        break;
                    }
                }
            }
            if(valid) {
                boolean consecutivePrimes = true;
                long square = number * number;
                long startNumber = square + values.get(0);
                long endNumber = square + values.get(values.size() - 1);
                long incStep = 1;
                for(long testValue = startNumber; testValue <= endNumber; testValue += incStep) {
                    boolean isPrime = Primes.isPrime(testValue);
                    int a = (int)(testValue - square);
                    if(isPrime) {
                       if(!usedValues[a]) {
                          consecutivePrimes = false;
                          break;
                       }
                    }
                    else {
                       if(usedValues[a]) {
                           consecutivePrimes = false;
                           break;
                       }
                    }
                    long mod6 = testValue % 6;
                    if (testValue >= 11) {
                        if(mod6 == 5) {
                           incStep = 2;
                        }
                        else if(mod6 == 1) {
                            incStep = 4;
                        }
                        else if(mod6 == 0) {
                            incStep = 1;
                        }
                        else {
                           incStep = 5 - mod6;
                        }
                    }
                    else {
                        incStep = 1;
                    }

                    if(testValue + incStep <= endNumber) {
                        int nextA = (int)(testValue + incStep - square);
                        for(int add = a + 1; add < nextA; add++) {
                            if(usedValues[add]) {
                                consecutivePrimes = false;
                                break;
                            }
                        }
                    }
                }

                if(consecutivePrimes) {
                    sum += number;
                }
            }
            if(number == maxPrime) {
                long k = maxPrime / finalStep;
                step = (k + 1) * finalStep - maxPrime;
            }
            else if (number > maxPrime) {
                if(allPrimeRemindersExcluded) {
                   break;
                }
                step = finalStep;
            }
        }
        return sum;
    }

    private static Set<Long> getExcludedReminders(List<Long> values, long prime) {
        Set<Long> reminders = new HashSet<>();
        for(long value : values) {
            reminders.add(value % prime);
        }
        Set<Long> excludedReminders = new TreeSet<>();
        for(long reminder : reminders) {
            long a = prime - reminder;
            List<Long> excluded = QuadraticCongruenceSolver.solve(a, prime);
            if (!excluded.isEmpty()) {
                excludedReminders.addAll(excluded);
            }
        }
        return excludedReminders;
    }
}
