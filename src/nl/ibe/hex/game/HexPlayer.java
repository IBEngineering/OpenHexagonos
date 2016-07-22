/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

/**
 *
 * @author b0rg3rt
 */
public class HexPlayer {
    
    private String name;
    private boolean human = true;
    private Type type;
    
    public static enum Type {
        
        CELL,
        BACTERIA,
        BLOCKER;
        
    }

    public HexPlayer(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return human;
    }

    public Type getType() {
        return type;
    }
    
    
}
