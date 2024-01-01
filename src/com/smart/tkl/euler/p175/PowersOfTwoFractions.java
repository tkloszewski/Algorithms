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


    /*
     q > p then n must be odd and in the form of 2(n/2) + 1 because f(2(n/2) + 1) = f(n/2) and
        f(2(n/2)) = f(n/2) + f(n/2 - 1). If q/p = pow * p + r then 2 cases:
        r = 0 =>  f(k) * (q - (pow - 1) * p) = f(k-1) * p => f(k) * p => f(k-1)*p => f(k) = f(k-1) => k = 1
        => then n = 2^(pow-1) + 2^(pow-2) + 2^(pow-3) + ... + 1 = 2^(pow-1) + (2^(pow-1) - 1)
        if r > 0 then we have
        f(k) (q - pow * p) = f(k-1) * p => f(k) * r = f(k-1) * p => f(k)/f(k-1) = p/r
        and n = 2^pow * k + 2^(pow-1) + ... + 1 = 2^pow * k + 2^pow - 1.

     q < p then n must even and the form of 2(n/2) because f(2(n/2)) = f(n/2) + f(n/2 - 1) and
        f(2(n/2) - 1) = f(n/2 - 1). If p/q = pow * q + r then 2 cases:
        r = 0 => f(k-1) * (p - (pow-1) * q) = q * f(k) => n = 2^(pow-1)
        r > 0 => f(k-1) * (p - pow * q) = q * f(k) => f(k-1) * r = q * f(k) => n = 2^pow * k and
        f(k)/f(k-1) = r/q
    * */
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

    /*1. If list size is even it means we have even number and there is 0 at the end. We left shift this number
      by k and add ones. On the other hand at the is one and we have to add k ones to the Shorted Binary Expansion.

      2. If n = 2^(k-1) then Shorted Binary Expansion is one of 1 and k-1 zeroes.
         If n = 2^k * f(n/2^k) then if n/2^k is even the list size is also even and we add k zeroes so the last elemnt
         of the list equals value + k. If at the end are one (list size is odd) we just add k zeroes to the end of the list.
    * */
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
            if(r == 0) { //
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
