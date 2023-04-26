package com.smart.tkl.lib.tree.binary.print;

import com.smart.tkl.lib.tree.binary.AVLTree;
import com.smart.tkl.lib.tree.binary.BiNode;
import com.smart.tkl.lib.tree.binary.BinaryTree;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TriangleBranchesTreePrinter<V extends Comparable<V>> implements BinaryTreePrinter<V> {

    public static void main(String[] args) {
        TriangleBranchesTreePrinter<Integer> treePrinter = new TriangleBranchesTreePrinter<>();
        BinaryTree<Integer> avlTree = new AVLTree<>(new BiNode<>(0));

        for(int i = 1; i <= 14; i++) {
            avlTree.insert(i);
        }

        treePrinter.printTree(avlTree);

        avlTree.delete(4);
        System.out.println("After deleting 4..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);
        avlTree.delete(3);
        System.out.println("After deleting 3..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);

        avlTree.delete(5);
        System.out.println("After deleting 5..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);

        avlTree.delete(6);
        System.out.println("After deleting 6..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);

        avlTree.delete(7);
        System.out.println("After deleting 7..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);

        avlTree.delete(1);
        System.out.println("After deleting 1..." + avlTree.getRoot());
        treePrinter.printTree(avlTree);
    }

    @Override
    public void printTree(BinaryTree<V> tree) {
        if(tree == null || tree.getRoot() == null) {
           return;
        }
        int maxLevel = tree.getMaxLevel();
        int firstPosition = getFirstPosition(maxLevel);
        List<PositionedNode<V>> nodes = Collections.singletonList(new PositionedNode<>(tree.getRoot(), firstPosition));
        printTree(nodes, 0, maxLevel);
    }

    private void printTree(List<PositionedNode<V>> nodes, int level, int maxLevel) {
        int branchLength = getBranchLength(level, maxLevel);
        if(level < maxLevel) {
            List<String> levelLines = buildNodeLineWithBranches(nodes, branchLength);
            for(String levelLine : levelLines) {
                System.out.println(levelLine);
            }
            int newBranchLength = level + 1 == maxLevel ? 0 : branchLength;
            List<PositionedNode<V>> nextLevelNodes = getNextLevelNodes(nodes, newBranchLength);
            printTree(nextLevelNodes, level + 1, maxLevel);
        }
        else {
            String lastLevelNodeLine = buildLastLevelNodeLine(nodes);
            System.out.println(lastLevelNodeLine);
        }
    }

    private int getFirstPosition(int maxLevel) {
        return (int)Math.pow(2, maxLevel) - 1 + maxLevel + 2;
    }

    private int getBranchLength(int level, int maxLevel) {
        return (int)Math.pow(2, maxLevel - level - 1);
    }

    private List<PositionedNode<V>> getNextLevelNodes(List<PositionedNode<V>> nodes, int branchLength) {
        List<PositionedNode<V>> result = new ArrayList<>(nodes.size() * 2);
        for(PositionedNode<V> positionedNode : nodes) {
            if(positionedNode.node.getLeft() != null) {
                result.add(new PositionedNode<>(positionedNode.node.getLeft(), positionedNode.position - branchLength - 1));
            }
            if(positionedNode.node.getRight() != null) {
                result.add(new PositionedNode<>(positionedNode.node.getRight(), positionedNode.position + branchLength + 1));
            }
        }
        return result;
    }

    private String buildLastLevelNodeLine(List<PositionedNode<V>> nodes) {
        int maxPosition = nodes.get(nodes.size() - 1).position;
        Map<Integer, PositionedNode<V>> positionedNodeMap = toPositionNodeMap(nodes);

        StringBuilder nodeLine = new StringBuilder();
        for (int i = 0; i <= maxPosition; i++) {
            if(positionedNodeMap.containsKey(i)) {
                PositionedNode<V> positionedNode = positionedNodeMap.get(i);
                String itemString = positionedNode.node.getItem().toString();
                nodeLine.append(itemString);
                if(itemString.length() > 1) {
                   i += (itemString.length() - 1);
                }
            }
            else {
                nodeLine.append(" ");
            }
        }

        return nodeLine.toString();
    }

    private List<String> buildNodeLineWithBranches(List<PositionedNode<V>> nodes, int branchLength) {
        List<String> result = new ArrayList<>();

        int maxPosition = nodes.get(nodes.size() - 1).position;
        Map<Integer, PositionedNode<V>> positionedNodeMap = toPositionNodeMap(nodes);

        StringBuilder nodeLine = new StringBuilder();
        for (int i = 0; i <= maxPosition; i++) {
            if(positionedNodeMap.containsKey(i)) {
                PositionedNode<V> positionedNode = positionedNodeMap.get(i);
                String itemString = positionedNode.node.getItem().toString();
                nodeLine.append(itemString);
                if(itemString.length() > 1) {
                   i += itemString.length() - 1;
                }
            }
            else {
                nodeLine.append(" ");
            }
        }
        result.add(nodeLine.toString());

        for(int i = 1; i <= branchLength; i++) {
            StringBuilder branchLine = new StringBuilder();
            for(int pos = 0; pos <= maxPosition + branchLength; pos++) {
                if(positionedNodeMap.containsKey(pos + i)) {
                    PositionedNode<V> positionedNode = positionedNodeMap.get(pos + i);
                    branchLine.append(positionedNode.node.getLeft() != null ? "/" : " ");
                }
                else if(positionedNodeMap.containsKey(pos - i)) {
                    PositionedNode<V> positionedNode = positionedNodeMap.get(pos - i);
                    branchLine.append(positionedNode.node.getRight() != null ? "\\" : " ");
                }
                else {
                    branchLine.append(" ");
                }
            }
            result.add(branchLine.toString());
        }

        return result;
    }

    private Map<Integer, PositionedNode<V>> toPositionNodeMap(List<PositionedNode<V>> positionedNodes) {
        return positionedNodes.stream()
                .collect(Collectors.toMap(p -> p.position, Function.identity()));
    }

    private static class PositionedNode<V extends Comparable<V>> {
        BiNode<V> node;
        int position;

        public PositionedNode(BiNode<V> node, int position) {
            this.node = node;
            this.position = position;
        }

        @Override
        public String toString() {
            return "PositionedNode{" +
                    "node=" + node.getItem() +
                    ", position=" + position +
                    '}';
        }
    }

}
