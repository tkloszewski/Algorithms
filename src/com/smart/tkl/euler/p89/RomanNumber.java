package com.smart.tkl.euler.p89;

public class RomanNumber {
    
    private String literal;
    private int value;
    
    public RomanNumber(String literal, int value) {
        this.literal = literal;
        this.value = value;
    }
    
    public String getLiteral() {
        return literal;
    }
    
    public void setLiteral(String literal) {
        this.literal = literal;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "RomanNumber{" +
                "literal='" + literal + '\'' +
                ", value=" + value +
                '}';
    }
}
