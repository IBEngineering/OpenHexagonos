/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MisterCavespider
 */
public class UpdateProvider {
    
    public static ArrayList<UpdateTicker> tickers = new ArrayList<>();
    
    public static ArrayList<UpdateListener> listeners = new ArrayList<>();
    
    public static void update(float tpf) {
        for (UpdateTicker ticker : tickers) {
            ticker.update();
        }
        
        for (UpdateListener listener : listeners) {
            listener.provUpdate(tpf);
        }
    }
    
}
