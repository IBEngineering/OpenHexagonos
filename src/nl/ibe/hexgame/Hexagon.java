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
    private Hexagon[] neighbors;
    
    public Hexagon(HexCoordinate c)
    {
        this.coordinate = c;
    }
    
    
}
