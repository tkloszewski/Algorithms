package com.smart.tkl.euler.p151;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SingleSheetExpectedValue {

    private final int initialSize;
    private final Map<List<Integer>, CachedValue> memo = new HashMap<>();

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SingleSheetExpectedValue singleSheetExpectedValue = new SingleSheetExpectedValue(16);
        BigDecimal expectedValue = singleSheetExpectedValue.calcExpectedValue();
        long time2 = System.currentTimeMillis();

        System.out.println("Expected value: " + expectedValue);
        System.out.println("Solution found in: " + (time2 - time1));
    }

    public SingleSheetExpectedValue(int initialSize) {
        this.initialSize = initialSize;
    }

    public BigDecimal calcExpectedValue() {
       List<Integer> sheets = new ArrayList<>();
       sheets.add(initialSize);
       BigDecimal exactValue = calcExpectedValue(sheets, BigDecimal.ONE, 1);
       return exactValue.round(new MathContext(6));
    }

    private BigDecimal calcExpectedValue(List<Integer> sheets, BigDecimal accumulatedProbability, int level) {
        CachedValue cachedValue = memo.get(sheets);
        if(cachedValue != null) {
           return cachedValue.calculatedValue
                   .divide(cachedValue.accumulatedValue, MathContext.DECIMAL128)
                   .multiply(accumulatedProbability);
        }

        BigDecimal result = BigDecimal.ZERO;
        if(sheets.size() == 1) {
           int sheet = sheets.get(0);
           if(sheet != 1 && sheet != initialSize) {
              result = accumulatedProbability;
           }
           if(sheet != 1) {
              sheets = cutSheet(sheet);
              result = result.add(calcExpectedValue(sheets, accumulatedProbability, level + 1));
           }
        }
        else {
            for(int i = 0; i < sheets.size(); i++) {
                Integer sheet = sheets.get(i);
                int j = i, sameValueSheetsSize = 0;
                while (j < sheets.size() && sheets.get(j).equals(sheet)) {
                  sameValueSheetsSize++;
                  j++;
                }
                BigDecimal currentProbability = calcProbability(sameValueSheetsSize, sheets.size());
                List<Integer> newSheets = new ArrayList<>(sheets);
                newSheets.remove(i);
                if(sheet != 1) {
                    newSheets.addAll(cutSheet(sheet));
                    newSheets.sort(Comparator.reverseOrder());
                }

                result = result.add(calcExpectedValue(newSheets, accumulatedProbability.multiply(currentProbability), level + 1));

                i = j - 1;
            }
        }
        memo.put(sheets, new CachedValue(accumulatedProbability, result));
        return result;
    }

    private List<Integer> cutSheet(int sheet) {
        List<Integer> sheets = new ArrayList<>();
        sheet = sheet / 2;
        while (sheet > 0) {
            sheets.add(sheet);
            sheet = sheet / 2;
        }
        return sheets;
    }

    private static BigDecimal calcProbability(int sameValueSheetsSize, int sheetsSize) {
        return BigDecimal.valueOf(sameValueSheetsSize).divide(BigDecimal.valueOf(sheetsSize), MathContext.DECIMAL128);
    }

    private static class CachedValue {
        BigDecimal accumulatedValue;
        BigDecimal calculatedValue;

        public CachedValue(BigDecimal accumulatedValue, BigDecimal calculatedValue) {
            this.accumulatedValue = accumulatedValue;
            this.calculatedValue = calculatedValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CachedValue that = (CachedValue) o;
            return Objects.equals(accumulatedValue, that.accumulatedValue) && Objects.equals(calculatedValue, that.calculatedValue);
        }

        @Override
        public int hashCode() {
            return Objects.hash(accumulatedValue, calculatedValue);
        }
    }

}
