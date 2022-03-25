package com.smart.tkl.euler.p89;

import static com.smart.tkl.euler.p89.RomanLiteral.*;

public enum SubtractivePair {
    IV(I, V),
    IX(I, X),
    XL(X, L),
    XC(X, C),
    CD(C, D),
    CM(C, M),
    UNDEFINED(NONE, NONE);
    
    private final RomanLiteral first;
    private final RomanLiteral second;
    private final int value;
    
    SubtractivePair(RomanLiteral first, RomanLiteral second) {
        this.first = first;
        this.second = second;
        this.value = second.getValue() - first.getValue();
    }
    
    public static SubtractivePair getByRomanNumber(RomanLiteral first, RomanLiteral second) {
        for(SubtractivePair subtractivePair : values()) {
            if(subtractivePair.first.equals(first) && subtractivePair.second.equals(second)) {
                return subtractivePair;
            }
        }
        return SubtractivePair.UNDEFINED;
    }
    
    public static SubtractivePair getByValue(int value) {
        for(SubtractivePair subtractivePair : values()) {
            if(subtractivePair.getValue() == value) {
                return subtractivePair;
            }
        }
        return SubtractivePair.UNDEFINED; 
    }
    
    public int getValue() {
        return value;
    }
    
    public String getLiteral() {
        return new String(new char[]{first.getCode(), second.getCode()});
    }
}
