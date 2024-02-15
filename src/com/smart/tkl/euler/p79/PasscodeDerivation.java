package com.smart.tkl.euler.p79;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PasscodeDerivation {

    public static void main(String[] args) {
        List<String> attempts = List.of("ATZ","PEI", "TIZ", "ETZ");
        String pass = derivePasscode(attempts);
        System.out.println("Pass: " + pass);
    }

    public static String derivePasscode(List<String> attempts) {
        StringBuilder result = new StringBuilder();

        Map<Character, Set<Character>> predecessorMap = new TreeMap<>();
        for(String attempt : attempts) {
            for(int i = 1; i < attempt.length(); i++) {
                char ch = attempt.charAt(i);
                if(!predecessorMap.containsKey(ch)) {
                    predecessorMap.put(ch, new TreeSet<>());
                }
                Set<Character> predecessors = predecessorMap.get(ch);
                for(int j = 0; j < i; j++) {
                    char ch2 = attempt.charAt(j);
                    predecessors.add(ch2);
                }
            }
            if(!predecessorMap.containsKey(attempt.charAt(0))) {
               predecessorMap.put(attempt.charAt(0), new TreeSet<>());
            }
        }

        for(Character ch : predecessorMap.keySet()) {
            Set<Character> checked = new HashSet<>();
            checked.add(ch);
            boolean inconsistent = checkForInconsistency(predecessorMap, ch, predecessorMap.get(ch), checked);
            if(inconsistent) {
               return "SMTH WRONG";
            }
        }



        while (!predecessorMap.isEmpty()) {
            Character next = null;
            for(Character ch : predecessorMap.keySet()) {
                if(predecessorMap.get(ch).isEmpty()) {
                   next = ch;
                   break;
                }
            }
            if(next == null) {
              break;
            }
            result.append(next);
            predecessorMap.remove(next);
            for(Character ch : predecessorMap.keySet()) {
                predecessorMap.get(ch).remove(next);
            }
        }

        return result.toString();
    }

    private static boolean checkForInconsistency(Map<Character, Set<Character>> predecessorMap, Character ch, Set<Character> predecessors, Set<Character> checked) {
        for(Character predecessor : predecessors) {
            if(!checked.contains(predecessor)) {
                Set<Character> prePredecessors = predecessorMap.get(predecessor);
                if(prePredecessors.contains(ch)) {
                   return true;
                }
                checked.add(predecessor);
                for(Character prePredecessor : prePredecessors) {
                    if(checkForInconsistency(predecessorMap, ch, predecessorMap.get(prePredecessor), checked)) {
                       return true;
                    }
                }
            }
        }
        return false;
    }
}
