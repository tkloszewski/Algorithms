package com.smart.tkl.dynamic.example;

public class LIS {

    private final String s;
    private String lis;
    private String[][] memo;

    public LIS(String s) {
        this.s = s;
        this.memo = new String[s.length()]['z'];
    }

    public String get() {
        if(this.lis == null) {
           this.lis = longestIncreasingSubsequence(s.toCharArray(), 0, 0);
        }
        return this.lis;
    }

    private String longestIncreasingSubsequence(char[] s, int i, int min) {
        if(this.memo[i][min] != null) {
           return this.memo[i][min];
        }
        if(i == s.length - 1) {
           return s[i] > min ? "" + s[i] : "";
        }
        String result;
        if(s[i] > min) {
           String result1 = s[i] + longestIncreasingSubsequence(s, i + 1, s[i]);
           String result2 = longestIncreasingSubsequence(s, i + 1, min);
           result = result1.length() >= result2.length() ? result1 : result2;
        }
        else {
           result = longestIncreasingSubsequence(s, i + 1, min);
        }
        this.memo[i][min] = result;
        return result;
    }
}
