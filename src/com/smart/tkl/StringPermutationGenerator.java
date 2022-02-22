package com.smart.tkl;

public class StringPermutationGenerator {

    public static void generatePermutations(String s) {
        perm("", s, (str) -> System.out.println(str));
    }

    private static void perm(String prefix, String s, StringPermutationListener listener) {
        int n = s.length();
        if(n == 0) {
            listener.permutation(prefix);
        }
        else {
            for(int i = 0; i < n; i++) {
                perm(prefix + s.charAt(i), s.substring(0,i) + s.substring(i+1,n), listener);
            }
        }
    }

    public interface StringPermutationListener {
        void permutation(String s);
    }
}



