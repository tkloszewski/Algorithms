package com.smart.tkl.tree.binary;

import java.util.Arrays;
import java.util.List;

public class TreesTest {

    public static void main(String[] args) {
        BinaryTreeBuilder<Integer> treeBuilder = new BinaryTreeBuilder<>();
        List<Integer> elements = Arrays.asList(9, 8, 7, 6, 5, 4, 3);
        BiNode<Integer> root = treeBuilder.build(elements);
        List<Integer> allNodes = NonRecursiveTreeTraversal.collectAllNodesFirstLeft(root);
        System.out.println("Left first: " + allNodes);
        allNodes = NonRecursiveTreeTraversal.collectAllNodesFirstRight(root);
        System.out.println("Right first: " + allNodes);

        InOrderTreeTraversal treeTraversal = new InOrderTreeTraversal();
    }
}
