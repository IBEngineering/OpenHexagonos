/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import nl.ibe.hex.view.Animation;

/**
 *
 * @author MisterCavespider
 */
public class UpdateProvider {
    
    public static HashMap<String,UpdateTicker> tickers = new HashMap<>();
    
    public static ArrayList<UpdateListener> listeners = new ArrayList<>();
    
    private static ArrayList<UpdateListener> removals = new ArrayList<>();
    private static ArrayList<UpdateListener> addals = new ArrayList<>();
    
    public static void update(float tpf) {
        for (Map.Entry<String, UpdateTicker> entry : tickers.entrySet()) {
            String key = entry.getKey();
            UpdateTicker value = entry.getValue();
            
            value.update();
        }
        
        for (UpdateListener listener : listeners) {
            listener.provUpdate(tpf);
        }
        
        //Delete the removals
        for (UpdateListener listener : removals) {
//            listeners.remove(listener);
        }
        
        //Add the addals
        for (UpdateListener listener : addals) {
            listeners.add(listener);
        }
    }

    public static void qeueRemove(UpdateListener l) {
        removals.add(l);
    }

    public static void qeueAdd(UpdateListener l) {
        addals.add(l);
    }
    
}
