package com.smart.tkl.euler.p175;

import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class PowersOfTwoFractions {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long p = 123456789, q = 987654321;
        String result = searchAsString(p, q);
        long time2 = System.currentTimeMillis();
        System.out.println("Shortened Binary Expansion: " + result);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static String toShortenedBinaryExpansion(BigInteger n) {
        StringBuilder result = new StringBuilder();

        String s = n.toString(2);
        char[] charArray = s.toCharArray();
        int i = 0;
        while (i < charArray.length) {
            int count = 0;
            while (i < charArray.length && charArray[i] == '1') {
                count++;
                i++;
            }
            if(count > 0) {
                result.append(count).append(",");
            }
            count = 0;
            while (i < charArray.length && charArray[i] == '0') {
                count++;
                i++;
            }
            if(count > 0) {
                result.append(count).append(",");
            }
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }

    public static BigInteger search(long p, long q) {
        long gcd = MathUtils.GCD(p, q);
        p = p / gcd;
        q = q / gcd;
        return doSearch(p ,q);
    }

    private static BigInteger doSearch(long p, long q) {
        if(p == q || p == 0 || q == 0) {
           return BigInteger.ONE;
        }

        if(q > p) {
            int k = (int)(q / p);
            BigInteger pow = BigInteger.TWO.pow(k);
            long r = q % p;
            if(r == 0) {
                pow = pow.divide(BigInteger.TWO);
                return pow.add(max(BigInteger.ZERO, pow.subtract(BigInteger.ONE)));
            }
            else {
                return pow.multiply(doSearch(p, q % p)).add(max(BigInteger.ZERO, pow.subtract(BigInteger.ONE)));
            }
        }
        else {
            int k = (int)(p / q);
            BigInteger pow = BigInteger.TWO.pow(k);

            long r = p % q;
            if(r == 0) {
                return pow.divide(BigInteger.TWO);
            }
            else {
                return pow.multiply(doSearch(p % q, q));
            }
        }

    }

    public static String searchAsString(long p, long q) {
        return search2(p, q).stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public static LinkedList<Integer> search2(long p, long q) {
        long gcd = MathUtils.GCD(p, q);
        p = p / gcd;
        q = q / gcd;
        return doSearch2(p ,q);
    }

    private static LinkedList<Integer> doSearch2(long p, long q) {
        if(p == q || p == 0 || q == 0) {
            return listOf(1);
        }

        if(q > p) {
            int k = (int)(q / p);
            long r = q % p;
            if(r == 0) {
                return listOf(k);
            }
            else {
                LinkedList<Integer> list = doSearch2(p ,r);
                if(list.size() % 2 == 0) {
                   list.add(k);
                }
                else {
                   int value = list.removeLast();
                   list.add(value + k);
                }
                return list;
            }
        }
        else {
            int k = (int)(p / q);

            long r = p % q;
            if(r == 0) {
                LinkedList<Integer> list = listOf(1);
                list.add(k - 1);
                return list;
            }
            else {
                LinkedList<Integer> list = doSearch2(r, q);
                if(list.size() % 2 == 0) {
                    int value = list.removeLast();
                    list.add(value + k);
                }
                else {
                    list.add(k);
                }
                return list;
            }
        }
    }

    private static LinkedList<Integer> listOf(int i) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(i);
        return list;
    }

    private static BigInteger max(BigInteger a, BigInteger b) {
        return a.compareTo(b) > 0 ? a : b;
    }
}
