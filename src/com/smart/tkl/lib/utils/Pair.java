package com.smart.tkl.lib.utils;

public class Pair<K> {

    private K left;
    private K right;

    public Pair(K left, K right) {
        this.left = left;
        this.right = right;
    }

    public static <K> Pair<K> of(K left, K right) {
        return new Pair<>(left, right);
    }

    public K getLeft() {
        return left;
    }

    public K getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
