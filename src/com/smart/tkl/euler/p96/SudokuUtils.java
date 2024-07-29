package com.smart.tkl.euler.p96;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuUtils {

    public static final Set<Integer> ALL_VALUES = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static Set<Integer> toExcluded(Set<Integer> values) {
        Set<Integer> excluded = new TreeSet<>(ALL_VALUES);
        excluded.removeAll(values);
        return excluded;
    }

    @SafeVarargs
    public static Set<Integer> toExcluded(Set<Integer>... sets) {
        Set<Integer> excluded = new TreeSet<>(ALL_VALUES);
        for(Set<Integer> set : sets) {
            excluded.removeAll(set);
        }
        return excluded;
    }

    public static Set<Integer> subtractSet(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new TreeSet<>(set1);
        result.removeAll(set2);
        return result;
    }

    @SafeVarargs
    public static Set<Integer> sumValuesSet(Set<Integer>... sets) {
        Set<Integer> result = new TreeSet<>();
        for(Set<Integer> set : sets) {
            result.addAll(set);
        }
        return result;
    }

    @SafeVarargs
    public static Set<Integer> commonValuesSet(Set<Integer>... sets) {
        List<Set<Integer>> listOfSets = new ArrayList<>();
        Collections.addAll(listOfSets, sets);
        return commonValuesSet(listOfSets);
    }

    public static Set<Integer> commonValuesSet(Collection<Set<Integer>> sets) {
        Set<Integer> result = new TreeSet<>();
        Iterator<Set<Integer>> iterator = sets.iterator();
        if(iterator.hasNext()) {
            result.addAll(iterator.next());
            while (iterator.hasNext() && !result.isEmpty()) {
                Set<Integer> currentSet = iterator.next();
                result.retainAll(currentSet);
            }
        }
        return result;
    }

}
