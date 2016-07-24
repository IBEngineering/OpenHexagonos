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
import nl.ibe.hex.game.HexTile;
import nl.ibe.hex.supply.MaterialSupplier;

/**
 * A grid for all the HexSpatials.
 * 
 * Contains a HashMap and Node.
 * 
 * @author MisterCavespider
 */
public class ViewGrid {
    
    //For spatials
    protected Node gridNode;
    
    protected HashMap<HexCoordinate, HexSpatial> grid;
    
    /**
     * Main constructor.
     * 
     * It will also attach the nodes, you only have to give it.
     * 
     * @param board     The HexBoard, created in the HexGame.
     * @param superNode Where to attach the node to.
     */
    public ViewGrid(ConcurrentHashMap<HexCoordinate, HexTile> board, Node superNode) {
        
        this.grid = new HashMap<>();
        this.gridNode = new Node("hexgrid node");
        superNode.attachChild(gridNode);
        
        //Loop the HashMap and put everything on the screen
        
        int counter = 0;
        
        for (Map.Entry<HexCoordinate, HexTile> entry : board.entrySet()) {
            HexCoordinate key = entry.getKey();
            
            HexSpatial spatial = new HexSpatial(key, gridNode);
            spatial.placeCorrectly();
            spatial.setMaterial(MaterialSupplier.getBoardMaterial());
            
            if (entry.getValue().getOwner() != null)
            {
                spatial.setMaterial(MaterialSupplier.getColoredMaterial(ColorRGBA.Pink));
            }
            
            grid.put(key, spatial);
            counter++;
        }
        
        System.err.println("======" + counter + "======");
        
    }

    /**
     * Returns the grid, a HashMap.
     * @return  The grid.
     */
    public HashMap<HexCoordinate, HexSpatial> getGrid() {
        return grid;
    }
    
    
}
