package com.smart.tkl.euler.p96.permutation;

import com.smart.tkl.PermutationIterator;
import com.smart.tkl.euler.p96.element.CellKey;
import com.smart.tkl.euler.p96.element.UniqueValuesSudokuElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ElementPermutationGenerator implements Comparable<ElementPermutationGenerator> {

    private UniqueValuesSudokuElement element;
    private final List<CellKey> availableCellKeys;
    private final PermutationIterator permutationIterator;

    public ElementPermutationGenerator(UniqueValuesSudokuElement uniqueValuesElement) {
        this.element = uniqueValuesElement;
        this.availableCellKeys = new ArrayList<>(uniqueValuesElement.getAvailableCellKeys());
        this.permutationIterator = createPermutationIterator(uniqueValuesElement);
    }

    public CellValuePermutation getNextPermutation() {
        int[] permutation = permutationIterator.next();
        List<CellValue> cellValues = new ArrayList<>();
        for(int i = 0; i < permutation.length; i++) {
            CellKey cellKey = availableCellKeys.get(i);
            cellValues.add(new CellValue(cellKey, permutation[i]));
        }
        return new CellValuePermutation(cellValues);
    }

    public Integer getPermutationLength() {
        return permutationIterator.getLength();
    }

    public boolean hasNextPermutation() {
        return permutationIterator.hasNext();
    }

    @Override
    public int compareTo(ElementPermutationGenerator other) {
        return this.getPermutationLength().compareTo(other.getPermutationLength());
    }

    private PermutationIterator createPermutationIterator(UniqueValuesSudokuElement uniqueValuesElement) {
        Set<Integer> permutationValues = uniqueValuesElement.getAvailableValues();
        int[] permutation = new int[permutationValues.size()];
        int i = 0;
        for(Integer value : permutationValues) {
            permutation[i++] = value;
        }
        return new PermutationIterator(permutation);
    }
}
