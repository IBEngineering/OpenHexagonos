/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hexgame;

/**
 *
 * @author mihaita
 */
public class Hexagon {
    
    private HexCoordinate coordinate; 
    private Hexagon[] neighbors = new Hexagon[6];
    
    public Hexagon(HexCoordinate c)
    {
        this.coordinate = c;
        
        for (int i = 0; i < 6; i++)
        {
            neighbors[i] = null;
        }
        
    }
    
    protected void setNeighbor(int i, Hexagon h)
    {
        neighbors[i] = h;
    }
    
    public Hexagon[] getNeighbors()
    {
        return neighbors;
    }
    
    public HexCoordinate getCoordinate()
    {
        return this.coordinate;
    }
}
