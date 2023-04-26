package com.smart.tkl.lib.coding.huff;

import com.smart.tkl.lib.tree.binary.BiNode;
import com.smart.tkl.lib.tree.binary.heap.MinBinaryHeap;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class HuffmanEncoder {

    private final String message;

    private final Set<Integer> charSet = new LinkedHashSet<>();
    private final int[] freqTab = new int[128];
    private final Map<Integer, String> codes = new HashMap<>();

    public HuffmanEncoder(String message) {
        this.message = message;
        this.fillStructures();
    }

    public static void main(String[] args) {
        String message = "A_DEAD_DAD_CEDED_A_BAD_BABE_A_BEADED_ABACA_BED";
        HuffmanEncoder encoder = new HuffmanEncoder(message);
        String encodedMessage = encoder.encode();
        System.out.println("Encoded: " + encodedMessage);
    }

    private void fillStructures() {
        for(char ch : this.message.toCharArray()) {
            freqTab[ch]++;
            charSet.add((int)ch);
        }
    }

    public String encode() {
        MinBinaryHeap<QueueItem> heap = new MinBinaryHeap<>(QueueItem.class);

        for(int ch : charSet) {
            CodeItem codeItem = new CodeItem((char)ch, freqTab[ch]);
            QueueItem queueItem = new QueueItem(codeItem, new BiNode<>(codeItem));
            heap.insert(queueItem);
        }

        while (heap.size() > 1) {
            QueueItem item1 = heap.deleteFirst();
            QueueItem item2 = heap.deleteFirst();
            QueueItem joinedItem = item1.join(item2);
            heap.insert(joinedItem);
        }

        BiNode<CodeItem> root = heap.getRoot().node;
        StringBuilder sb = new StringBuilder();

        if(root.isLeaf()) {
            //0 for all chars the same.
            sb.append("0".repeat(this.message.length()));
        }
        else {
            fillCodes(root, "");
            for (int ch : message.toCharArray()) {
                String code = codes.get(ch);
                if (code != null) {
                    sb.append(code);
                }
            }
        }

        return sb.toString();
    }

    private void fillCodes(BiNode<CodeItem> node, String path) {
        if(node.isLeaf()) {
           this.codes.put(node.getItem().charCode, path);
        }
        else {
           if(node.getLeft() != null) {
              fillCodes(node.getLeft(), path + '0');
           }
           if(node.getRight() != null) {
              fillCodes(node.getRight(), path + '1');
           }
        }
    }

    private static class QueueItem implements Comparable<QueueItem> {
        CodeItem codeItem;
        BiNode<CodeItem> node;

        public QueueItem(CodeItem codeItem, BiNode<CodeItem> node) {
            this.codeItem = codeItem;
            this.node = node;
        }

        public QueueItem join(QueueItem item2) {
            BiNode<CodeItem> node1 = this.node;
            BiNode<CodeItem> node2 = item2.node;

            CodeItem joinedCodeItem = this.codeItem.join(item2.codeItem);
            BiNode<CodeItem> newNode =  this.codeItem.freq <= item2.codeItem.freq ?
                    new BiNode<>(joinedCodeItem, node1, node2) : new BiNode<>(joinedCodeItem, node2, node1);

            return new QueueItem(joinedCodeItem, newNode);
        }

        @Override
        public int compareTo(QueueItem other) {
            return this.codeItem.compareTo(other.codeItem);
        }
    }

    private static class CodeItem implements Comparable<CodeItem> {
        int charCode = -1;
        String s;
        int freq;

        public CodeItem(int charCode, int freq) {
            this.charCode = charCode;
            this.s = String.valueOf(charCode);
            this.freq = freq;
        }

        public CodeItem(String s, int freq) {
            this.s = s;
            this.freq = freq;
        }

        public CodeItem join(CodeItem toJoin) {
            return new CodeItem(this.s.concat(toJoin.s), this.freq + toJoin.freq);
        }

        @Override
        public int compareTo(CodeItem other) {
            return Integer.compare(this.freq, other.freq);
        }
    }
}
