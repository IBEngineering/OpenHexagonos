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
    
    ArrayList<UpdateTickListener> listeners;
    
    private String name;
    
    private long lastTime;
    private final double pieceOfTime;
    private final double hertz;
    
    public UpdateTicker(String name, double hertz, long startTime) {
        this.name = name;
        this.hertz = hertz;
        
        pieceOfTime = 1000/hertz;
        lastTime = startTime;
        
        handlers = new ArrayList<>();
        listeners = new ArrayList<>();
    }
    
    public void update() {
        long deltaTime = System.currentTimeMillis() - lastTime;
        
        if(deltaTime > pieceOfTime) {
            //Tick!
            for (UpdateTickHandler handler : handlers) {
                handler.onClick(this, lastTime);
            }
            lastTime = System.currentTimeMillis();
            
            for (UpdateTickListener listener : listeners) {
                
                listener.tickUpdate(hertz);
                
            }
        }
    }
    
    public void addHandler(UpdateTickHandler handler) {
        handlers.add(handler);
    }
    
    public void addListener(UpdateTickListener listener) {
        listeners.add(listener);
    }
    
}
