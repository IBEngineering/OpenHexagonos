/*
 * Do what you want with the code.
 * (This is an open-source project).
 */
package nl.ibe.hex.settings;

import com.jme3.math.ColorRGBA;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ibe.hex.supply.ColorSupplier;
import nl.mrcave.zml.ZML;

/**
 * Contains a tree of subclasses
 * 
 * @author MisterCavespider
 */
public class SettingsHQ {
    
    public static void readBase() {
        
        try {
            
            
            File f = new File("assets/Settings/links.zml");
            ZML links = new ZML(f);
            
            String graphicsPath = links.getFc().findField("graphicspath").getRealValue();
            
            Graphics.read(graphicsPath);
            
            
        } catch (FileNotFoundException fnfe) {
            Logger.getLogger(SettingsHQ.class.getName()).log(Level.SEVERE, null, fnfe);
        }
    }
    
    public static class Graphics {
        
        public static class Colours {
            
            public static ColorRGBA colour_bacteria;
            public static float bac_distort;
            
            protected static void read(ZML graphics) {
                String rawColour_bacteria = graphics.getFc().findField("colour_bacteria").getRealValue();
                colour_bacteria = ColorSupplier.fromNiftyFormat(rawColour_bacteria);
                
                bac_distort = (Float) graphics.getFc().findField("colour_bacteria_aprox").getValue();
                
                System.out.println(colour_bacteria.toString());
            }
            
        }
        
        public static String test;
        
        protected static void read(String path) throws FileNotFoundException {
            File f = new File(path);
            ZML graphics = new ZML(f);
            
            Colours.read(graphics);
        }
        
    }
    
}
