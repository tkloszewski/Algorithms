package com.smart.tkl.graph.standard.connect;

public class ConnectivityResult {

    private final boolean weaklyConnected;
    private final boolean stronglyConnected;

    public ConnectivityResult(boolean weaklyConnected, boolean stronglyConnected) {
        this.weaklyConnected = weaklyConnected;
        this.stronglyConnected = stronglyConnected;
    }

    public boolean isWeaklyConnected() {
        return weaklyConnected;
    }

    public boolean isStronglyConnected() {
        return stronglyConnected;
    }

    @Override
    public String toString() {
        return "{" +
                "weaklyConnected=" + weaklyConnected +
                ", stronglyConnected=" + stronglyConnected +
                '}';
    }
}
