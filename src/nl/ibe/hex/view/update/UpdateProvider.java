/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MisterCavespider
 */
public class UpdateProvider {
    
    public static HashMap<String,UpdateTicker> tickers = new HashMap<>();
    
    public static ArrayList<UpdateListener> listeners = new ArrayList<>();
    
    public static void update(float tpf) {
        for (Map.Entry<String, UpdateTicker> entry : tickers.entrySet()) {
            String key = entry.getKey();
            UpdateTicker value = entry.getValue();
            
            value.update();
        }
        
        for (UpdateListener listener : listeners) {
            listener.provUpdate(tpf);
        }
    }
    
}
