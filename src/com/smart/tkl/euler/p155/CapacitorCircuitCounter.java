package com.smart.tkl.euler.p155;

import com.smart.tkl.utils.Fraction;
import java.util.ArrayList;
import java.util.Collections;
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
            for (int i = 0; i <= (list1.size() - 1) / 2; i++) {
                Fraction capacitance1 = list1.get(i);
                for (int j = 0; j <= (list2.size() - 1) / 2; j++) {
                    Fraction capacitance2 = list2.get(j);
                    Fraction invertedCapacitance2 = capacitance2.toInverted();

                    Fraction seriesCapacitance = seriesConnection(capacitance1, capacitance2);
                    Fraction parallelCapacitance = parallelConnection(capacitance1, capacitance2);
                    Fraction invertedSeries = seriesConnection(capacitance1, invertedCapacitance2);
                    Fraction invertedParallel = parallelConnection(capacitance1, invertedCapacitance2);

                    addCapacitanceAndInverted(seriesCapacitance, capacitanceSet);
                    addCapacitanceAndInverted(parallelCapacitance, capacitanceSet);
                    addCapacitanceAndInverted(invertedSeries, capacitanceSet);
                    addCapacitanceAndInverted(invertedParallel, capacitanceSet);
                }
            }
        }

        List<Fraction> list = new ArrayList<>(capacitanceSet);
        Collections.sort(list);

        capacitanceMap.put(capacitors, list);

        return capacitanceSet.size();
    }

    private void addCapacitanceAndInverted(Fraction capacitance, Set<Fraction> capacitanceSet) {
        if(allCapacitanceSet.add(capacitance)) {
            capacitanceSet.add(capacitance);
        }
        Fraction inverted = capacitance.toInverted();
        if(allCapacitanceSet.add(inverted)) {
            capacitanceSet.add(inverted);
        }
    }

    private Fraction seriesConnection(Fraction a, Fraction b) {
        return Fraction.geometricAvg(a, b);
    }

    private Fraction parallelConnection(Fraction a, Fraction b) {
        return Fraction.sum(a, b);
    }
}
