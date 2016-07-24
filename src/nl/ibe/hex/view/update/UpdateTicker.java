/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

import java.util.ArrayList;

/**
 *
 * @author MisterCavespider
 */
public class UpdateTicker {
    
    ArrayList<UpdateTickHandler> handlers;
    
    private long lastTime;
    private double pieceOfTime;
    
    public UpdateTicker(double hertz, long startTime) {
        pieceOfTime = 1000/hertz;
        lastTime = startTime;
        
        handlers = new ArrayList<>();
    }
    
    public void update() {
        long deltaTime = System.currentTimeMillis() - lastTime;
        
        if(deltaTime > pieceOfTime) {
            //Tick!
            for (UpdateTickHandler handler : handlers) {
                handler.onClick(this, lastTime);
            }
            lastTime = System.currentTimeMillis();
        }
    }
    
    public void addHandler(UpdateTickHandler handler) {
        handlers.add(handler);
    }
    
}
