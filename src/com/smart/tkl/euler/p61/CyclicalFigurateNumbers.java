package com.smart.tkl.euler.p61;

import java.util.*;
import java.util.function.Function;

public class CyclicalFigurateNumbers {

    private final List<Integer> chosenFigurates;

    public CyclicalFigurateNumbers(List<Integer> chosenFigurates) {
        this.chosenFigurates = chosenFigurates;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<Integer> sums = new CyclicalFigurateNumbers(List.of(3, 4, 6, 7)).cyclicalFigurateNumbersSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum = " + sums);
        System.out.println("Time = " + (time2 - time1));
    }

    public List<Integer> cyclicalFigurateNumbersSum() {
        List<Integer> triangles = generateFourDigitFigurateNumbers(i -> (i * (i + 1))/2);
        List<Integer> squares = generateFourDigitFigurateNumbers(i -> i * i);
        List<Integer> pentagonals = generateFourDigitFigurateNumbers(i -> (i * (3 * i - 1)) / 2);
        List<Integer> hexagonals = generateFourDigitFigurateNumbers(i -> (i * (2 * i - 1)));
        List<Integer> heptagonals = generateFourDigitFigurateNumbers(i -> (i * (5 * i - 3)) / 2);
        List<Integer> octagonals = generateFourDigitFigurateNumbers(i -> (i * (3 * i - 2)));

        Figurates trianglesFigurates = toFigurates(triangles, FiguratesType.TRIANGLE);
        Figurates squareFigurates = toFigurates(squares, FiguratesType.SQUARE);
        Figurates pentFigurates = toFigurates(pentagonals, FiguratesType.PENTAGONAL);
        Figurates hexFigurates = toFigurates(hexagonals, FiguratesType.HEXAGONAL);
        Figurates heptFigurates = toFigurates(heptagonals, FiguratesType.HEPTAGONAL);
        Figurates octFigurates = toFigurates(octagonals, FiguratesType.OCTAGONAL);

        List<Figurates> figurates = new ArrayList<>(6);

        for(int chosen : chosenFigurates) {
            if(chosen == 3) {
                figurates.add(trianglesFigurates);
            }
            else if(chosen == 4) {
                figurates.add(squareFigurates);
            }
            else if(chosen == 5) {
                figurates.add(pentFigurates);
            }
            else if(chosen == 6) {
                figurates.add(hexFigurates);
            }
            else if(chosen == 7) {
                figurates.add(heptFigurates);
            }
            else if(chosen == 8) {
                figurates.add(octFigurates);
            }
        }

        /*figurates.add(trianglesFigurates);
        figurates.add(squareFigurates);
        figurates.add(pentFigurates);
        figurates.add(hexFigurates);
        figurates.add(heptFigurates);
        figurates.add(octFigurates);*/

        List<List<Integer>> allCyclicalChains = new ArrayList<>();

        List<InitialChain> chains = generateInitialChainAround(0, figurates);
        if(figurates.size() == 3) {
            for(InitialChain initialChain : chains) {
                int first = initialChain.chain.get(0);
                int last = initialChain.chain.get(2);
                if(last % 100 == first / 100) {
                    allCyclicalChains.add(initialChain.chain);
                }
            }
        }
        else {
            for (InitialChain chain : chains) {
                List<Figurates> otherFigurates = new ArrayList<>();
                for (int i = 0; i < figurates.size(); i++) {
                    if ((chain.chosenMask & (1 << i)) == 0) {
                        otherFigurates.add(figurates.get(i));
                    }
                }
                List<List<Integer>> cyclicalChains;
                if(figurates.size() == 4) {
                    cyclicalChains = getCyclicalChainsWithLength4(chain, otherFigurates);
                }
                else if(figurates.size() == 5) {
                    cyclicalChains = getCyclicalChainsWithLength5(chain, otherFigurates);
                }
                else {
                    cyclicalChains = getCyclicalChainsWithLength6(chain, otherFigurates);
                }
                allCyclicalChains.addAll(cyclicalChains);
            }
        }

        System.out.println(allCyclicalChains);

        Set<Integer> uniqueSums = new HashSet<>();
        for(List<Integer> chain : allCyclicalChains) {
            Set<Integer> chainSet = new HashSet<>(chain);
            if (chain.size() == chainSet.size()) {
                int sum = chain.stream().reduce(0, Integer::sum);
                uniqueSums.add(sum);
            }
        }

        List<Integer> sums = new ArrayList<>(uniqueSums);
        Collections.sort(sums);

        return sums;
    }

