package com.smart.tkl.euler.poker.exception;

import com.smart.tkl.euler.poker.Rank;

public class UnsupportedRankException extends RuntimeException {

    private Rank rank;

    public UnsupportedRankException(String message) {
        super(message);
    }

    public UnsupportedRankException(String message, Rank rank) {
        super(message);
        this.rank = rank;
    }
}
