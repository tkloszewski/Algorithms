package com.smart.tkl.euler.p89;

public class RomanNumberConverter {    
    
    public static RomanNumber toRomanNumber(String literal) {
        if(literal.length() == 1) {
            return new RomanNumber(literal, toRomanLiteral(literal.charAt(0)).getValue());
        }
        
        int value = 0;
        int pos = 0;
        while (pos < literal.length()) {
            RomanLiteral curr = toRomanLiteral(literal.charAt(pos));
            RomanLiteral next = pos + 1 < literal.length() ? toRomanLiteral(literal.charAt(pos + 1)) : null;
            SubtractivePair subtractivePair = SubtractivePair.UNDEFINED;
            
            if(next != null) {
                subtractivePair = SubtractivePair.getByRomanNumber(curr, next);
                if(!subtractivePair.equals(SubtractivePair.UNDEFINED)) {
                    value += subtractivePair.getValue();
                    pos = pos + 2;
                }                
            }
    
            if (subtractivePair.equals(SubtractivePair.UNDEFINED)) {
                value += curr.getValue();
                pos++;
            }
        }        
        
        return new RomanNumber(literal, value);
    }
    
    public static RomanNumber toMinimized(RomanNumber romanNumber) {
        return toMinimized(romanNumber.getValue());
    }
    
    public static RomanNumber toMinimized(int value) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < value / 1000; i++) {
            sb.append("M");
        }
        int hundredPart = value % 1000 - value % 100;
        int tensPart = value % 100 - value % 10;
        int oneDigitPart = value % 10;
        
        sb.append(getMinimalFormLiteral(hundredPart, 100, RomanLiteral.D, "C"));
        sb.append(getMinimalFormLiteral(tensPart, 10, RomanLiteral.L, "X"));
        sb.append(getMinimalFormLiteral(oneDigitPart, 1, RomanLiteral.V, "I"));
        
        return new RomanNumber(sb.toString(), value);
    }
    
    private static String getMinimalFormLiteral(int partValue, int divider, RomanLiteral midLiteral, String fillLiteral) {
        StringBuilder sb = new StringBuilder();
        
        RomanLiteral romanLiteral = RomanLiteral.getByValue(partValue);
        if(!romanLiteral.equals(RomanLiteral.NONE)) {
            sb.append(romanLiteral.getCode());
        }
        else {
            SubtractivePair subtractivePair = SubtractivePair.getByValue(partValue);
            if (!subtractivePair.equals(SubtractivePair.UNDEFINED)) {
                sb.append(subtractivePair.getLiteral());
            }
            else {
                if(partValue > midLiteral.getValue()) {
                    partValue -= midLiteral.getValue();
                    sb.append(midLiteral.getCode());
                }
                for (int i = 0; i < partValue / divider; i++) {
                    sb.append(fillLiteral);
                }
            }
        }
        
        return sb.toString();
    }
    
    private static RomanLiteral toRomanLiteral(char ch) {
        RomanLiteral romanLiteral = RomanLiteral.getByCode(ch);
        if(romanLiteral.equals(RomanLiteral.NONE)) {
            throw new IllegalArgumentException("Unrecognized roman literal: " + ch);
        }
        return romanLiteral;
    }
}
