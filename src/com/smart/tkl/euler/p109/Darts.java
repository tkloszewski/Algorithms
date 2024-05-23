package com.smart.tkl.euler.p109;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Darts {

    private final List<Integer> singles = new ArrayList<>();
    private final List<Integer> doubles = new ArrayList<>();
    private final List<Integer> triples = new ArrayList<>();
    private final Set<Integer> singlesSet = new HashSet<>();
    private final Set<Integer> doublesSet = new HashSet<>();
    private final Set<Integer> triplesSet = new HashSet<>();

    private final int limit;

    public Darts(int limit) {
        this.limit = limit;
        initGame();
    }

    public static void main(String[] args) {
        Darts darts = new Darts(100000);
        long time1 = System.currentTimeMillis();
        int totalCount = darts.totalCount();
        long time2 = System.currentTimeMillis();
        System.out.println("Total count: " + totalCount);
        System.out.println("Total time in ms: " + (time2 - time1));
    }

    public int totalCount() {
        int result = 0;
        for(int i = 1; i < limit; i++) {
            int scoreWays = countScoresForCheckout(i);
            result += scoreWays;
        }
        return result;
    }

    private int countScoresForCheckout(int checkout) {
        int count = 0;
        for(int doubleScore : doubles) {
            if(doubleScore < checkout) {
                int twoScoresCount = countTwoScoresForValue(checkout - doubleScore);
                count += twoScoresCount;
            }
            else {
                if(doubleScore == checkout) {
                   count++;
                }
                break;
            }
        }
        return count;
    }

    private int countTwoScoresForValue(int value) {
        int count = 0;
        if(singlesSet.contains(value)) {
            count++;
        }
        if(doublesSet.contains(value)) {
            count++;
        }
        if(triplesSet.contains(value)) {
            count++;
        }

        int doubleSinglesCount = value > 50 ? 0 : countSameScoreTypeWays(value, singles);
        count += doubleSinglesCount;

        int doubleDoublesCount = value % 2 == 1 || value > 100 ? 0 : countSameScoreTypeWays(value, doubles);
        count += doubleDoublesCount;

        int doubleTriplesCount = value > 120 ? 0 : countSameScoreTypeWays(value, triples);
        count += doubleTriplesCount;

        int singlesDoublesCount = countDifferentScoreTypeWays(value, singles, doubles);
        int singlesTriplesCount = countDifferentScoreTypeWays(value, singles, triples);
        int doublesTriplesCount = countDifferentScoreTypeWays(value, doubles, triples);

        count += singlesDoublesCount;
        count += singlesTriplesCount;
        count += doublesTriplesCount;

        return count;
    }

    private int countSameScoreTypeWays(int value, List<Integer> scores) {
        int result = 0;
        int maxScore = scores.get(scores.size() - 1);
        for(int i = 0; i < scores.size(); i++) {
            int score1 = scores.get(i);
            if(score1 < value) {
                if(value - score1 > maxScore) {
                   continue;
                }
                for(int j = i; j < scores.size(); j++) {
                    int score2 = scores.get(j);
                    if(score1 + score2 == value) {
                        result++;
                        break;
                    }
                }
            }
            else {
                break;
            }
        }
        return result;
    }

    private int countDifferentScoreTypeWays(int value, List<Integer> scores1, List<Integer> scores2) {
        int result = 0;
        for (int score1 : scores1) {
            if (score1 < value) {
                for (int score2 : scores2) {
                    if (score1 + score2 == value) {
                        result++;
                        break;
                    }
                }
            }
            else {
                break;
            }
        }
        return result;
    }

    private void initGame() {
        for(int i = 1; i <= 20; i++) {
            singles.add(i);
            doubles.add(i * 2);
            triples.add(i * 3);
        }
        singles.add(25);
        doubles.add(50);
        singlesSet.addAll(singles);
        doublesSet.addAll(doubles);
        triplesSet.addAll(triples);
    }
}
