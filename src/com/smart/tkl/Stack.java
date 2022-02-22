package com.smart.tkl;

public class Stack<V> {

    private Node<V> head;

    public V pop() {
        V result = null;
        if(head != null) {
           result = head.item;
           head = head.next;
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

}
