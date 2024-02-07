package com.smart.tkl.euler.p55;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LychrelNumbers {

    private final int limit;
    private final Map<BigInteger, Integer> countMap = new HashMap<>();

    public LychrelNumbers(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        LychrelNumbers lychrelNumbers = new LychrelNumbers(100000);
        BigInteger palindrome = lychrelNumbers.getMaxCountPalindrome();
        int freq = lychrelNumbers.getFreq(palindrome);
        long time2 = System.currentTimeMillis();
        System.out.println(palindrome);
        System.out.println(freq);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int getFreq(BigInteger palindrome) {
        return countMap.get(palindrome);
    }

    //79497
    public BigInteger getMaxCountPalindrome() {
        BigInteger maxFreqPalindrome = BigInteger.ONE;
        int maxCount = 0;

        Map<BigInteger, BigInteger> arrivesAtPalindromeMap = new HashMap<>();

        for(int i = 1; i <= limit; i++) {
            BigInteger cachedPalindrome = arrivesAtPalindromeMap.get(BigInteger.valueOf(i));
            if(cachedPalindrome == null) {
                Optional<List<BigInteger>> palindromeOpt = getPalindrome(i, 60);
                if (palindromeOpt.isPresent()) {
                    List<BigInteger> chain = palindromeOpt.get();
                    BigInteger palindrome = chain.get(chain.size() - 1);
                    Integer count = countMap.get(palindrome);
                    count = count != null ? count + 1 : 1;
                    countMap.put(palindrome, count);
                    if (maxCount < count) {
                        maxCount = count;
                        maxFreqPalindrome = palindrome;
                    }
                    for (BigInteger element : chain) {
                        arrivesAtPalindromeMap.put(element, palindrome);
                    }
                }
            }
            else {
                Integer count = countMap.get(cachedPalindrome);
                count = count != null ? count + 1 : 1;
                countMap.put(cachedPalindrome, count);
                if (maxCount < count) {
                    maxCount = count;
                    maxFreqPalindrome = cachedPalindrome;
                }
            }
        }

        return maxFreqPalindrome;
    }

    private static Optional<List<BigInteger>> getPalindrome(int n, int iterationsLimit) {
        int i = 0;
        List<BigInteger> chain = new ArrayList<>();
        BigInteger current = BigInteger.valueOf(n);
        chain.add(current);
        while(i < iterationsLimit && !isPalindrome(current)) {
            current = current.add(reverse(current));
            chain.add(current);
            i++;
        }
        return i < iterationsLimit ? Optional.of(chain) : Optional.empty();
    }

    private static BigInteger reverse(BigInteger n) {
        BigInteger reversed = BigInteger.ZERO;
        while(n.compareTo(BigInteger.ZERO) > 0) {
            BigInteger remainder = n.remainder(BigInteger.TEN);
            reversed = reversed.multiply(BigInteger.TEN).add(n.remainder(BigInteger.TEN));
            n = n.subtract(remainder).divide(BigInteger.TEN);
        }
        return reversed;
    }

    private static boolean isPalindrome(BigInteger n) {
        return isPalindrome(n.toString());
    }

    private static boolean isPalindrome(String s) {
        for(int i = 0, j = s.length() - 1; j >= i; i++, j--) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

}
