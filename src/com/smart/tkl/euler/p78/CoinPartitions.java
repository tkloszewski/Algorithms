package com.smart.tkl.euler.p78;

import java.util.LinkedHashMap;
import java.util.Map;

public class CoinPartitions {

    private final int modValue;
    private final Map<Integer, Integer> partitionMap;

    public static void main(String[] args) {
        CoinPartitions coinPartitions = new CoinPartitions(1000000);
        long time1 = System.currentTimeMillis();
        int foundValue = coinPartitions.findValue();
        long time2 = System.currentTimeMillis();
        System.out.printf("Found value: %d. The solution took in ms: %d", foundValue, (time2 - time1));
    }

    public CoinPartitions(int modValue) {
        this.modValue = modValue;
        partitionMap = new LinkedHashMap<>(modValue);
    }

    public int findValue() {
        int value = 0, modPartitionCount = 1;
        while (modPartitionCount != 0) {
           modPartitionCount = modPartition(++value);
        }
        return value;
    }

    private int modPartition(int n) {
        if(partitionMap.containsKey(n)) {
           return partitionMap.get(n);
        }

        if(n < 0) {
           return 0;
        }
        if(n == 0) {
           return 1;
        }

        int result = 0;

        int sign = 1, index = n;
        for(int k = 1; index > 0; k++) {
            int n1 = n - toPentagonal(k);
            int n2 = n - toPentagonal(-k);

            int value1 = n1 >= 0 ? modPartition(n1) : 0;
            int value2 = n2 >= 0 ? modPartition(n2) : 0;

            int sum = (value1 + value2) % modValue;
            result = (result + sign * (sum)) % modValue;

            sign *= -1;
            index = n2;
        }

        partitionMap.put(n, result);

        return result;
    }

    private int toPentagonal(int k) {
        return (k * (3 * k - 1)) / 2;
    }

}
