package com.smart.tkl.hash;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class RabinKarpPatternMatching {

    private final String pattern;
    private final String text;

    private Integer firstPosition;

    public RabinKarpPatternMatching(String pattern, String text) {
        this.pattern = pattern;
        this.text = text;
    }

    public int findFirstPosition() {
        if(this.firstPosition != null) {
            return this.firstPosition;
        }
        if(this.text.length() < this.pattern.length()) {
            this.firstPosition = -1;
            return this.firstPosition;
        }

        RollinHash patternRollingHash = new RollinHash(this.pattern.length());
        for(int i = 0; i < this.pattern.length(); i++) {
            patternRollingHash.append(this.pattern.charAt(i));
        }
        long patternHash = patternRollingHash.getHash();

        RollinHash textRollingHash = new RollinHash(this.pattern.length());
        for(int i = 0; i < this.pattern.length(); i++) {
            textRollingHash.append(this.text.charAt(i));
        }

        if(patternHash == textRollingHash.getHash() && patternEquals(0)) {
            this.firstPosition = 0;
            return this.firstPosition;
        }


        for(int i = pattern.length(); i < text.length(); i++) {
            textRollingHash.skip(text.charAt(i - pattern.length()));
            textRollingHash.append(text.charAt(i));

            if(patternHash == textRollingHash.getHash()) {
                int pos = i - pattern.length() + 1;
                if(patternEquals(pos)) {
                    this.firstPosition = pos;
                    return this.firstPosition;
                }
            }
        }

        this.firstPosition = -1;
        return this.firstPosition;
    }

    private boolean patternEquals(int pos) {
        for(int i = 0; i < pattern.length(); i++) {
            if(pattern.charAt(i) != text.charAt(pos + i)) {
                return false;
            }
        }
        return true;
    }
}
