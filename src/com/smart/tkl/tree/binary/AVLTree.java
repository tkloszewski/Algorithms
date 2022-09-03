package com.smart.tkl.tree.binary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AVLTree<V extends Comparable<V>> extends BinarySearchTree<V> {

    public AVLTree(BiNode<V> root) {
        super(root);
        this.root.height = 0;
        this.root.balanceFactor = 0;
    }

    public AVLTree() {
        this.root.height = -1;
        this.root.balanceFactor = 0;
    }

    public static void main(String[] args) {
        final int limit = (1 << 10) - 2;
        AVLTree<Integer> tree = new AVLTree<>(new BiNode<>(0));
        List<Integer> list = new ArrayList<>(limit + 1);

        for(int i = 1; i <= limit; i++) {
            tree.insert(i);
            list.add(i);
        }

        long time1 = System.currentTimeMillis();
        BiNode<Integer> found = tree.find(limit);
        long time2 = System.currentTimeMillis();
        System.out.println("Finding element in tree: " + found + " took time in ms: " + (time2 - time1));

        System.out.println("Tree height: " + tree.getMaxLevel());
        System.out.println("Node 1000: " + tree.find(limit / 2));

        List<Integer> nodes = tree.getAll(TraversalOrder.BREADTH_FIRST).stream()
                .map(n -> n.item).collect(Collectors.toList());
        System.out.println("Breadth first nodes: " + nodes);

        time1 = System.currentTimeMillis();
        int index = list.indexOf(limit );
        time2 = System.currentTimeMillis();
        System.out.println("Finding element in list: " + index + " took time in ms: " + (time2 - time1));
    }

    @Override
    public void delete(BiNode<V> node) {
        BiNode<V> parent = node.parent;

        boolean leftChild = parent != null && parent.left == node;

        if(node.left == null) {
            subtreeShift(node, node.right);
        }
        else if(node.right == null) {
            subtreeShift(node, node.left);
        }
        else {
            BiNode<V> successor = getSuccessor(node);
            if (successor != null) {
                swapItem(node, successor);

                parent = successor.parent;
                leftChild = parent != null && parent.left == successor;

                subtreeShift(successor, successor.right);
            }
        }

        BiNode<V> child = null;
        while (parent != null) {
            updateHeightAndBalanceFactor(parent);
            if(parent.balanceFactor == 0) {
                break;
            }
            if(leftChild && parent.balanceFactor == 2) {
                if(parent.right.balanceFactor >= 0) {
                    parent = rotateLeft(parent, parent.right);
                }
                else {
                    parent = rotateRightLeft(parent, parent.right);
                }
            }
            else if(parent.balanceFactor == -2) {
                if(parent.left.balanceFactor <= 0) {
                    parent = rotateRight(parent, parent.left);
                }
                else {
                    parent = rotateLeftRight(parent, parent.left);
                }
            }

            child = parent;
            parent = parent.parent;
            leftChild = parent != null && parent.left == child;
        }
    }

    @Override
    protected void insert(BiNode<V> node) {
        super.insert(node);
        updateHeightAndBalanceFactor(node);

        BiNode<V> child = node;
        BiNode<V> parent = node.parent;

        while (parent != null) {
            updateHeightAndBalanceFactor(parent);
            if(parent.balanceFactor == 0) {
                break;
            }
            if(parent.right == child && parent.balanceFactor == 2) {
                if(child.balanceFactor >= 0) {
                    parent = rotateLeft(parent, child);
                }
                else {
                    parent = rotateRightLeft(parent, child);
                }
            }
            else if(parent.left == child && parent.balanceFactor == -2) {
                if(child.balanceFactor <= 0) {
                    parent = rotateRight(parent, child);
                }
                else {
                    parent = rotateLeftRight(parent, child);
                }
            }
            child = parent;
            parent = parent.parent;
        }
    }

    @Override
    public void insertAfter(BiNode<V> node, BiNode<V> newNode) {
        throw new UnsupportedOperationException("This operation is not supported in self balancing AVL Tree");
    }

    @Override
    public void insertBefore(BiNode<V> node, BiNode<V> newNode) {
        throw new UnsupportedOperationException("This operation is not supported in self balancing AVL Tree");
    }

    /*Move right child up and current subtree root down and update height and balance factor*/
    private BiNode<V> rotateLeft(BiNode<V> subtreeRoot, BiNode<V> rightChild) {
        BiNode<V> subtreeRootParent = subtreeRoot.parent;

        subtreeRoot.right = rightChild.left;
        if(rightChild.left != null) {
            rightChild.left.parent = subtreeRoot;
        }
        subtreeRoot.parent = rightChild;

        if(subtreeRootParent != null) {
            if(subtreeRootParent.right == subtreeRoot) {
                subtreeRootParent.right = rightChild;
            }
            else {
                subtreeRootParent.left = rightChild;
            }
        }

        rightChild.left = subtreeRoot;
        rightChild.parent = subtreeRootParent;


        updateHeightAndBalanceFactor(subtreeRoot);
        updateHeightAndBalanceFactor(rightChild);

        if(this.root == subtreeRoot) {
            this.root = rightChild;
        }

        return rightChild;
    }

    /*Move left child up and current subtree root down and update height and balance factor*/
    private BiNode<V> rotateRight(BiNode<V> subtreeRoot, BiNode<V> leftChild) {
        BiNode<V> subtreeRootParent = subtreeRoot.parent;

        subtreeRoot.left = leftChild.right;
        if(leftChild.right != null) {
            leftChild.right.parent = subtreeRoot;
        }
        subtreeRoot.parent = leftChild;

        if(subtreeRootParent != null) {
            if(subtreeRootParent.right == subtreeRoot) {
                subtreeRootParent.right = leftChild;
            }
            else {
                subtreeRootParent.left = leftChild;
            }
        }

        leftChild.right = subtreeRoot;
        leftChild.parent = subtreeRootParent;

        updateHeightAndBalanceFactor(subtreeRoot);
        updateHeightAndBalanceFactor(leftChild);

        if(this.root == subtreeRoot) {
            this.root = leftChild;
        }

        return leftChild;
    }

    private BiNode<V> rotateRightLeft(BiNode<V> subtreeRoot, BiNode<V> rightChild) {
        BiNode<V> leftRightChild = rotateRight(rightChild, rightChild.left);
        return rotateLeft(subtreeRoot, leftRightChild);
    }

    private BiNode<V> rotateLeftRight(BiNode<V> subtreeRoot, BiNode<V> leftChild) {
        BiNode<V> rightLeftChild = rotateLeft(leftChild, leftChild.right);
        return rotateRight(subtreeRoot, rightLeftChild);
    }

    private void updateHeightAndBalanceFactor(BiNode<V> node) {
        node.height = treeHeight(node);
        node.balanceFactor = balanceFactor(node);
    }

    private int balanceFactor(BiNode<V> node) {
        return treeHeight(node.right) - treeHeight(node.left);
    }

}
