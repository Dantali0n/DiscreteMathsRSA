/*
 * This Code was programmed by Software Jedi's Corne Lukken & Geoffrey van Driessel.
 */
package findprimefactors;

import java.io.*;
import java.math.BigInteger;

/** sudo apt install openjfx openjfx-source **/
import javafx.util.Pair;

/**
 * Finds the prime factors of an integer
 * @author Geoffrey van Driessel
 * @author Corne Lukken
 */
public class FindPrimeFactors {
    static String message = ""; // message to encrypt
    static long n = 0; // p times q
    static long p = 0;
    static long q = 0;
    static long e = 0;
    static long ePrivateKey = 0;

    static boolean modeDecrypt = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       long CurrentTime = System.nanoTime();
        BigInteger bi1, bi2;
        for(String argument : args) {
            if(n == 0) { // first argument must equal n
                n = Long.parseLong(argument);
                continue;
            } 
            try { // second argument must equal e or be the sentence
                e = Long.parseLong(argument);
            } 
            // If argument cannot be converted to long then it is a message
            catch(NumberFormatException nfExcept) {
                message = argument;
            } 
            
        }

	// enter decrypting mode when e is not zero.
	if(e != 0) modeDecrypt = true;

        Pair<Integer, Integer> pq = FindPQ(n);
        p = pq.getKey();
        q = pq.getValue();

	// If we encrypt find e otherwise it is given.
	if(!modeDecrypt) e = FindE(p, q);

        System.out.println("n is: " + n);
        System.out.println("p is: " + p);
        System.out.println("q is: " + q);
        System.out.println("e is: " + e);

	if(!modeDecrypt) {
            System.out.println(encryptAndPrintMessage(message.toLowerCase(), e, n));
            ePrivateKey = calculatePrivateKey(e, p, q);
            System.out.println("private key: " + p*q + ", " + ePrivateKey);
            System.out.println("Total time to encode: " + ((System.nanoTime() - CurrentTime) / 1000000000D));
	}
	else {
            Decrypt.run(n, e, message);
            System.out.println("Total time to decrypt: " + ((System.nanoTime() - CurrentTime) / 1000000000D));
	}             
    }

    /**
     * Find P and Q for an given N
     */
    public static Pair<Integer, Integer> FindPQ(long n) {
        Pair<Integer, Integer>  PQ = new Pair(0, 0);
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
        // Put found p and q in pair and return
        return new Pair<Integer, Integer>(p, q);
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
    
    public static long calculatePrivateKey(long e, long p, long q){
        for(long x = 0; x<(Long.MAX_VALUE); x++){
            if(((e*x) % ((p-1)*(q-1))) == 1){
                return x;
            }
        }
        return -1;
    }
}

