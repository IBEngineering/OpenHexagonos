/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game;

import java.util.ArrayList;

/**
 * A coordinate with 3 values.
 * 
 * @author MisterCavespider, b0rg3rt
 */
public class HexCoordinate {
    
    public int x;
    public int y;
    public int z;
    
    public HexCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
      
    public HexCoordinate add(HexCoordinate other)
    {
        return new HexCoordinate(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    
    public HexCoordinate scale(float val)
    {
        return new HexCoordinate((int)(this.x * val), (int)(this.y * val), (int)(this.z * val));
    }
    
    public int distance(HexCoordinate other)
    {
        return (Math.abs(this.x - other.x) + Math.abs(this.y - other.y) + Math.abs(this.z - other.z)) / 2;
    }
    
    public HexCoordinate getNeighbor(int i)
    {
        switch(i) {
            case 0:
                return this.add(new HexCoordinate( -1, +1, 0));
            case 1:
                return this.add(new HexCoordinate( 0, +1, -1));                
            case 2:
                return this.add(new HexCoordinate( +1, 0, -1));
            case 3:
                return this.add(new HexCoordinate( +1, -1, 0));
            case 4:
                return this.add(new HexCoordinate( 0,  -1, +1)); 
            case 5:
                return this.add(new HexCoordinate( -1,  0, +1));
            default:
                return null;
        }
      
    }
    
    public ArrayList<HexCoordinate> ring(int radius)
    {
        
        ArrayList<HexCoordinate> result = new ArrayList();
        HexCoordinate neigh4 = this.getNeighbor(4);
        
        HexCoordinate cube = neigh4;
        for (int i =1; i < radius; i++)
        {
            cube = cube.getNeighbor(4);
        }
                
        for (int i = 0; i < 6; i++)
        {
            for (int j =0; j < radius; j++)
            {
                result.add(cube);
                cube = cube.getNeighbor(i);
            }
        }
        return result;
    }
    
    
    @Override
    public String toString()
    {
        return "HexCoordinate: [ "+x+", "+y+", "+z+" ]";
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        hash = 53 * hash + this.z;
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
        final HexCoordinate other = (HexCoordinate) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }
    
    
    
}
