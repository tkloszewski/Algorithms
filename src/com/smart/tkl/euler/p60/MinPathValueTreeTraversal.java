package com.smart.tkl.euler.p60;

import com.smart.tkl.tree.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MinPathValueTreeTraversal {

    private final int depth;

    public MinPathValueTreeTraversal(int depth) {
        this.depth = depth;
    }

    public LinkedList<Node<Long>> visitAll(Node<Long> root) {
        return visitAll(root, 1);
    }

    private LinkedList<Node<Long>> visitAll(Node<Long> current, int level) {
        if(level == this.depth) {
           return new LinkedList<>(Arrays.asList(current));
        }

        if(current.getChildren() != null) {
           LinkedList<Node<Long>> minPath = null;
           for(Node<Long> child : current.getChildren()) {
               LinkedList<Node<Long>> path = visitAll(child, level + 1);
               if(path != null) {
                  Long sum = getSum(path);
                  if(minPath == null || getSum(minPath) > sum) {
                      minPath = path;
                  }
               }
           }
           if(minPath != null) {
              minPath.addFirst(current);
              return minPath;
           }
        }

        return null;
    }

    private Long getSum(List<Node<Long>> list) {
        return list.stream().map(Node::getItem).reduce(0L, Long::sum);
    }
}
