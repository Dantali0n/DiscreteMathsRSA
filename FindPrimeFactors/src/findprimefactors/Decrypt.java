/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimefactors;

import static findprimefactors.FindPrimeFactors.FindPQ;
import java.util.HashMap;
import java.math.BigInteger;
import javafx.util.Pair;

/**
 *
 * @author geoffrey
 */
public class Decrypt {
    static long n = 0; // p times q
    static String encryptedMessage = "25039,10674,20519,715,21302,12500,27532,19133,21127,646,20519,715,19133,12500,29128,29365,20519,715,19133,12500,29128,29365,20519,"
            + "12500,646,10674,19133,12500,939,4719,12738,12738,4719,7373,20826,12500,21302,29365,12500,646,21127,21127,19133,8765,21302,12500,21302,10014,19133,12500,"
            + "10674,4719,715,21814,23939,12500,7373,29365,21302,12500,27532,19133,21127,646,20519,715,19133,12500,4719,21302,20529,715,12500,715,646,6750,19133,12500"
            + "29365,10674,12500,21127,19133,10674,21302,646,4719,7373,12646,12500,8356,17576,7373,29128,11153,29365,20519,715"; // message to decrypt
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

    // Giant memory inefficient lookup table
    static HashMap<BigInteger, Character> decryptMapping = new HashMap();

    public static void run(long argN, long argE, String argMessage) {
        n = argN;
        e = argE;
        encryptedMessage = argMessage;
        BigInteger bi1, bi2;
        Pair<Integer, Integer> entry = FindPQ(n);
        p = entry.getKey();
        q = entry.getValue();
        ePrivateKey = FindPrimeFactors.calculatePrivateKey(e, p, q);
        System.out.println("private key: " + ePrivateKey );

        String[] letters = encryptedMessage.split(",");
        for (String letter : letters) {
           // Convert string to BigInteger
           bi1 = new BigInteger(letter);

	   // Lookup table does not contain character?
           if(!decryptMapping.containsKey(bi1)) {
               // Decrypt character and store in lookup table
               bi2 = bi1.pow((int)ePrivateKey); // Calculate character to the power of ePrivateKey
               decryptMapping.put(bi1,(char)Integer.parseInt(bi2.mod(new BigInteger(Long.toString(n))).toString()));
           }
           // Output characters
           System.out.print(decryptMapping.get(bi1));
           //System.out.print("" + (char)Integer.parseInt(bi2.mod(new BigInteger(Long.toString(n))).toString()));
        }
        System.out.println();
    }
}
