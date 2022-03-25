package com.smart.tkl.euler.p89;

public enum RomanLiteral {
    I('I', 1),
    V('V', 5),
    X('X', 10),
    L('L', 50),
    C('C', 100),
    D('D', 500),
    M('M', 1000),
    NONE('0', 0);    
    
    private final char code;
    private final int value;
    
    RomanLiteral(char code, int value) {
        this.code = code;
        this.value = value;
    }
    
    public static RomanLiteral getByCode(char code) {
        for(RomanLiteral romanNumber : values()) {
            if(romanNumber.code == code) {
               return romanNumber; 
            }
        }
        return RomanLiteral.NONE;
    }
    
    public static RomanLiteral getByValue(int value) {
        for(RomanLiteral romanNumber : values()) {
            if(romanNumber.value == value) {
                return romanNumber;
            }
        }
        return RomanLiteral.NONE;
    }
    
    public char getCode() {
        return code;
    }
    
    public int getValue() {
        return value;
    }
}
