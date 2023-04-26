package com.smart.tkl.lib.dynamic.example;

import java.util.*;

public class Examples1 {

    public static void main(String[] args) {
        int[] output = howSum(300, new int[]{7, 14, 18}, new HashMap<>());
        int[] bestSum = bestSum(100, new int[]{1, 2, 5, 25}, new HashMap<>());
        int[] bestSumTab = bestSumTab(100, new int[]{1, 2, 5, 19});
        System.out.println("How sum: " + Arrays.toString(output));
        System.out.println("Best sum: " + Arrays.toString(bestSum));
        System.out.println("Best sum tab: " + Arrays.toString(bestSumTab));


        System.out.println("Can construct: " + canConstruct("skateboard", new String[]{"bo","rd","ate","t","ska", "sk", "board"}, new HashMap<>()));
        System.out.println("Can construct tab skateboard: " + canConstructTab("skateboard", new String[]{"bo","rd","ate","t","ska", "sk", "board"}));
        System.out.println("Can construct tab skateboard 2: " + canConstructTab("skateboard", new String[]{"bo","rd","ate","t","ska", "sk", "boar"}));
        System.out.println("Can construct tab abcdef: " + canConstructTab("abcdef", new String[]{"ab","abc","cd","def","abcd"}));
        System.out.println("Can construct tab2 skateboard: " + canConstructTab2("skateboard", new String[]{"bo","rd","ate","t","ska", "sk", "board"}));
        System.out.println("Can construct tab2 skateboard 2: " + canConstructTab2("skateboard", new String[]{"bo","rd","ate","t","ska", "sk", "boar"}));
        System.out.println("Can construct tab2 abcdef: " + canConstructTab2("abcdef", new String[]{"ab","abc","cd","def","abcd"}));


        System.out.println("Count construct: " + countConstruct("purple", new String[]{"purp", "p", "ur", "le", "purpl"}, new HashMap<>()));
        System.out.println("Count construct tab: " + countConstructTab("purple", new String[]{"purp", "p", "ur", "le", "purpl"}));

        List<String[]> ways = allConstruct("purple", new String[]{"purp", "p", "ur", "le", "purpl"});
        System.out.println("Ways to construct purple:");
        for(String[] way : ways) {
            System.out.println("way: " + Arrays.toString(way));
        }

        ways = allConstructTab("purple", new String[]{"purp", "p", "ur", "le", "purpl"});
        System.out.println("Ways to construct purple (tab version):");
        for(String[] way : ways) {
            System.out.println("way: " + Arrays.toString(way));
        }


        ways = allConstruct("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd", "ef", "c"});
        System.out.println("Ways to construct abcdef:");
        for(String[] way : ways) {
            System.out.println("way: " + Arrays.toString(way));
        }

        ways = allConstructTab("abcdef", new String[]{"ab", "abc", "cd", "def", "abcd", "ef", "c"});
        System.out.println("Ways to construct abcdef (tab version):");
        for(String[] way : ways) {
            System.out.println("way: " + Arrays.toString(way));
        }

        System.out.println("Fib 6: " + fibTab(50));
        System.out.println("Can sum tab1: " + canSumTab(300, new int[]{7, 14, 18}));
        System.out.println("Can sum tab2: " + canSumTab(7, new int[]{5, 3, 4}));

        int[] howSumTabResult = howSumTab(100, new int[]{1, 2, 5, 25});
        System.out.println("How sum tab 100: " + Arrays.toString(howSumTabResult));

