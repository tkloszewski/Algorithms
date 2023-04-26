package com.smart.tkl.lib.tree.binary;

public class BiNode<V extends Comparable<V>> {

    protected V item;
    protected BiNode<V> left, right;
    protected BiNode<V> parent;

    protected Integer height;
    protected Integer balanceFactor;

    public BiNode(V item) {
        this.item = item;
    }

    public BiNode(V item, BiNode<V> parent) {
        this.item = item;
        this.parent = parent;
    }

    public V getItem() {
        return item;
    }

    public void setItem(V item) {
        this.item = item;
    }

    public BiNode<V> getLeft() {
        return left;
    }

    public void setLeft(BiNode<V> left) {
        this.left = left;
    }

    public BiNode<V> getRight() {
        return right;
    }

    public void setRight(BiNode<V> right) {
        this.right = right;
    }

    public BiNode<V> getParent() {
        return parent;
    }

    public void setParent(BiNode<V> parent) {
        this.parent = parent;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    @Override
    public String toString() {
        return "{" +
                "item=" + item + ", " +
                "left=" + ((left != null) ? left.item : "null") + ", " +
                "right=" + ((right != null) ? right.item : "null") + ", " +
                "parent=" + ((parent != null) ? parent.item : "null") +
                '}';
    }
}
