/*
 * This Code was programmed by Software Jedi's Corne Lukken & Geoffrey van Driessel.
 */
package findprimefactors;

import java.io.*;
import java.math.BigInteger;
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
    static long ePrivateKey = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
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
        
        System.out.println(encryptAndPrintMessage(message.toLowerCase(), e, n)); 
        
        ePrivateKey = calculatePrivateKey(e, p, q);
        System.out.println("privatekey: " + p*q + ", " + ePrivateKey);
        System.out.println("\nAmount of time busy encoding was: ");
        
        System.out.println("first decryption of message: " + (((long)Math.pow(631, ePrivateKey) % n) % n));

        //optional bereken private key               
    }
    
    /**
     * Find P and Q for an given N
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
     * Find largest e between range 1 and 14
     */
    public static long FindE(long p, long q) {
        long e = 0;
        long z = (p-1)*(q-1);
        
        // loop through 1 to 14, because bigger numbers need bigger datastructure than long
        //also it doenst make much sense sich we are using static encoding(number per letter) anyway
        for(long i = 1; i < 13; i++) {
            //if zis dividible by i, then i is always the greatest common divider
            if(z%i!=0) { 
                
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
        }
        return e;
    }
    
    public static String encryptAndPrintMessage(String stringToEncrypt, long publicKeye, long publicKeyN){
        System.out.print("Message after encryption is: ");
        long letterCode = 0;
        long letterEncrypted = 0;
        String message = "";
        for (char letter : stringToEncrypt.toCharArray()) {
            letterCode = (long)letter - 95;
            letterEncrypted = (long) Math.pow(letterCode, publicKeye);
            letterEncrypted = letterEncrypted % publicKeyN;
            message += letterEncrypted + ", ";
        }  
        return message;
    }
    
    public static BigInteger calculatePrivateKey(long e, long p, long q){
        BigInteger e2 = BigInteger.valueOf(e);
        BigInteger p2 = BigInteger.valueOf(p);
        BigInteger q2 = BigInteger.valueOf(q);
        for(BigInteger x = BigInteger.ONE; x.compareTo(BigInteger.valueOf(Long.MAX_VALUE)); x.add(BigInteger.ONE)){
            if(((e2*BigInteger.valueOf(x) % ((p-1)*(q-1))) == 1){
                return x;
            }
        }
        return -1;
    }
}