    private static List<List<Integer>> getCyclicalChainsWithLength6(InitialChain chain, List<Figurates> otherFigurates) {
        List<List<Integer>> result = new ArrayList<>(6);

        Integer firstNum = chain.chain.get(0);
        Integer thirdNum = chain.chain.get(2);
        Integer chainPrefix = firstNum / 100;
        Integer chainSuffix = thirdNum % 100;


        for(int i = 0; i < otherFigurates.size(); i++) {
            Figurates fig1 = otherFigurates.get(i);
            if (!fig1.prefixMap.containsKey(chainSuffix)) {
                continue;
            }
            for(int j = 0; j < otherFigurates.size(); j++) {
                if(j == i) {
                    continue;
                }
                Figurates fig2 = otherFigurates.get(j);
                for(int k = 0; k < otherFigurates.size(); k++) {
                    if(k == i || k == j) {
                        continue;
                    }
                    Figurates fig3 = otherFigurates.get(k);
                    if (!fig3.suffixMap.containsKey(chainPrefix)) {
                        continue;
                    }
                    for(Integer fourth : fig1.prefixMap.get(chainSuffix)) {
                        int fourthSuffix = fourth % 100;
                        for(Integer sixth : fig3.suffixMap.get(chainPrefix)) {
                            int sixthPrefix = sixth / 100;
                            Integer fifth = fourthSuffix * 100 + sixthPrefix;
                            if(fig2.numbers.contains(fifth)) {
                                List<Integer> cyclicalChain = new ArrayList<>(chain.chain);
                                cyclicalChain.addAll(Arrays.asList(fourth, fifth, sixth));
                                result.add(cyclicalChain);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static List<List<Integer>> getCyclicalChainsWithLength5(InitialChain chain, List<Figurates> otherFigurates) {
        List<List<Integer>> result = new ArrayList<>(5);

        Integer firstNum = chain.chain.get(0);
        Integer thirdNum = chain.chain.get(2);
        Integer chainPrefix = firstNum / 100;
        Integer chainSuffix = thirdNum % 100;


        for(int i = 0; i < otherFigurates.size(); i++) {
            Figurates fig1 = otherFigurates.get(i);
            if (!fig1.prefixMap.containsKey(chainSuffix)) {
                continue;
            }
            for(int j = 0; j < otherFigurates.size(); j++) {
                if(j == i) {
                    continue;
                }
                Figurates fig2 = otherFigurates.get(j);
                if (!fig2.suffixMap.containsKey(chainPrefix)) {
                    continue;
                }

                for(Integer fourth : fig1.prefixMap.get(chainSuffix)) {
                    int fourthSuffix = fourth % 100;
                    for(Integer fifth : fig2.suffixMap.get(chainPrefix)) {
                        int fifthPrefix = fifth / 100;
                        if(fourthSuffix == fifthPrefix) {
                            List<Integer> cyclicalChain = new ArrayList<>(chain.chain);
                            cyclicalChain.addAll(Arrays.asList(fourth, fifth));
                            result.add(cyclicalChain);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static List<List<Integer>> getCyclicalChainsWithLength4(InitialChain chain, List<Figurates> otherFigurates) {
        List<List<Integer>> result = new ArrayList<>(5);

        Integer firstNum = chain.chain.get(0);
        Integer thirdNum = chain.chain.get(2);
        Integer chainPrefix = firstNum / 100;
        Integer chainSuffix = thirdNum % 100;

        for (Figurates fig1 : otherFigurates) {
            if (!fig1.prefixMap.containsKey(chainSuffix)) {
                continue;
            }

            for (Integer fourth : fig1.prefixMap.get(chainSuffix)) {
                int fourthSuffix = fourth % 100;
                if (fourthSuffix == chainPrefix) {
                    List<Integer> cyclicalChain = new ArrayList<>(chain.chain);
                    cyclicalChain.add(fourth);
                    result.add(cyclicalChain);
                }
            }
        }
        return result;
    }

    private static List<InitialChain> generateInitialChainAround(int aroundIdx, List<Figurates> list) {
        List<InitialChain> result = new ArrayList<>();
        Figurates chosenFigurates = list.get(aroundIdx);

        for(int i = 0; i < list.size(); i++) {
            if(i == aroundIdx) {
                continue;
            }
            Figurates previousFigurates = list.get(i);
            for(int j = 0; j < list.size(); j++) {
                if(j == aroundIdx || j == i) {
                    continue;
                }
                Figurates nextFigurates = list.get(j);
                for(Integer middle : chosenFigurates.numbers) {
                    int suffix = middle % 100;
                    int prefix = middle / 100;
                    if(nextFigurates.prefixMap.containsKey(suffix) && previousFigurates.suffixMap.containsKey(prefix)) {
                        for(Integer prev : previousFigurates.suffixMap.get(prefix)) {
                            for(Integer next : nextFigurates.prefixMap.get(suffix)) {
                                Integer mask = (1 << aroundIdx) | (1 << i) | (1 << j);
                                result.add(new InitialChain(Arrays.asList(prev, middle, next), mask));
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private static Figurates toFigurates(List<Integer> numbers, FiguratesType type) {
        Figurates result = new Figurates(new LinkedHashSet<>(numbers), type);
        for(int figurative : numbers) {
            int mod100 = figurative % 100;
            int prefix = figurative / 100;
            if (mod100 >= 10) {
                if(result.suffixMap.containsKey(mod100)) {
                    result.suffixMap.get(mod100).add(figurative);
                }
                else {
                    Set<Integer> modNumbers = new LinkedHashSet<>();
                    modNumbers.add(figurative);
                    result.suffixMap.put(mod100, modNumbers);
                }
                if(result.prefixMap.containsKey(prefix)) {
                    result.prefixMap.get(prefix).add(figurative);
                }
                else {
                    Set<Integer> prefNumbers = new LinkedHashSet<>();
                    prefNumbers.add(figurative);
                    result.prefixMap.put(prefix, prefNumbers);
                }
            }

        }
        return result;
    }

    private static List<Integer> generateFourDigitFigurateNumbers(Function<Integer, Integer> formula) {
        List<Integer> numbers = new ArrayList<>(1000);
        int i = 1, n = 1;
        while(n < 10000) {
            if(n >= 1000 && n % 100 >= 10) {
                numbers.add(n);
            }
            n = formula.apply(i);
            i++;
        }
        return numbers;
    }

    private static class InitialChain {
        List<Integer> chain;
        Integer chosenMask;

        public InitialChain(List<Integer> chain, Integer chosenMask) {
            this.chain = chain;
            this.chosenMask = chosenMask;
        }

        @Override
        public String toString() {
            return chain.toString();
        }

        public int getSum() {
            return chain.stream().reduce(0, Integer::sum);
        }
    }


    enum FiguratesType {TRIANGLE, SQUARE, PENTAGONAL, HEXAGONAL, HEPTAGONAL, OCTAGONAL}

    private static class Figurates {
        Map<Integer, Set<Integer>> suffixMap = new LinkedHashMap<>();
        Map<Integer, Set<Integer>> prefixMap = new LinkedHashMap<>();
        Set<Integer> numbers;
        private FiguratesType type;

        public Figurates(Set<Integer> numbers, FiguratesType type) {
            this.numbers = numbers;
            this.type = type;
        }

        @Override
        public String toString() {
            return type + " numbers size: " + numbers.size();
        }
    }


}
