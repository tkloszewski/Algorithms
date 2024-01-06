package com.smart.tkl.euler.p198;

import com.smart.tkl.lib.Stack;
import com.smart.tkl.lib.utils.Fraction;

public class AmbiguousNumbers {

    private final Fraction leftBound;
    private final Fraction rightBound;
    private final long limit;

    public AmbiguousNumbers(Fraction leftBound, Fraction rightBound, long limit) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        AmbiguousNumbers ambiguousNumbers = new AmbiguousNumbers(new Fraction(0, 1), new Fraction(1, 100), 100000000);
        long count = ambiguousNumbers.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long result = 0;
        Term left = new Term(0, 1);
        Term right = new Term(1, 1);
        Range range = new Range(left, right);

        Stack<Range> stack = new Stack<>();
        stack.push(range);

        while (!stack.isEmpty()) {
            range = stack.pop();
            left = range.left;
            right = range.right;

            if(left.p * rightBound.getDenominator() > left.q * rightBound.getNumerator()) {
               continue;
            }

            if(right.p * leftBound.getDenominator() < right.q * leftBound.getNumerator()) {
               continue;
            }

            long ambQ = left.q * right.q * 2;
            if(ambQ <= limit) {
               long ambP = left.p * right.q + left.q * right.p;
               if(ambP * rightBound.getDenominator() < ambQ * rightBound.getNumerator()) {
                   result++;
               }
               Term mediant = new Term(left.p + right.p, left.q + right.q);
               stack.push(new Range(left, mediant));
               stack.push(new Range(mediant, right));
            }
        }

        return result;
    }

    private static class Range {
        Term left, right;

        public Range(Term left, Term right) {
            this.left = left;
            this.right = right;
        }
    }

    private static class Term {
        long p, q;

        public Term(long p, long q) {
            this.p = p;
            this.q = q;
        }

        @Override
        public String toString() {
            return "Term{" +
                    "p=" + p +
                    ", q=" + q +
                    '}';
        }
    }
}
