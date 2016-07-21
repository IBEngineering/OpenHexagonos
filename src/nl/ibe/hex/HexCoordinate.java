/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex;

/**
 * A coordinate with 3 values.
 * 
 * @author mihaita
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
    
    @Override
    public String toString()
    {
        return "HexCoordinate: [ "+x+", "+y+", "+z+" ]";
    }
}
