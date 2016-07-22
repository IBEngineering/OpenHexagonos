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
public class HexTile {
    
    /**
     *
     * @author MisterCavespider
     */
    public static class Direction {
        
        public static final int RIGHT = 0;
        public static final int TOP_RIGHT = 1;
        public static final int TOP_LEFT = 2;
        public static final int LEFT = 3;
        public static final int BOTTOM_LEFT = 4;
        public static final int BOTTOM_RIGHT = 5;
        
    }
    
    private HexCoordinate coordinate; 
    private HexTile[] neighbors = new HexTile[6];
    private HexPlayer owner = null;
    
    public HexTile(HexCoordinate c)
    {
        this.coordinate = c;
        
        for (int i = 0; i < 6; i++)
        {
            neighbors[i] = null;
        }
        
    }
    
    protected void setNeighbor(int i, HexTile h)
    {
        neighbors[i] = h;
    }
    
    public HexTile[] getNeighbors()
    {
        return neighbors;
    }
    
    public HexCoordinate getCoordinate()
    {
        return this.coordinate;
    }

    public HexPlayer getOwner() {
        return owner;
    }
    
    
}
