/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package findprimefactors;

import com.sun.javafx.css.CalculatedValue;
import java.io.IOException;

/**
 *
 * @author geoffrey
 */
public class Decrypt {
    public static long privateKey = FindPrimeFactors.calculatePrivateKey (11, 151, 197);
    
     public static void main(String[] args) throws IOException {
         System.out.println("privaetkely: " + privateKey );
     
     }
}
