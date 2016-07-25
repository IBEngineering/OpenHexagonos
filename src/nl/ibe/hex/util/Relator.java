/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.util;

/**
 *
 * @author MisterCavespider
 */
public class Relator {
    
    protected static char base;
    
    /**
     * Gets the relativity of value to maxValue.
     * The max is used to 'cast' the answer, as a maximum
     * answer.
     * maxValue corresponds to max.
     * 
     * Uses a cross-table:
     * (value*max)/maxValue.
     * 
     * @param value     What is being related
     * @param maxValue  The maximum value could be
     * @param max       The maximum of the return value
     * @return      value relative to maxValue, maximum max
     */
    public static double relate(double value, double maxValue, double max) {
        
        double answer = (value*max)/maxValue;
        return answer;
        
    }
    
    public static float relate(float value, float maxValue, float max) {
        return (float) relate((double) value, (double) maxValue, (double) max);
    }
    
    public static char toHexadecimal(double value, double maxValue) {
        
        int hexDecDouble = (int) relate(value, maxValue, 16);
        
        char c;
        
        if(hexDecDouble > 9) {
            //use abcdef
            int diff = 16-hexDecDouble;
            
            c = (char) (base + diff);
        } else {
            
            //Gets the first (and only) char of the String of the int
            c = Integer.toString(hexDecDouble).charAt(0);
            
        }
        
        return c;
        
    }
    
}
