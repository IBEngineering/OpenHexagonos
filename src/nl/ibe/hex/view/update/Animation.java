/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

/**
 *
 * @author MisterCavespider
 */
public interface Animation {
    
    /**
     * 
     * Insert an animation, based on the current time.
     * 
     * @param currentTime   The current time.
     */
    public void calculation(long currentTime);
    
}