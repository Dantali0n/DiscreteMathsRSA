/*
 * This Code was programmed by Software Jedi's Corne Lukken & Geoffrey van Driessel.
 */
package findprimefactors;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Finds the prime factors of an integer
 * @author Geoffrey van Driessel
 * @author Corne Lukken
 */
public class FindPrimeFactors {
    static long n = 0; // p times q
    static String message = ""; // message to encrypt
    static long p = 0;
    static long q = 0;
    static long e = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        // take argument to assign n and message attributes
        for(String argument : args) {
            if(n == 0) n = Long.parseLong(argument);
            else message = argument;
        }       
        
        for (Map.Entry<Integer, Integer> entry : FindPQ(n).entrySet()) {
            p = entry.getKey();
            q = entry.getValue();
        }
        
        e = FindE(p, q);
        
        System.out.println("p is: " + p);
        System.out.println("q is: " + q);
        System.out.println("e is: " + e);      
        
        encryptAndPrintMessage(message.toLowerCase(), e, n);
        
        System.out.println("\nAmount of time busy encoding was: ");
        

        //optional bereken private key               
    }
    
    /**
     * Find P and Q for an given N
     * @param n number that consists of two prime factors 
     * @return key value pair with P and Q
     */
    public static HashMap<Integer, Integer> FindPQ(long n) {
        HashMap<Integer, Integer>  PQ = new HashMap();
        int p = 0;
        int q = 0;
        int i= 2;
        while(n>1) {
            if(n%i == 0) {
                if(p == 0) p=i;
                else if(q == 0) q=i;
                else n = 0;
                n=n/i;
            } else {
                i++;
            }
        }
        // Put found p and q in hashmap and return
        PQ.put(p, q);
        return PQ;
    }
    
    /**
     * Find largest e between range 1 and z
     */
    public static long FindE(long p, long q) {
        long e = 0;
        long z = (p-1)*(q-1);
        
        // loop through 1 to z and reduce possibilities by modulo z through current i
        for(long i = 1; i < z; i++) {
            
            //if z is dividible by i then we can optimize by skipping i
            if(z%i==0) {
                // skip current element in for loop
                continue; 
            }
                
            // loop through reduced set and check for GCD start checking at 2 as 1 and 0 always work
            boolean hasCommonDivider = false;
            for(int j = 2; j <= i; j++) {
                // If GCD has modulo 0 set hasCommonDivider and stop loop
                if(z%j==0 && i%j==0) {
                    hasCommonDivider = true;
                    break;
                }
            }
                
            // dit not have commonDivider we found a possible e
            // take large e for added complexity
            if(!hasCommonDivider) {
                e = i;
            }
        }
        // Return the largest found e between 1 and z
        return e;
    }
    
    public static void encryptAndPrintMessage(String stringToEncrypt, long publicKeye, long publicKeyN){
        System.out.print("Message after encryption is: ");
        long letterCode = 0;
        long letterEncrypted = 0;
        for (char letter : stringToEncrypt.toCharArray()) {
            letterCode = (int)letter - 97;
            letterEncrypted = (int) Math.pow(letterCode, publicKeye);
            letterEncrypted = letterEncrypted % publicKeyN;
            System.out.print(letterEncrypted + ",");
        }  
    }
}

