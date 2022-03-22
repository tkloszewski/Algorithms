package com.smart.tkl.euler.p88;

import java.util.LinkedHashSet;
import java.util.Set;

public class ProductSumNumber {
    
    private final int maxSetSize;
    private final int productMax;
    private final int[] minProductSumTab;    
       
    
    public static void main(String[] args) {
        ProductSumNumber productSumNumber = new ProductSumNumber(12000);
        long time1 = System.currentTimeMillis();        
        int sum = productSumNumber.solve().distinctSum(); 
        long time2 = System.currentTimeMillis();
        System.out.println("uniqueMinProductSumNumbers sum: " + sum + ". In ms: " + (time2 - time1));
    }
    
    public ProductSumNumber(int maxSetSize) {
        this.maxSetSize = maxSetSize;
        this.productMax = 2 * maxSetSize;
        minProductSumTab = new int[maxSetSize + 1];
        for(int i = 2; i < minProductSumTab.length; i++) {
            minProductSumTab[i] = 2 * i;
        }
    }    
    
    public ProductSumNumber solve() {
        int maxFactors = 0, product = 2;
        while (product <= this.productMax) {
            maxFactors++;    
            product *= 2;
        }
        for(int numOfFactors = 2; numOfFactors <= maxFactors; numOfFactors++) {
            generateProducts(numOfFactors);
        }
        
        return this;
    }
    
    public int distinctSum() {
        Set<Integer> uniqueMinProductSumNumbers = new LinkedHashSet<>();
        for(int minProductSum : minProductSumTab) {
            uniqueMinProductSumNumbers.add(minProductSum);
        }
        return uniqueMinProductSumNumbers.stream().reduce(0, Integer::sum);
    }
    
    private void generateProducts(int numOfFactors) {
        int[] factors = new int[numOfFactors];
        doGenerateProducts(0, 2, 0 , 1, factors);
    }
    
    private void doGenerateProducts(int index, int value, int currSum, int currProd, int[] factors) {        
        if(index == factors.length - 1) {
            int sum = currSum + value;
            int product = currProd * value;
            int k = factors.length + Math.max(0, product - sum); 
            
            while (product <= this.productMax ) {
                factors[index] = value;    
                if(k <= maxSetSize && minProductSumTab[k] > product) {
                   minProductSumTab[k] = product; 
                }
                value++;
                product = currProd * value;
                sum = currSum + value;
                k = factors.length + Math.max(0, product - sum);           
            }
        }
        else {
           int pow = factors.length - index;            
           while (currProd * value <= productMax && currProd * Math.pow(value, pow) <= productMax) {
               factors[index] = value;
               
               doGenerateProducts(index + 1, value, currSum + value, currProd * value, factors);
               
               value++;
           }
        }
    }   
    
}
