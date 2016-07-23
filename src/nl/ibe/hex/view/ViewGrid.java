/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.ColorRGBA;
import nl.ibe.hex.game.HexCoordinate;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mygame.Main;
import nl.ibe.hex.game.HexTile;

/**
 *
 * @author MisterCavespider
 */
public class ViewGrid {
    
    protected int radius; //In amount of hexagons.
    
    //For spatials
    protected Node gridNode;
    
    protected HashMap<HexCoordinate, HexSpatial> grid;
    
    public ViewGrid(ConcurrentHashMap<HexCoordinate, HexTile> board, Node superNode) {
        
        this.grid = new HashMap<>();
        this.gridNode = new Node("hexgrid node");
        superNode.attachChild(gridNode);
        
        //Loop the HashMap and put everything on the screen
        
        for (Map.Entry<HexCoordinate, HexTile> entry : board.entrySet()) {
            HexCoordinate key = entry.getKey();
            
            HexSpatial spatial = new HexSpatial(key, gridNode);
            spatial.placeCorrectly();
            spatial.setMaterial(ModelSupplier.getBoardMaterial());
            
            if (entry.getValue().getOwner() != null)
            {
                spatial.setMaterial(ModelSupplier.getColoredMaterial(ColorRGBA.Pink));
            }
            
            grid.put(key, spatial);
        }
        
    }
}
