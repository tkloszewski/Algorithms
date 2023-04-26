package com.smart.tkl.lib.tree.binary.print;

import com.smart.tkl.lib.tree.binary.BinaryTree;

public interface BinaryTreePrinter<V extends Comparable<V>> {

    void printTree(BinaryTree<V> binaryTree);
}
