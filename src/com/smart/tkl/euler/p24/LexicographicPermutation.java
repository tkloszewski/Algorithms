package com.smart.tkl.euler.p24;

public class LexicographicPermutation {

    private final int length;
    private final char[] chars;
    private final long[] factorials;

    public LexicographicPermutation(String s) {
        this.length = s.length();
        this.chars = s.toCharArray();
        this.factorials = new long[length + 1];
        this.factorials[0] = 1;
        for(int i = 1; i <= length; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
    }

    public static void main(String[] args) {
        String s = "abcde";

        for(int i = 1; i <= 120; i++) {
            LexicographicPermutation lexicographicPermutation = new LexicographicPermutation(s);
            String permutation = lexicographicPermutation.getNthSortedPermutation(i);
            System.out.println(permutation);
        }

    }

    public String getNthSortedPermutation(long nth) {
        setSortedPermutation(this.chars, 0, length, nth);
        return new String(this.chars);
    }

    private void setSortedPermutation(char[] chars, int currentPos, int currentLength, long nth) {
        if(nth == 1) {
           return;
        }
        if(currentLength > 1) {
            if(nth <= factorials[currentLength - 1]) {
               setSortedPermutation(chars, currentPos + 1, currentLength - 1, nth);
            }
            else if(nth == factorials[currentLength]) {
                char currentChar = chars[currentPos];
                chars[currentPos] = chars[chars.length - 1];
                for(int j = chars.length - 1; j > currentPos; j--) {
                    chars[j] = chars[j - 1];
                }
                chars[currentPos + 1] = currentChar;
                setSortedPermutation(chars, currentPos + 1, currentLength - 1, factorials[currentLength - 1]);
            }
            else {
                char currentChar = chars[currentPos];
                int shift = (int)(nth / factorials[currentLength - 1]);
                long rest = nth % factorials[currentLength - 1];

                if(rest == 0) {
                   shift--;
                   nth = factorials[currentLength - 1];
                }
                else {
                   nth = rest;
                }

                if (shift > 0) {
                    chars[currentPos] = chars[currentPos + shift];
                    for (int j = currentPos + shift; j > currentPos; j--) {
                        chars[j] = chars[j - 1];
                    }
                    chars[currentPos + 1] = currentChar;
                    setSortedPermutation(chars, currentPos + 1, currentLength - 1, nth);
                }
            }
        }
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

}
