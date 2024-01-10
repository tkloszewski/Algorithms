package com.smart.tkl.lib;

public class FibonacciGenerator {

    public static void main(String[] args) {
        int[] fibb = generate(15);
        printTable(fibb);
        int fibbAt7 = getNumberAt(9);
        System.out.println(fibbAt7);
        fibbAt7 = fibb(9);
        System.out.println(fibbAt7);

    }

    public static int getNumberAt(int index) {
       if(index == 0 || index == 1) {
           return 1;
       }
       int a = 1, b = 1;
       int curr = a + b;
       for(int i = 2; i <= index; i++) {
           curr = a + b;
           a = b;
           b = curr;
       }
       return curr;
    }

    public static int fibb(int index) {
        if(index == 0 || index == 1) {
            return 1;
        }
        return fibb(index - 1) + fibb(index - 2);
    }

    public static int[] generate(int n) {
        assert n > 0 ;
        int[] fibb = new int[n];
        for(int i = 0 ; i < n && i < 2;i++ ) {
            fibb[i] = 1;
        }
        for(int i = 2; i < n; i++) {
            fibb[i] = fibb[i - 1] + fibb[i - 2];
        }
        return fibb;
    }

    private static void printTable(int[] t) {
        printTable(t, t.length - 1);
    }

    private static void printTable(int[] t, int endIdx) {
        for(int i = 0; i <= endIdx; i++) {
            System.out.print(t[i] + " ");
        }
        System.out.print("\n");
    }
}
