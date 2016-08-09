/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.math.ColorRGBA;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.settings.SettingsHQ;
import nl.ibe.hex.util.Random;
import nl.ibe.hex.util.Relator;

/**
 * Supplies colours.
 * 
 * @see ColorRGBA
 * @author MisterCavespider
 */
public class ColorSupplier {
    
    /**
     * A shortcut for getting the player's colour,
     * stated in the settings.
     * 
     * The colour should be approximated for the best result -
     * this way no 2 cells are the same. You can approximate
     * colours using {@link Random}.
     * 
     * @see Random
     * @param team  The player type.
     * @return      The desired colour.
     */
    public static ColorRGBA getPlayerColor(Team team) {
        
        if(team.getColourID().equals(0)) {
            float blue = (float) Random.approximate(0.8f, 0.2f);
            
            return new ColorRGBA(0.2f, 0.2f, blue, 1.0f);
        } else if(team.getColourID().equals(1)) {
            //Bacteria
            
            ColorRGBA color = SettingsHQ.Graphics.Colours.colour_bacteria.clone();
            float g = Random.approximate(color.g, 0.2f);
            
            color.g = g;
            
            return color;
            
            
        } else if(team.getColourID().equals(2)) {
            float red = (float) Random.approximate(0.8f, 0.2f);
            
            return new ColorRGBA(red, 0.2f, 0.2f, 0.8f);
        }   //End of ifs
        
        //If, somehow, the player wasn't any of those, give it a slight orange-alpha
        return new ColorRGBA(0.6f, 0.2f, 0, 0.4f);
    }
    
    /**
     * Gets the board's colour.
     * Straight from the settings.
     * 
     * @see SupplyRouter.SupplySettings
     * @return  The colour of the board.
     */
    public static ColorRGBA getBoardColor() {
        ColorRGBA c =  SupplyRouter.getSettings().boardColor.clone();
        c.r = (float) Random.approximate(c.r, SupplyRouter.getSettings().redApproximation);
        return c;
    }
    
    public static ColorRGBA fromNiftyFormat(String c) {
        
        switch (c.length()) {
            case 5:
                float[] values = new float[4];
                ColorRGBA color = new ColorRGBA();
                
                for (int a = 0; a < 4; a++) {
                    int i = Integer.parseInt(c.charAt(a+1) + "");
                    float f = Relator.relate(i, 9, 1.0f);
                    values[a] = f;
                }
                
                color.r = values[0];
                color.g = values[1];
                color.b = values[2];
                color.a = values[3];
                
                return color;
            case 9:
                return null;
            default:
                return null;
        }
        
    }
    
    public static String niftyFormat(ColorRGBA c) {
        float[] rgba = new float[4];
        
        rgba[0] = c.r;
        rgba[1] = c.g;
        rgba[2] = c.b;
        rgba[3] = c.a;
        
        String s = "";
        
        for (int i = 0; i < rgba.length; i++) {
            
            char character = Relator.toHexadecimal(rgba[i], 1);
            s = s + character;
            
        }
        
        return "#" + s;
    }
    
    public static String getNiftyBackground(String id) {
        
        if(id.equals("start")) {
            return niftyFormat(ColorRGBA.Blue);
        } else {
            return "#123f";
        }
        
    }
    
}
