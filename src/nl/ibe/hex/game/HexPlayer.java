/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (this.human ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HexPlayer other = (HexPlayer) obj;
        if (this.human != other.human) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HexPlayer{" + "name=" + name + ", human=" + human + ", type=" + type + '}';
    }
    
    
    
}
