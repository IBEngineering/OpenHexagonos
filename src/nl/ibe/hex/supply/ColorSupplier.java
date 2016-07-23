/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.math.ColorRGBA;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.util.Random;

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
     * @param type  The player type.
     * @return      The desired colour.
     */
    public static ColorRGBA getPlayerColor(HexPlayer.Type type) {
        switch(type) {
            case BACTERIA: {
                float green = Random.rdm(0.35f, 1f);
                return new ColorRGBA(0.2f, green, 0.2f, 1);
            }
            case CELL: {
                float blue = Random.rdm(0.35f, 1f);
                return new ColorRGBA(0.2f, 0.2f, blue, 1);
            }
            case BLOCKER: {
                //Pure red
                return ColorRGBA.Red;
            }
        }   //End of switch
        
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
    
}
