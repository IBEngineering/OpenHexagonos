/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

/**
 *
 * @author MisterCavespider
 */
public class ID {
    
    private long id;
    
    private String extraSpecification;
    
    /**
     * Generates an ID based on a long and a string.
     * 
     * 
     * @param s
     * @param l
     */
    public ID(String s, long l) {
        
        id = l;
        
        char[] chars = s.toCharArray();
        
        for (char c : chars) {
            id = id*c;
        }
        
    }
    
    /**
     * Generates an ID based on a long and a String.
     * Also adds the extraSpecification
     * 
     * @param s     the string
     * @param l     the long
     * @param extra the extraSpecification
     */
    public ID(String s, long l, String extra) {
        id = l;
        
        char[] chars = s.toCharArray();
        
        for (char c : chars) {
            id = id*c;
        }
        
        extraSpecification = extra;
    }

    public long getId() {
        return id;
    }

    public String getExtraSpecification() {
        return extraSpecification;
    }
    
}
