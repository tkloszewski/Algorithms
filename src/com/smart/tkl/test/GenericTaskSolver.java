package com.smart.tkl.test;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

import java.util.*;

public class GenericTaskSolver {


    public static void main(String[] args) {

        List<Set<Integer>> list = new ArrayList<>();

        list.add(new HashSet<Integer>(Arrays.asList(2,4,5,6,7,8,9)));
        list.add(new HashSet<Integer>(Arrays.asList(2,3,4,5,6,8,9)));
        list.add(new HashSet<Integer>(Arrays.asList(2,3,4,5,6,7,8)));

        List<Integer> lastTestDigits = Arrays.asList(3, 7, 9);


        for(int i = 0; i < lastTestDigits.size(); i++) {
            int lastDigit = lastTestDigits.get(i);
            List<int[]> combinations = new ArrayList<int[]>(CombinatoricsUtils.combinations(list.get(i), 4));
            for(int k = 0; k < combinations.size(); k++) {
                int[] combination = combinations.get(k);
                List<int[]> permutations = generatePermutations(combination, 0);
                for(int p = 0; p < permutations.size(); p++) {
                    int[] permutation = permutations.get(p);
                    int number = makeNumber(permutation, lastDigit);
                    int table[][] = generateTable(number);
                    boolean result = testTable(table);

                    if(result) {
                        System.out.println("Found valid number: " + number);
                        for(int l = 0; l <=5 ;l++) {
                            printTable(table[l]);
                        }
                        return;
                    }
                }
            }
        }
    }

    private static boolean testTable(int table[][]) {
        for(int i = 0; i < 6; i++) {
            Set<Integer> uniqueRow = new HashSet<>();
            for(int j = 0 ; j < 6; j++) {
                if(!uniqueRow.add(table[i][j])) {
                    return false;
                }
            }
        }
        for(int i = 0; i < 6; i++) {
            Set<Integer> uniqueColumn = new HashSet<>();
            for(int j = 0 ; j < 6; j++) {
                if(!uniqueColumn.add(table[j][i])) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int makeNumber(int[] combination, int lastDigit) {
        int middleNum = combination[0] * 1000 + combination[1] * 100 + combination[2] * 10 + combination[3];
        middleNum = middleNum * 10;
        return 100000 + middleNum + lastDigit;
    }

    private static int[][] generateTable(int number) {
        int[][] table = new int[6][6];
        for(int i = 1; i<=6 ;i ++) {
            int currentNum = number * i;
            for(int j = 5; j >= 0; j--) {
                table[i-1][j] = currentNum % 10;
                currentNum = currentNum/10;
            }
        }
        return table;
    }

    private static List<int[]> generatePermutations(int[] t, int pos) {
        List<int[]> result = new ArrayList<>();
        if(t.length == 1) {
            result.add(t.clone());
            return result;
        }
        else if(pos == t.length - 2) {
            result.add(t.clone());
            swap(t, pos, pos + 1);
            result.add(t.clone());
            swap(t, pos, pos + 1);
            return result;
        }
        else {
            result.addAll(generatePermutations(t, pos + 1));
            for(int i = pos + 1; i < t.length; i++) {
                swap(t, pos, i);
                result.addAll(generatePermutations(t, pos + 1));
                swap(t, pos, i);
            }
            return result;
        }
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
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
