package com.smart.tkl;

import java.util.LinkedList;
import java.util.List;

public class LuckyNumberGenerator {

    private final int limit;

    public LuckyNumberGenerator(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        System.out.println(new LuckyNumberGenerator(1000).generate());
    }

    public List<Integer> generate() {
       List<Integer> result = new LinkedList<>();
       LuckyNode first = new LuckyNode(1);
       LuckyNode current = first;
       for(int i = 3; i <= limit; i += 2) {
           current.next = new LuckyNode(i);
           current = current.next;
       }

       LuckyNode survivor = first.next;
       while (survivor != null) {
           current = first;
           int count = 1;
           LuckyNode prev = null;
           while (current != null) {
               if(count % survivor.value == 0) {
                  if(current.next != null && prev != null) {
                     prev.next = current.next;
                  }
               }
               prev = current;
               current = current.next;
               count++;
           }
           survivor = survivor.next;
       }

       current = first;
       while (current != null) {
           result.add(current.value);
           current = current.next;
       }

       return result;
    }

    private static final class LuckyNode {
        int value;
        LuckyNode next;

        public LuckyNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "LuckyNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
