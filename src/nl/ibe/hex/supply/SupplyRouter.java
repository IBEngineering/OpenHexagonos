/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;

/**
 * Routes stuff and methods to the suppliers.
 * 
 * This class only functions when startRouting() has been called.
 * 
 * @see nl.ibe.hex.supply
 * 
 * @author MisterCavespider
 */
public class SupplyRouter {

    /**
     * Settings for all the suppliers.
     * It has default values.
     */
    public static class SupplySettings {
        
        public ColorRGBA boardColor = new ColorRGBA(0.2f, 0.1f, 0.3f, 0.4f);
        
        /**
         * THIS IS UNFINISHED.
         * @param loadDefault   Should it load default values?
         */
        public SupplySettings(boolean loadDefault) {
            if(loadDefault) {
                
            }
        }
        
    }
    
    //For using jME
    public static AssetManager assetManager;
    public static SimpleApplication app;
    
    private static SupplySettings settings;
    
    /**
     * Alternate constructor,
     * starts the system.
     * 
     * @param app   Needed for it's managers.
     * @param sets  The desired settings.
     */
    public static void startRouting(SimpleApplication app, SupplySettings sets) {
        SupplyRouter.app = app;
        assetManager = app.getAssetManager();
        settings = sets;
    }
    
    /**
     * Checks wether the the assetManager is null.
     * @return  wether the assetManager is null.
     */
    public static boolean checkManager() {
        if(assetManager == null) {
            System.err.println("There is no assetManager!!");
            return false;
        } else {
            //Nothing, it's in place
            return true;
        }
    }
    
    public static SupplySettings getSettings() {
        return settings;
    }
    
}
