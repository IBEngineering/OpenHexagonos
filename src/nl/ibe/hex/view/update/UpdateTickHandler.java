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
public interface UpdateTickHandler {
    
    public static UpdateTickHandler buildPrinter() {
        return new UpdateTickHandler() {
            @Override
            public void onClick(UpdateTicker prov, long time) {
                System.out.println("Tick!");
                System.out.println("" + prov + time);
            }
        };
    }
    
    public void onClick(UpdateTicker prov, long time);
    
}