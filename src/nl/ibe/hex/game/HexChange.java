/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.Objects;

/**
 *
 * @author mihaita
 */
public class HexChange {
    
    private HexTile start;
    private HexTile end;
    private Type type;
    
    public static enum Type {
        DUPLICATION,
        JUMP,
        CONQUEST;
    }

    public HexChange(HexTile start, HexTile end, Type type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public HexTile getStart() {
        return start;
    }

    public HexTile getEnd() {
        return end;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HexChange{" + "start=" + start + ", end=" + end + ", type=" + type + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.start);
        hash = 41 * hash + Objects.hashCode(this.end);
        hash = 41 * hash + Objects.hashCode(this.type);
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
        final HexChange other = (HexChange) obj;
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
