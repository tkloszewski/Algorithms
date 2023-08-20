package com.smart.tkl.lib.utils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PrimitiveRoot {

   public static void main(String[] args) {
       long root = generateRoot(10);
       System.out.println("Primitive Root: " + root);
   }

   public static long generateRoot(long n) {
       List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(n);
       if(primeFactors.size() > 2) {
          return -1;
       }
       else if(primeFactors.size() == 2) {
           if(primeFactors.get(0).getFactor() != 2) {
              return -1;
           }
       }
       else if(primeFactors.size() == 1) {
           if(primeFactors.get(0).getFactor() == 2 && !Set.of(1L, 2L, 4L).contains(n)) {
              return -1;
           }
       }

       long phi = MathUtils.phi(n);
       List<Long> phiPrimeFactors = MathUtils.listPrimeFactors(phi)
               .stream()
               .map(PrimeFactor::getFactor)
               .filter(f -> f < phi)
               .collect(Collectors.toList());

       if(primeFactors.size() == 2) {
           long prime = primeFactors.get(1).getFactor();
           for (long i = 3; i <= n; i += 2) {
               if(i % prime == 0) {
                  continue;
               }
               if(isRoot(i, phiPrimeFactors, phi, n)) {
                   return i;
               }
           }
       }
       else if(primeFactors.size() == 1) {
           long prime = primeFactors.get(0).getFactor();
           for(long i = 2; i <= n; i++) {
               if(i % prime == 0) {
                  continue;
               }
               if(isRoot(i, phiPrimeFactors, phi, n)) {
                  return i;
               }
           }
       }

       return -1;
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
