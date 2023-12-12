package com.smart.tkl.euler.p185;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NumberMindRandomized {

    private final int length;
    private final List<Sequence> sequences;
    private final LinkedHashSet<Integer>[] excluded;

    private int[] current;

    private Random random;

    public NumberMindRandomized(int length, List<Sequence> sequences) {
        this.length = length;
        this.sequences = sequences;
        this.excluded = createExcluded(length, sequences);
        this.random = new Random();
        this.current = initializeRandomSequence(length, random);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<Sequence> sequences = List.of(
                new Sequence(toArray("90342"), 2),
                new Sequence(toArray("70794"), 0),
                new Sequence(toArray("39458"), 2),
                new Sequence(toArray("34109"), 1),
                new Sequence(toArray("51545"), 2),
                new Sequence(toArray("12531"), 1));

        NumberMindRandomized numberMind = new NumberMindRandomized(5, sequences);
        int[] foundSequence = numberMind.findSequence();
        long time2 = System.currentTimeMillis();
        System.out.println("Found sequence: " + Arrays.toString(foundSequence));
        System.out.println("Time in ms: " + (time2 - time1));

        int simulations = 100;
        long totalTime = 0;
        for(int i = 0; i < simulations; i++) {
            time1 = System.currentTimeMillis();
            sequences = List.of(
                    new Sequence(toArray("5616185650518293"), 2),
                    new Sequence(toArray("3847439647293047"), 1),
                    new Sequence(toArray("5855462940810587"), 3),
                    new Sequence(toArray("9742855507068353"), 3),
                    new Sequence(toArray("4296849643607543"), 3),
                    new Sequence(toArray("3174248439465858"), 1),
                    new Sequence(toArray("4513559094146117"), 2),
                    new Sequence(toArray("7890971548908067"), 3),
                    new Sequence(toArray("8157356344118483"), 1),
                    new Sequence(toArray("2615250744386899"), 2),
                    new Sequence(toArray("8690095851526254"), 3),
                    new Sequence(toArray("6375711915077050"), 1),
                    new Sequence(toArray("6913859173121360"), 1),
                    new Sequence(toArray("6442889055042768"), 2),
                    new Sequence(toArray("2321386104303845"), 0),
                    new Sequence(toArray("2326509471271448"), 2),
                    new Sequence(toArray("5251583379644322"), 2),
                    new Sequence(toArray("1748270476758276"), 3),
                    new Sequence(toArray("4895722652190306"), 1),
                    new Sequence(toArray("3041631117224635"), 3),
                    new Sequence(toArray("1841236454324589"), 3),
                    new Sequence(toArray("2659862637316867"), 2));

            numberMind = new NumberMindRandomized(16, sequences);

            foundSequence = numberMind.findSequence();
            time2 = System.currentTimeMillis();
            System.out.println("Found sequence: " + toString(foundSequence));
            System.out.println("Time in ms: " + (time2 - time1));
            totalTime += (time2 - time1);
        }

        long avgTimeInMs = totalTime / simulations;
        System.out.println("Avg time: " + avgTimeInMs);
    }

    public int[] findSequence() {
        List<Sequence> sequencesWithCorrectValue = this.sequences.stream()
                .filter(s -> s.correct > 0)
                .collect(Collectors.toList());


        final int localMinimumThreshold = 10;

        int error = evalError(sequencesWithCorrectValue, current);
        int stuckInLocalMinCount = 0;

        while (error > 0) {
            int prevError = error;
            for(int pos = 0; pos < length; pos++) {
                int value = this.current[pos];
                int newValue = generateRandom(value, pos);
                this.current[pos] = newValue;
                int newError = evalError(sequencesWithCorrectValue, current);
                if(newError < error) {
                    error = newError;
                }
                else {
                    this.current[pos] = value;
                }
            }
            if(prevError == error) {
                stuckInLocalMinCount++;
                if(stuckInLocalMinCount >= localMinimumThreshold) {
                    int randomPos = random.nextInt(this.length);
                    current[randomPos] = generateRandom(current[randomPos], randomPos);
                    error = evalError(sequencesWithCorrectValue, current);
                    stuckInLocalMinCount = 0;
                }
            }
            else {
                stuckInLocalMinCount = 0;
            }
        }

        return current;
    }

    private int generateRandom(int currentValue, int pos) {
        int newRandom = random.nextInt(10);
        while (newRandom == currentValue || (excluded != null && excluded[pos].contains(newRandom))) {
            newRandom = random.nextInt(10);
        }
        return newRandom;
    }

    private static int evalError(List<Sequence> sequences, int[] currentSequence) {
        int error = 0;
        for(Sequence sequence : sequences) {
            error += evalError(sequence, currentSequence);
        }
        return error;
    }

    private static int evalError(Sequence sequence, int[] currentSequence) {
        int same = 0;
        for(int i = 0; i < currentSequence.length; i++) {
            if(sequence.values[i] == currentSequence[i]) {
                same++;
            }
        }
        return Math.abs(sequence.correct - same);
    }

    private static LinkedHashSet<Integer>[] createExcluded(int length, List<Sequence> sequences) {
        List<Sequence> sequencesWithNoCorrectValues = sequences.stream()
                .filter(s -> s.correct == 0)
                .collect(Collectors.toList());

        if(!sequencesWithNoCorrectValues.isEmpty()) {
            LinkedHashSet<Integer>[] excluded = new LinkedHashSet[length];
            for(int i = 0; i < excluded.length; i++) {
                excluded[i] = new LinkedHashSet<>();
            }
            for(Sequence sequence: sequencesWithNoCorrectValues) {
                for(int i = 0; i < sequence.values.length; i++) {
                    excluded[i].add(sequence.values[i]);
                }
            }
            return excluded;
        }
        return null;
    }

    private static int[] initializeRandomSequence(int length, Random random) {
        int[] result = new int[length];
        for(int i = 0; i < length; i++) {
            result[i] = random.nextInt(10);
        }
        return result;
    }

    private static int[] toArray(String s) {
        int[] array = new int[s.length()];
        for(int i = 0; i < s.length(); i++) {
            array[i] = s.charAt(i) - '0';
        }
        return array;
    }

    private static String toString(int[] array) {
        StringBuilder s = new StringBuilder();
        for(int val : array) {
            s.append(val);
        }
        return s.toString();
    }

    /*4, 6, 4, 0, 2, 6, 1, 5, 7, 1, 8, 4, 9, 5, 3, 3*/
}
