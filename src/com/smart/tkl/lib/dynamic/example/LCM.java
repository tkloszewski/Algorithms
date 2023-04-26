package com.smart.tkl.lib.dynamic.example;

public class LCM {

    private String a;
    private String b;
    private String lcm;

    private String[][] memo;

    public LCM(String a, String b) {
        this.a = a;
        this.b = b;
        this.memo = new String[a.length()][b.length()];
    }

    public String get() {
        if(lcm == null) {
            lcm = longestCommonSubsequence(a.toCharArray(), b.toCharArray(), 0, 0);
        }
        return lcm;
    }

    private String longestCommonSubsequence(char[] a, char[] b, int i, int j) {
        if(i >= a.length || j >= b.length) {
            return "";
        }
        if(memo[i][j] != null) {
            return memo[i][j];
        }

        String result;
        if(a[i] == b[j]) {
            result = a[i] + longestCommonSubsequence(a, b, i + 1, j + 1);
        }
        else {
            String result1 = longestCommonSubsequence(a, b, i, j + 1);
            String result2 = longestCommonSubsequence(a, b, i + 1, j);
            result = result1.length() > result2.length() ? result1 : result2;
        }

        memo[i][j] = result;

        return result;
    }
}
