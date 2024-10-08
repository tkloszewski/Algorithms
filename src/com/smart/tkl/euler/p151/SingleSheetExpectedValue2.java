package com.smart.tkl.euler.p151;

import com.smart.tkl.lib.utils.ModuloUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SingleSheetExpectedValue2 {

    private static final long MOD = (long) Math.pow(10, 9) + 7;

    private final int N;
    private final int[] state;
    private final long[] powers2;
    private final EnvelopeState[] cache = new EnvelopeState[10000000];
    private final long[] cachedModInv = new long[65];

    public SingleSheetExpectedValue2(int N) {
        this.N = N;
        this.state = new int[N];
        this.powers2 = new long[N];
        initState();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SingleSheetExpectedValue2 singleSheetExpectedValue = new SingleSheetExpectedValue2(8);
        List<EnvelopeState> resolvedStates = singleSheetExpectedValue.resolveAllValues();
        long time2 = System.currentTimeMillis();
        for(EnvelopeState envelopeState : resolvedStates) {
            System.out.println(envelopeState.asString());
        }
        System.out.println("Time in ms: " + (time2 - time1));
    }

    /*
    * 1 0 0 0 0 0 0 0
    * 0 1 1 1 1 1 1 1
    * 0 0 2 2 2 2 2 2
    * 0 0 0 4 4 4 4 4
    * 0 0 0 0 8 8 8 8
    * 0 0 0 0 0 16 16 16
    * 0 0 0 0 0 0  32 32
    * 0 0 0 0 0 0  0  64
    * */
    private void initState() {
        this.state[0] = 1;
        long pow = 1;
        for(int i = N - 1; i >= 0; i--) {
            int pos = i + 1;
            powers2[i] = pow;
            if(pos >= 2) {
               long pow2 = (long)Math.pow(2, pos - 2);
               pow = pow * (pow2 + 1);
            }
            else if(pos == 1) {
                pow = pow * 2;
            }
        }
        cachedModInv[1] = 1;
        for(int value = 2; value <= 64; value++) {
            cachedModInv[value] = ModuloUtils.modInv(value, MOD);
        }
    }

    List<EnvelopeState> resolveAllValues() {
        List<EnvelopeState> result = new ArrayList<>();
        resolveExpectedValue(state, powers2[0], 1, result);
        return result;
    }

    private EnvelopeState resolveExpectedValue(int[] state, long stateValue, int allSheetsCount, List<EnvelopeState> resolvedConfigs) {
        if(cache[(int) stateValue] != null) {
           return cache[(int) stateValue];
        }
        else {
            EnvelopeState result;
            long expectedValue;

            if(stateValue == 0) {
                expectedValue = 0;
                result = new EnvelopeState(stateValue, expectedValue, asString(state, expectedValue));
            }
            else if(allSheetsCount == 1) {
                expectedValue = 1;
                int newAllSheetsCount = 0;
                long newStateValue = stateValue;
                for(int i = 0; i < state.length; i++) {
                    if(state[i] == 1) {
                        newAllSheetsCount += (N - i - 1);
                        state[i] = 0;
                        newStateValue = newStateValue - powers2[i];
                        for (int j = i + 1; j < state.length; j++) {
                            state[j]++;
                            newStateValue = newStateValue + powers2[j];
                        }

                        EnvelopeState resolvedState = resolveExpectedValue(state, newStateValue, newAllSheetsCount, resolvedConfigs);
                        expectedValue = (expectedValue + resolvedState.expectedValue) % MOD;

                        state[i]++;
                        for(int j = i + 1; j < state.length; j++) {
                            state[j]--;
                        }

                        break;
                    }
                }
                result = new EnvelopeState(stateValue, expectedValue, asString(state, expectedValue));
            }
            else {
                expectedValue = 0;
                for(int i = 0; i < state.length; i++) {
                    int count = state[i];
                    if(count > 0) {
                        state[i]--;
                        int newAllSheetsCount = allSheetsCount - 1;
                        long newStateValue = stateValue;
                        newAllSheetsCount += (N - i - 1);
                        newStateValue = newStateValue - powers2[i];
                        for(int j = i + 1; j < state.length; j++) {
                            state[j]++;
                            newStateValue = newStateValue + powers2[j];
                        }
                        long modInv = cachedModInv[allSheetsCount];
                        long probability = (count * modInv) % MOD;
                        EnvelopeState resolvedState = resolveExpectedValue(state, newStateValue, newAllSheetsCount, resolvedConfigs);
                        expectedValue = (expectedValue + (probability * resolvedState.expectedValue) % MOD) % MOD;
                        //revert
                        state[i]++;
                        for(int j = i + 1; j < state.length; j++) {
                            state[j]--;
                        }
                    }
                }
                result = new EnvelopeState(stateValue, expectedValue, asString(state, expectedValue));
            }
            cache[(int)stateValue] = result;
            resolvedConfigs.add(result);
            return result;
        }
    }

    private static String asString(int[] state, long expectedValue) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for (int j : state) {
            stringJoiner.add(String.valueOf(j));
        }
        return stringJoiner + ": "+ expectedValue;
    }

    private static class EnvelopeState {
        long stateValue2;
        long expectedValue;
        String asString;

        public EnvelopeState(long stateValue2, long expectedValue, String asString) {
            this.stateValue2 = stateValue2;
            this.expectedValue = expectedValue;
            this.asString = asString;
        }

        @Override
        public String toString() {
            return "{" +
                    ", sv2=" + stateValue2 +
                    ", EV=" + expectedValue +
                    ", state= " + asString +
                    '}';
        }

        public String asString() {
            return asString;
        }
    }
}
