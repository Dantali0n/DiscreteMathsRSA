/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimefactors;

import com.sun.javafx.css.CalculatedValue;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * @author geoffrey
 */
public class Decrypt {
    static long n = 0; // p times q
    static String encryptedMessage = "25039,10674,20519,715,21302,12500,27532,19133,211127,646,20519,715,19133,12500,29128,29365,20519"; // message to decrypt
    static long p = 0;
    static long q = 0;
    static long e = 0;
    static long ePrivateKey = 0;
    //we receive a public key and a message
    //public key has n and e
    //call findPQ with n
    //then pq should be relative prime with given e, 
    //else retry
    //if yes we cna find privatekey by doing e * x(?) mod (p - 1)(q - 1)
    //private key is: n, x
    
    //e =11, p = 151, q=197
    //public static long privateKey = FindPrimeFactors.calculatePrivateKey (11, 151, 197);
    
     public static void main(String[] args) throws IOException {
         n = 29747;
         e = 11;
         BigInteger bi1, bi2;
         for (Map.Entry<Integer, Integer> entry : FindPrimeFactors.FindPQ(n).entrySet()) {
            p = entry.getKey();
            q = entry.getValue();
        }
        
         
        ePrivateKey = FindPrimeFactors.calculatePrivateKey(e, p, q);
     
        System.out.println("privaetkely: " + ePrivateKey );
        
        bi1 = new BigInteger("25039");
        
        String[] letters = encryptedMessage.split(",");
        for (String letter : letters) {
           bi1 = new BigInteger(letter); 
           bi2 = bi1.pow((int)ePrivateKey);
           System.out.print("" + (char)Integer.parseInt(bi2.mod(new BigInteger(Long.toString(n))).toString()));
        }
       
        //System.out.println("for letter: " + message.charAt(0));
     }
}
