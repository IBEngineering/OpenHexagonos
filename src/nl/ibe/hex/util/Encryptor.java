/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihaita
 */
public class Encryptor {
    
    protected static abstract class SettingsEncryptor {
        
        public static char enc(char c) {
//            return (char) (c^(1/3) * 10);
            
//            char x = c;
//            x = (char) Math.pow(x, (1/3));
//            x = (char) (x * 10);
//            return x;

            return (char) (c*2 - 1);
        }
        
        public static char dec(char x) {
//            return (char) ();
//            char c = x;
//            c = (char) (c/10);
//            c = (char) Math.pow(c, 3);
//            return c;

            return (char) ((x + 1) / 2);
        }
    }
    
    /**
     * Encrypts a char.
     * 
     * String s tells what encryptor to use.
     * For instance, "settings" will result in
     * (c^(1/3) * 10).
     * If no useable encryptor has been found, (c + 1)
     * is used,
     * 
     * @param identify How to encrypt it.
     * @param c
     * @return
     */
    public static char encryptChar(String identify, char c) {
        if(identify.equalsIgnoreCase("settings")) {
            return SettingsEncryptor.enc(c);
        } else {
            return (char) (c + 1);
        }
    }
    
    public static char decryptChar(String identify, char c) {
        if(identify.equalsIgnoreCase("settings")) {
            return SettingsEncryptor.dec(c);
        } else {
            return (char) (c - 1);
        }
    }
    
    public static String encryptString(String identify, String s) {
        char[] pseudoString = s.toCharArray();
        
        String answer = "";
        for (char c : pseudoString) {
            answer = answer + encryptChar(identify, c);
        }
        
        return answer;
    }
    
    public static String decryptString(String identify, String s) {
        char[] pseudoString = s.toCharArray();
        
        String answer = "";
        for (char c : pseudoString) {
            answer = answer + decryptChar(identify, c);
        }
        
        return answer;
    }
    
    public static List<String> encrypt(String identify, List<String> list) {
        ArrayList<String> answers = new ArrayList<>();
        for (String string : list) {
            answers.add(encryptString(identify, string));
        }
        
        return answers;
    }
    
    public static List<String> decrypt(String identify, List<String> list) {
        ArrayList<String> answers = new ArrayList<>();
        for (String string : list) {
            answers.add(decryptString(identify, string));
        }
        
        return answers;
    }
}
