package com.smart.tkl.tree.binary.print;

import com.smart.tkl.tree.binary.BinaryTree;

public interface BinaryTreePrinter<V extends Comparable<V>> {

    void printTree(BinaryTree<V> binaryTree);
}
