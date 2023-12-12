package com.smart.tkl.euler.p185;

import java.util.LinkedHashSet;
import java.util.List;

public class NumberMind {

    private final int length;
    private final List<Sequence> sequences;
    private final LinkedHashSet<IndexedValue> candidates = new LinkedHashSet<>();
    private final LinkedHashSet<Integer>[] excluded;

    public NumberMind(int length, List<Sequence> sequences) {
        this.length = length;
        this.sequences = sequences;
        this.excluded = new LinkedHashSet[length];
    }

    private boolean evaluate(Sequence sequence) {
        int correctLeft = sequence.correct;
        int positionsLeft = sequence.values.length;
        for(IndexedValue candidate : this.candidates) {
            if(candidate.value == sequence.values[candidate.index]) {
               correctLeft--;
               if(correctLeft < 0) {
                  return false;
               }
            }
        }

        if(correctLeft > this.length - this.candidates.size()) {
           return false;
        }

        int incorrect = 0;
        for(int pos = 0; pos < this.length; pos++) {
            if(excluded[pos].contains(sequence.values[pos])) {
               incorrect++;
               if(this.length - incorrect < sequence.correct) {
                  return false;
               }
            }
        }

        return true;
    }

    private static class Sequence {
        final int[] values;
        final int correct;

        public Sequence(int[] sequence, int correct) {
            this.values = sequence;
            this.correct = correct;
        }
    }

    private static class IndexedValue {
        int value;
        int index;

        public IndexedValue(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

}
