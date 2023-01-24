package com.smart.tkl.euler.p156;

import java.util.List;

public class BinarySearch {

    public static void main(String[] args) {
        int found1 = search(List.of(1, 3, 5, 7, 9, 11, 13, 15), 9);
        int found2 = search(List.of(1, 3, 5, 7, 9, 11, 13, 15), 11);
        System.out.println("Found1: " + found1);
        System.out.println("Found2: " + found2);
    }

    public static int search(List<Integer> list, int value) {
        return search(list, value, 0, list.size() - 1);
    }

    private static int search(List<Integer> list, int value, int left, int right) {
        if(list.get(left) == value) {
           return left;
        }
        if(list.get(right) == value) {
           return right;
        }
        int middle = (left + right) / 2;
        if(value > list.get(left) && value < list.get(middle)) {
           return search(list, value, left, middle);
        }
        else if(value >= list.get(middle) && value < list.get(right)) {
           return search(list, value, middle, right);
        }
        return -1;
    }
}
