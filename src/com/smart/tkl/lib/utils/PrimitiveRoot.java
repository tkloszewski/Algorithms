package com.smart.tkl.lib.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PrimitiveRoot {

   public static void main(String[] args) {
       List<Long> roots = generateRoots(19);
       System.out.println("Primitive roots 19: " + roots);
       roots = generateRoots(37);
       System.out.println("Primitive roots 37: " + roots);
   }

   public static List<Long> generateRoots(long n) {
       List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(n);
       if(primeFactors.size() > 2) {
          return List.of();
       }
       else if(primeFactors.size() == 2) {
           if(primeFactors.get(0).getFactor() != 2 || primeFactors.get(0).getPow() != 1) {
              return List.of();
           }
       }
       else if(primeFactors.size() == 1) {
           if(primeFactors.get(0).getFactor() == 2 && !Set.of(1L, 2L, 4L).contains(n)) {
              return List.of();
           }
       }

       long phi = MathUtils.phi(n);
       List<Long> phiPrimeFactors = MathUtils.listPrimeFactors(phi)
               .stream()
               .map(PrimeFactor::getFactor)
               .filter(f -> f < phi)
               .collect(Collectors.toList());

       List<Long> roots = new ArrayList<>();

       for(long factor = 2; factor < n; factor++) {
           if(isRoot(factor, phiPrimeFactors, phi, n)) {
              roots.add(factor);
           }
       }

       return roots;
   }

   private static boolean isRoot(long x, List<Long> phiPrimeFactors, long phi, long n) {
       for(Long phiFactor : phiPrimeFactors) {
           if(MathUtils.moduloPower(x, phi/ phiFactor, n) == 1) {
               return false;
           }
       }
       return true;
   }

}
