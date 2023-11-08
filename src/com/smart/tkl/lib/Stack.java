package com.smart.tkl.lib;

public class Stack<V> {

    private Node<V> head;
    private int size;

    public V pop() {
        V result = null;
        if(head != null) {
           result = head.item;
           head = head.next;
           size--;
        }
        return result;
    }

    public V peek() {
        return head != null ? head.item : null;
    }

    public void push(V item) {
        Node<V> newHead = new Node<>(item);
        if(head == null) {
            head = newHead;
        }
        else {
           newHead.next = head;
           head = newHead;
        }
        size++;
    }

    private static class Node<V> {
        Node<V> next;
        V item;

        public Node(V item) {
            this.item = item;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void print() {
        Node<V> node = head;
        while (node != null) {
            System.out.print(node.item);
            System.out.print(" ");
            node = node.next;
        }
        System.out.println();
    }

}
