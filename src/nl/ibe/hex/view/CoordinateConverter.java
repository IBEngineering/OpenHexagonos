/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.Vector3f;
import nl.ibe.hex.game.HexCoordinate;

/**
 * A converter for hexagonal coordinates.
 * 
 * @author MisterCavespider
 */
public class CoordinateConverter {
    
    /**
     * Converts a HexCoordinate to a Vector3f.
     * 
     * top-pointing.
     * 
     * 1 height unit = the diameter.
     * 1 width unit = sqrt(3)/2 * height.
     * 
     * 1 vertical distance = height * 0.75.
     * 1 horizontal distance = width.attachTo.
     * 
     * x = (hex) z * vertical distance.
     * y = 0.
     * z = (hex) x * horizontal distance + 0.5 * (hex) z * horizontal distance.
     * 
     * @param c The hexagonal coordinate
     * @return  The vector of the coordinate
     */
    public static Vector3f toVector(HexCoordinate c) {
        
        int size = 2;
        
        float height = size;
        float width = (float) (Math.sqrt(3.0)/2.0 * height);
        
        float vertiz = height * 0.75f;
        float horiz = width;
        
        float x = c.z * vertiz;
        float z = c.x * horiz + (0.5f * c.z * horiz); 
        return new Vector3f(z,-1,x);
        
    }
    
}
