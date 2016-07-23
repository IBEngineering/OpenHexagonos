/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.util;

/**
 * Just a helper class.
 * 
 * It has extensive random functions.
 * 
 * DISCLAIMER: It does not randomly generate numbers,
 * it only uses Math.random() in a smart way.
 * 
 * @author MisterCavespider
 */
public class Random {
    
    /**
     * Generates a random number between min and max.
     * 
     * @param min   The minimum value.
     * @param max   The maximum value.
     * @return      The random value.
     */
    public static double rdm(double min, double max) {
        double delta = max - min;
        double randomNr = min + Math.random()*delta;
        return randomNr;
    }
    
    /**
     * Calls and casts the other rdm().
     */
    public static float rdm(float min, float max) {
        return (float) rdm((double) min, (double) max);
    }
    
    /**
     * Calls and casts the other rdm().
     */
    public static int rdm(int min, int max) {
        return (int) rdm((double) min, (double) max);
    }
    
}
