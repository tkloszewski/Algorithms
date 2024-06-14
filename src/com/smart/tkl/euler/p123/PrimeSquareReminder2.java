package com.smart.tkl.euler.p123;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.List;

public class PrimeSquareReminder2 {

    private final long limit;
    private final int primeLimit;
    private final long[] reminders;

    public PrimeSquareReminder2(long limit) {
        this.limit = limit;
        this.primeLimit = (int)Math.sqrt(limit) * 10;
        this.reminders = generateReminders();
    }


    public static void main(String[] args) {
        long limit = 1000000000000L;
        PrimeSquareReminder2 primeSquareReminder = new PrimeSquareReminder2(limit);
        int primeIndex = primeSquareReminder.findPrimeIndex(limit);
        System.out.println("Prime index: " + primeIndex);
    }

    public int findPrimeIndex(long reminder) {
       if(reminder == 1) {
          return 2;
       }
       if(reminder < 5) {
          return 3;
       }
       if(reminder < 110) {
          return 5;
       }
       int index = findIndex(3, this.reminders.length - 1, reminder);
       return 2 * index + 1;
    }

    private int findIndex(int from, int to, long reminder) {
        if(this.reminders[from] == reminder) {
           return from + 1;
        }
        if(this.reminders[from] > reminder) {
            return from;
        }

        if(from + 1 == to) {
           if(this.reminders[from] > reminder) {
                return from;
           }
           return findIndex(to, to, reminder);
        }

        int middle = (from + to) / 2;
        long middleReminder = this.reminders[middle];

        if(middleReminder == reminder) {
           return middle + 1;
        }
        else if(middleReminder < reminder){
           return findIndex(middle, to, reminder);
        }
        else {
           return findIndex(from, middle, reminder);
        }
    }

    private long[] generateReminders() {
        List<Long> primes = PrimesSieve.generatePrimesUpTo(primeLimit);
        int startInd = primes.size() % 2 == 0 ? primes.size() - 2 : primes.size() - 1;
        int indexThreshold = startInd + 1;
        for(int index = startInd; index >= 0; index -= 2) {
            long prime = primes.get(index);
            if(2 * prime * (index + 1) <= limit) {
               indexThreshold = index + 3;
               break;
            }
        }
        int remindersSize = (indexThreshold + 1) / 2;
        long[] reminders = new long[remindersSize];
        reminders[1] = 5;
        for(int i = 3; i <= remindersSize; i++) {
            int primeIndex = (2 * i - 1);
            reminders[i - 1] = primes.get(primeIndex - 1) * primeIndex * 2;
        }
        return reminders;
    }
}
