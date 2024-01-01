package com.smart.tkl.cses.hackerrank.algorithms;

public class StringSimilarity {


    public static void main(String[] args) {
        String[] input = {
                "aacbdbaaeaa",
                "daaeddceddaccbebceccdacc",
                "baeeddbaeebbaeebcdedade",
                "ceebeedcdcadbbcaeeddabcecaeabdceebcdcaabadebbedaddaabdedaadcdadeeadbdae",
                "bdbabdaceadeaceacdbdbaeeaacdaeddccdaadcbddaaacdeabeeddaeeecbdceaeeaceddebbcd",
                "c",
                "cbbcbabaeedeadabbcaedacecceeeecdddbebeccbabbbcdcbadecaabc",
                "ddcebdcaebcdeaadbedacddcbaeccdacbcbccaedeecdeebcdeeadcebcbabe",
                "aaeecedbdeaeaeaeccdeccbdbcdedbaabbeaaebbebabccbeebbbeceabaabdcbedaeaedbabbceeaaabbccadddaae",
                "aeccecceeeacdeddcecddebbebcebcddccbbaacbbcd"
        };

        for(String s : input) {
            System.out.println(s + " similarity: " + stringSimilarity(s));
        }
    }

    public static long stringSimilarity(String s) {
        // Write your code here
        int length = s.length();
        int lastSameCharsPrefixPos = 0;
        long result = length;
        if(length > 0) {
            char firstChar = s.charAt(0);
            int i = 1;
            while(i < length && s.charAt(i) == firstChar) {
                lastSameCharsPrefixPos = i;
                i++;
            }
            long inc = ((long)(lastSameCharsPrefixPos) * (1 + lastSameCharsPrefixPos))/2;
            result += inc;
            for(; i < length; i++) {
                int pos = i, k = 0;
                long similarity = 0;
                while(pos < length && s.charAt(k) == s.charAt(pos)) {
                    pos++;
                    k++;
                    similarity++;
                }
                result += similarity;
            }

        }
        return result;
    }

}
