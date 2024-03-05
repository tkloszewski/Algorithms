package com.smart.tkl.euler.p104;

import java.util.ArrayList;
import java.util.List;

public class PandigitalFibonacciNumbers2 {

    private final int a, b, k;

    public PandigitalFibonacciNumbers2(int a, int b, int k) {
        this.a = a;
        this.b = b;
        this.k = k;
    }

    public static void main(String[] args) {
        int a = 3, b = 4, k = 9;
        long time1 = System.currentTimeMillis();
        PandigitalFibonacciNumbers2 fib = new PandigitalFibonacciNumbers2(a, b, k);
        int n = fib.find();
        long time2 = System.currentTimeMillis();
        System.out.println("n: " + n + " => for a: " + a + " and b: " + b);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int find() {
        long f1 = a;
        long f2 = b;

        BigNum b1 = BigNum.of(a);
        BigNum b2 = BigNum.of(b);

        long mod = (long)Math.pow(10, k);

        for(int n = 2; n <= 2000000; n++) {
            if(isPanDigital(f2)) {
               long highest = b2.highest();
               if(highest < mod && b2.size() > 1) {
                  highest = highest * BigNum.MOD + b2.get(b2.size() - 2);
               }
               while (highest >= mod) {
                  highest = highest / 10;
               }
               if(isPanDigital(highest)) {
                  return n;
               }
            }

            long temp = f2;
            f2 = (f1 + f2) % mod;
            f1 = temp;

            BigNum temp1 = b2;
            b2 = b1.add(b2);
            b1 = temp1;

            if(b1.size() > 10) {
                b1.resize();
                b2.resize();
            }
        }
        return -1;
    }

    private boolean isPanDigital(long value) {
        int[] freq = new int[10];
        int numOfDigits = 0;
        while (value != 0) {
            int digit = (int)(value % 10);
            if(digit == 0 || digit > k || ++freq[digit] > 1) {
                return false;
            }
            value = value / 10;
            numOfDigits++;
        }
        return numOfDigits == k;
    }

    private static class BigNum {
        private static final long MOD = (long)Math.pow(10, 9);

        List<Long> values;

        public BigNum(List<Long> values) {
            this.values = values;
        }

        public static BigNum of(long val) {
            List<Long> values = new ArrayList<>();
            while (val > 0) {
                long part = val % MOD;
                values.add(part);
                val = val / MOD;
            }
            return new BigNum(values);
        }

        public BigNum add(BigNum other) {
            if(this.size() < other.size()) {
                for(int i = 0; i < other.size() - this.size(); i++) {
                    values.add(0L);
                }
            }
            List<Long> result = new ArrayList<>(other.size());
            long carry = 0;
            for(int i = 0; i < other.size(); i++) {
                long val1 = values.get(i);
                long val2 = other.get(i);
                long val = val1 + val2 + carry;
                carry = val / MOD;
                result.add(val % MOD);
            }

            if(carry > 0) {
               result.add(carry);
            }

            return new BigNum(result);
        }

        public long highest() {
            return values.get(values.size() - 1);
        }

        public long get(int i) {
            return values.get(i);
        }

        public int size() {
            return values.size();
        }

        public void resize() {
            values = values.subList(values.size() - 10, values.size());
        }

        @Override
        public String toString() {
            return "BigNum{" +
                    "values=" + values +
                    '}';
        }
    }
}
