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
    protected Node node;
    
    protected HashMap<HexCoordinate, HexSpatial> grid;
    
    public ViewGrid(ConcurrentHashMap<HexCoordinate, HexTile> board, Node node) {
        
        this.grid = new HashMap<>();
        this.node = new Node("hexgrid node");
        node.attachChild(this.node);
        
        //Loop the HashMap and put everything on the screen
        
        for (Map.Entry<HexCoordinate, HexTile> entry : board.entrySet()) {
            HexCoordinate key = entry.getKey();
            
            HexSpatial spatial = new HexSpatial(key);
            spatial.placeCorrectly();
            spatial.setMaterial(Main.getColoredMaterial(new ColorRGBA(0.1f, 0.2f, 0.3f, 1)));
            
            if (entry.getValue().getOwner() != null)
            {
                spatial.setMaterial(Main.getColoredMaterial(ColorRGBA.Pink));
            }
            
            this.node.attachChild(spatial);
            
            grid.put(key, spatial);
        }
        
    }
}
