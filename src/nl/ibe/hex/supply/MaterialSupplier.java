/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import nl.ibe.hex.game.HexPlayer;
import static nl.ibe.hex.supply.SupplyRouter.*;

/**
 * Supplies materials.
 * 
 * @see Material
 * @author MisterCavespider
 */
public class MaterialSupplier {
    
    /**
     * A shortcut for getting materials with random colours.
     * 
     * @return  The material with random colours.
     */
    public static Material getRandomMaterial() {
        return getColoredMaterial(ColorRGBA.randomColor());
    }
    
    /**
     * A shortcut for getting the player's coloured material.
     * 
     * @param p The player type.
     * @return  The player's coloured material.
     */
    public static Material getPlayerMaterial(HexPlayer.Type p) {
        return getColoredMaterial(ColorSupplier.getPlayerColor(p));
    }
    
    /**
     * A shortcut for getting a coloured material.
     * 
     * @param color The desired colour.
     * @return      The desired material.
     */
    public static Material getColoredMaterial(ColorRGBA color) {
        if(!checkManager()) {return null;}
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        
        return mat;
    }
    
    /**
     * Gets the material based on the board's colour,
     * stated in the settings.
     * 
     * @return  A material with the board's colour.
     */
    public static Material getBoardMaterial() {
        return getColoredMaterial(ColorSupplier.getBoardColor());
    }
}
