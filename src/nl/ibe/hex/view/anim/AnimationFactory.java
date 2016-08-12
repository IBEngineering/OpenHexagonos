/*
 * Do what you want with the code.
 * (This is an open-source project).
 */
package nl.ibe.hex.view.anim;

import com.jme3.math.Vector3f;
import nl.ibe.hex.game.HexChange;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.supply.ModelSupplier;
import nl.ibe.hex.view.HexSpatial;
import nl.ibe.hex.view.View;

/**
 *
 * @author MisterCavespider
 */
public class AnimationFactory {
    
    public static Animation buildAnimation(HexChange change, View view, boolean run) {
        HexPlayer player;
        HexSpatial startH, endH;
        Vector3f startPos, endPos, moveVec;
        Animation prod;
        
        switch (change.getType()) {
            
            case DUPLICATION:
                player = change.getStart().getOwner();
                break;
            case JUMP:
                player = change.getEnd().getOwner();
                break;
            case CONQUEST:
                player = change.getStart().getOwner();
                break;
            default:
                player = change.getStart().getOwner();
                break;
            
        }
        
        
        startH = view.grid.getGrid().get(change.getStart().getCoordinate());
        endH = view.grid.getGrid().get(change.getEnd().getCoordinate());
        
        startPos = startH.getWorldTranslation();
        endPos = endH.getWorldTranslation();
        
        moveVec = endPos.subtract(startPos);
        
        prod = new Animation(startPos, endPos, moveVec, view.getRootNode(), ModelSupplier.getPlayerModel(player));
        
        if(run){prod.go();}
        
        return prod;
    }
    
}
