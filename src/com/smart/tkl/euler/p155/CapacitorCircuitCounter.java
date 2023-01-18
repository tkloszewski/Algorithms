package com.smart.tkl.euler.p155;

import com.smart.tkl.utils.Fraction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CapacitorCircuitCounter {

    private final int maxCapacitors;
    private final Map<Integer, List<Fraction>> capacitanceMap = new HashMap<>();
    private final Set<Fraction> allCapacitanceSet = new HashSet<>();

    public CapacitorCircuitCounter(int capacitors) {
        this.maxCapacitors = capacitors;
        capacitanceMap.put(1, List.of(new Fraction(1, 1)));
        allCapacitanceSet.add(new Fraction(1, 1));
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        CapacitorCircuitCounter capacitorCircuitCounter = new CapacitorCircuitCounter(18);
        int count = capacitorCircuitCounter.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int count() {
        int result = 1;
        for(int capacitors = 2; capacitors <= maxCapacitors; capacitors++) {
            result += countForCapacitors(capacitors);
        }
        return result;
    }

    private int countForCapacitors(int capacitors) {
        Set<Fraction> capacitanceSet = new LinkedHashSet<>();
        for(int setSize1 = 1; setSize1 <= capacitors/2; setSize1++) {
            int setSize2 = capacitors - setSize1;
            List<Fraction> list1 = capacitanceMap.get(setSize1);
            List<Fraction> list2 = capacitanceMap.get(setSize2);
            for (Fraction capacitance1 : list1) {
                for (Fraction capacitance2 : list2) {
                    Fraction seriesCapacitance = seriesConnection(capacitance1, capacitance2);
                    if(allCapacitanceSet.add(seriesCapacitance)) {
                       capacitanceSet.add(seriesCapacitance);
                    }
                    Fraction parallelCapacitance = parallelConnection(capacitance1, capacitance2);
                    if(allCapacitanceSet.add(parallelCapacitance)) {
                       capacitanceSet.add(parallelCapacitance);
                    }
                }
            }
        }

        capacitanceMap.put(capacitors, new ArrayList<>(capacitanceSet));

        return capacitanceSet.size();
    }

    private static Fraction seriesConnection(Fraction capacity1, Fraction capacity2) {
        return Fraction.geometricAvg(capacity1, capacity2);
    }

    private static Fraction parallelConnection(Fraction capacity1, Fraction capacity2) {
        return Fraction.sum(capacity1, capacity2);
    }
}
