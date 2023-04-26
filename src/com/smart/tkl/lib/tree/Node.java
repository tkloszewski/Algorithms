package com.smart.tkl.lib.tree;

import java.util.List;

public class Node<V extends Comparable<V>> {

    private V item;
    private Node<V> parent;
    private List<Node<V>> children;

    public Node(V item) {
        this.item = item;
    }

    public Node(V item, List<Node<V>> children) {
        this.item = item;
        this.children = children;
    }

    public Node(V item, Node<V> parent) {
        this.item = item;
        this.parent = parent;
    }

    public Node(V item, Node<V> parent, List<Node<V>> children) {
        this.item = item;
        this.parent = parent;
        this.children = children;
    }

    public V getItem() {
        return item;
    }

    public void setItem(V item) {
        this.item = item;
    }

    public Node<V> getParent() {
        return parent;
    }

    public void setParent(Node<V> parent) {
        this.parent = parent;
    }

    public List<Node<V>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<V>> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "item=" + item +
                '}';
    }
}
