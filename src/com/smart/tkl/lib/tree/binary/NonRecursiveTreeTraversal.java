package com.smart.tkl.lib.tree.binary;

import com.smart.tkl.lib.Stack;

import java.util.ArrayList;
import java.util.List;

public class NonRecursiveTreeTraversal {

   public static <V extends Comparable<V>> List<V> collectAllNodesFirstLeft(BiNode<V> root) {
       List<V> result = new ArrayList<>();
       BiNode<V> current = root;

       Stack<BiNode<V>> stack = new Stack<>();

       while(current != null || !stack.isEmpty()) {
           while(current != null) {
               stack.push(current);
               current = current.getLeft();
           }
           BiNode<V> popped = stack.pop();
           current = popped.getRight();
           result.add(popped.getItem());
       }
       return result;
   }

   public static <V extends Comparable<V>> List<V> collectAllNodesFirstRight(BiNode<V> root) {
        List<V> result = new ArrayList<>();
        BiNode<V> current = root;

        Stack<BiNode<V>> stack = new Stack<>();

        while(current != null || !stack.isEmpty()) {
            while(current != null) {
                stack.push(current);
                current = current.getRight();
            }
            BiNode<V> popped = stack.pop();
            current = popped.getLeft();
            result.add(popped.getItem());
        }
        return result;
    }

}