        howSumTabResult = howSumTab(300, new int[]{7, 14, 18});
        System.out.println("How sum tab 300: " + Arrays.toString(howSumTabResult));
    }

    public static List<String[]> allConstructTab(String target, String[] words) {
        List<String[]>[] tab = new List[target.length() + 1];
        tab[0] = new ArrayList<>();
        for(int i = 0; i <= target.length(); i++) {
            if(tab[i] != null) {
                List<String[]> existingWays = tab[i];
                for(String word : words) {
                    if(i + word.length() < tab.length && target.substring(i).startsWith(word)) {
                        if(tab[i + word.length()] == null) {
                            tab[i + word.length()] = new ArrayList<>();
                        }
                        List<String[]> targetList = tab[i + word.length()];
                        if(existingWays.size() == 0) {
                            targetList.add(new String[]{word});
                        }
                        else {
                            for (String[] ways : existingWays) {
                                String[] newWays = Arrays.copyOf(ways, ways.length + 1);
                                newWays[newWays.length - 1] = word;
                                targetList.add(newWays);
                            }
                        }
                    }
                }
            }
            System.out.println("i= " + i);
        }

        for(int i = 1; i < tab.length; i++) {
            if(tab[i] != null) {
                System.out.println("Ways to construct: " + target.substring(0, i));
                for(String[] ways : tab[i]) {
                    System.out.println("way: " + Arrays.toString(ways));
                }
            }
        }

        return tab[target.length()];
    }

    public static int countConstructTab(String target, String[] words) {
        int[] tab = new int[target.length() + 1];
        tab[0] = 1;
        for(int i = 0; i <= target.length(); i++) {
            for(String word : words) {
                if(i + word.length() < tab.length && target.substring(i).startsWith(word)) {
                    tab[i + word.length()] += tab[i];
                }
            }
        }

        return tab[target.length()];
    }

    public static boolean canConstructTab2(String target, String[] words) {
        boolean[] tab = new boolean[target.length() + 1];
        tab[0] = true;
        for(int i = 0; i <= target.length(); i++) {
            if(tab[i]) {
                for(String word : words) {
                    if(i + word.length() < tab.length && target.substring(i).startsWith(word)) {
                        tab[i + word.length()] = true;
                    }
                }
            }
        }
        return tab[target.length()];
    }

    public static boolean canConstructTab(String target, String[] words) {
        String[] tab = new String[target.length() + 1];
        tab[0] = "";
        for(int i = 0; i <= target.length(); i++) {
            String prefix = tab[i];
            if(prefix != null) {
                for(String word : words) {
                    if(i + word.length() <= target.length() && target.startsWith(prefix + word)) {
                        tab[i + word.length()] = prefix + word;
                    }
                }
            }
        }
        return tab[target.length()] != null && tab[target.length()].equals(target);
    }

    public static int[] bestSumTab(int target, int[] nums) {
        int[][] result = new int[target + 1][];
        result[0] = new int[0];
        for(int i = 0; i <= target; i++) {
            if(result[i] != null) {
                int newLength = result[i].length + 1;
                for(int num : nums) {
                    if(i + num <= target) {
                        if(result[i + num] == null || result[i + num].length > newLength) {
                            int[] newTab = Arrays.copyOf(result[i], newLength);
                            newTab[newLength - 1] = num;
                            result[i + num] = newTab;
                        }
                    }
                }
            }
        }
        return result[target];
    }

    public static int[] howSumTab(int target, int[] nums) {
        int[][] result = new int[target + 1][];
        result[0] = new int[0];
        for(int i = 0; i <= target; i++) {
            if(result[i] != null) {
                for(int num : nums) {
                    if(i + num <= target && result[i + num] == null) {
                        int[] newTab = Arrays.copyOf(result[i], result[i].length + 1);
                        newTab[newTab.length - 1] = num;
                        result[i + num] = newTab;
                    }
                }
                if(result[target] != null) {
                    return result[target];
                }
            }
        }
        return result[target];
    }

    public static boolean canSumTab(int target, int[] nums) {
        boolean[] tab = new boolean[target + 1];
        tab[0] = true;
        for(int num : nums) {
            tab[num] = true;
        }
        for(int i = 1; i <= target; i++) {
            if(tab[i]) {
                for(int num : nums) {
                    if(i + num <= target) {
                        tab[i + num] = true;
                    }
                }
            }
            if(tab[target]) {
                return true;
            }
        }
        return tab[target];
    }

    public static long fibTab(int n) {
        long[] tab = new long[n + 1];
        tab[1] = 1;
        for(int i = 2; i <= n; i++) {
            tab[i] = tab[i - 1] + tab[i - 2];
        }
        return tab[n];
    }

    public static List<String[]> allConstruct(String target, String[] words) {
        List<String[]> result = allConstruct(target, words, new HashMap<>());
        return result != null ? result : new ArrayList<>();
    }

    public static List<String[]> allConstruct(String target, String[] words, Map<String, List<String[]>> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        if("".equals(target)) {
            return new ArrayList<>();
        }

        List<String[]> result = null;

        for(String word : words) {
            if(target.startsWith(word)) {
                List<String[]> subResults = allConstruct(target.substring(word.length()), words, memo);
                if(subResults != null) {
                    if(result == null) {
                        result = new ArrayList<>();
                    }
                    if(subResults.size() == 0) {
                        result.add(new String[]{word});
                    }
                    else {
                        for (String[] subResult : subResults) {
                            String[] ways = new String[subResult.length + 1];
                            ways[0] = word;
                            int pos = 1;
                            for (String w : subResult) {
                                ways[pos++] = w;
                            }
                            result.add(ways);
                        }
                    }
                }
            }
        }
        memo.put(target, result);
        return result;
    }

    public static int countConstruct(String target, String[] words, Map<String, Integer> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        if("".equals(target)) {
            return 1;
        }

        int result = 0;

        for(String word : words) {
            if(target.startsWith(word)) {
                int count = countConstruct(target.substring(word.length()), words, memo);
                result += count;
            }
        }
        memo.put(target, result);
        return result;
    }

    public static boolean canConstruct(String target, String[] words, Map<String, Boolean> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        if("".equals(target)) {
            return true;
        }
        for(String word : words) {
            if(target.startsWith(word)) {
                if(canConstruct(target.substring(word.length()), words, memo)) {
                    memo.put(target, true);
                    return true;
                }
            }
        }
        memo.put(target, false);
        return false;
    }

    public static int[] bestSum(int target, int[] input, Map<Integer,int[]> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        if(target == 0) {
            return new int[0];
        }
        if(target < 0) {
            return null;
        }

        int[] result = null;
        int shortest = Integer.MAX_VALUE;

        for(int num : input) {
            int[] output = bestSum(target - num, input, memo);
            if(output != null && output.length < shortest) {
                result = Arrays.copyOf(output, output.length + 1);
                result[result.length - 1] = num;
                shortest = output.length;
            }
        }

        memo.put(target, result);

        return result;
    }

    public static int[] howSum(int target, int[] input, Map<Integer,int[]> memo) {
        if(memo.containsKey(target)) {
            return memo.get(target);
        }
        boolean targetMatch = false;
        for(int num : input) {
            if(target == num) {
                return new int[]{num};
            }
            else if(target > num) {
                targetMatch = true;
                break;
            }
        }

        if(!targetMatch) {
            return null;
        }

        int[] result = null;

        for(int num : input) {
            int[] output = howSum(target - num, input, memo);
            if(output != null) {
                result = Arrays.copyOf(output, output.length + 1);
                result[result.length - 1] = num;
                break;
            }
        }

        memo.put(target, result);

        return result;
    }
}
