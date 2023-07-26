package com.smart.tkl.euler;

import com.smart.tkl.euler.p60.PrimePairSets;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PeriodicFraction;
import com.smart.tkl.lib.utils.SquareRootPeriodicFractionGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class ProblemSet7 {

    private static final String P_079_FILE_PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\p079_keylog.txt";

    public static void main(String[] args) {
        System.out.println("Prime pair sets: " + primePairSets());
        System.out.println("Powerful digit counts: " + powerfulDigitCounts());
       // System.out.println("Passcode derivation: " + derivePasscode());
        System.out.println("Convergents of e: " + eContinuedFractionNumeratorDigitSum());
    }



    public static long primePairSets() {
        PrimePairSets primePairSets = new PrimePairSets(30000, 5);
        return primePairSets.generateMinSum();
    }

    public static int eContinuedFractionNumeratorDigitSum() {
        BigDecimal numerator = eContinuedFractionNumerator(100);
        return MathUtils.sumOfDigits(numerator.toString());
    }

    private static BigDecimal eContinuedFractionNumerator(int pos) {
        if(pos == -1) {
            return BigDecimal.ZERO;
        }
        if(pos == 0) {
            return BigDecimal.ONE;
        }
        BigDecimal prev1 = BigDecimal.ONE, prev2 = BigDecimal.ZERO, numerator = BigDecimal.valueOf(2);
        for(int i = 1; i <= pos; i++) {
            long continuedFraction = eContinuedFraction(i - 1);
            numerator = BigDecimal.valueOf(continuedFraction).multiply(prev1).add(prev2);
            prev2 = prev1;
            prev1 = numerator;
        }
        return numerator;
    }

    private static long eContinuedFraction(int index) {
        if(index == 0) {
            return 2;
        }
        if(index % 3 == 0 || index % 3 == 1) {
            return 1;
        }
        return 2 * (index + 1)/3;
    }

    public static String derivePasscode() {
        int[][] loginAttempts = readLoginAttempts(P_079_FILE_PATH);
        return derivePasscode(loginAttempts);
    }

    private static String derivePasscode(int[][] logins) {
        Set<Integer> uniqueDigits = new TreeSet<>();
        for(int i = 0; i < logins.length; i++) {
            for(int j = 0; j < logins[i].length; j++) {
                uniqueDigits.add(logins[i][j]);
            }
        }

        List<Integer> passDigits = new ArrayList<>(uniqueDigits);

        for(int[] login : logins) {
            for(int i = 0; i < login.length; i++) {
                for(int j = i + 1; j < login.length; j++) {
                    int idx1 = passDigits.indexOf(login[i]);
                    int idx2 = passDigits.indexOf(login[j]);
                    if(idx1 > idx2) {
                        passDigits.set(idx1, login[j]);
                        passDigits.set(idx2, login[i]);
                    }
                }
            }
        }

        StringBuilder passCode = new StringBuilder();
        for(Integer passDigit : passDigits) {
            passCode.append(passDigit);
        }

        return passCode.toString();
    }

    private static int[][] readLoginAttempts(String filePath) {
        List<String> list = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                list.add(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] result = new int[list.size()][];
        for(int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            result[i] = new int[code.length()];
            for(int j = 0; j < code.length(); j++) {
                result[i][j] = code.charAt(j) - '0';
            }
        }
        return result;
    }

    public static int powerfulDigitCounts() {
        int result = 0;
        for(int i = 1; i <= 9; i++) {
            result += countPowerValues(i);
        }
        return result;
    }

    /*Counts a number of times a given i raised to the n-th power
     * forms n digit number*/
    private static int countPowerValues(int i) {
        double result = 1 / (1 - Math.log10(i));
        return (int)result;
    }


}
