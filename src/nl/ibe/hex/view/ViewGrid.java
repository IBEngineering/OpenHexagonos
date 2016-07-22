/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import nl.ibe.hex.game.HexCoordinate;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mygame.Main;
import nl.ibe.hex.game.HexTile;

/**
 *
 * @author mihaita
 */
public class ViewGrid {
    
    protected int size; //In amount of hexagons.
    
    //For spatials
    protected Node node;
    
    protected HashMap<HexCoordinate, HexSpatial> grid;
    
    public ViewGrid(int size, Node rootNode) {
        this.size = size;
        
        this.grid = new HashMap<>();
        this.node = new Node("hexgrid node");
        rootNode.attachChild(node);
        
        generateField();
        
        //Put everything on a location
        for (Map.Entry<HexCoordinate, HexSpatial> entry : grid.entrySet()) {
            
            HexCoordinate hexCoordinate = entry.getKey();
            HexSpatial hexagon = entry.getValue();
            
            
        }
    }
    
    public ViewGrid(ConcurrentHashMap<HexCoordinate, HexTile> board, Node node) {
        //Loop the HashMap and put everything on the screen
        
        for (Map.Entry<HexCoordinate, HexTile> entry : board.entrySet()) {
            HexCoordinate key = entry.getKey();
            HexTile value = entry.getValue();
            
            HexSpatial spatial = new HexSpatial(key);
            spatial.placeCorrectly();
            spatial.setMaterial(Main.getRandomMat());
            
            node.attachChild(spatial);
            
            grid.put(key, spatial);
        }
        
    }
    
    /**
     * It uses the "+x-y line" algorythm.
     */
    protected final void generateField() {
        
        //Loop the x (i) from -size to size
        
        int x = -1*size;
        while(x<size+1) {
            
            int y = -1*size;
            //Loop the y
            while(y<size+1) {
                
                
                if (x+y <= size && x+y >= -1*size) {

                    //Loop the z
                    int z = -1*x -y;

                    HexCoordinate coord = new HexCoordinate(x, y, z);

                    System.out.println("Coord:" + coord.toString());
                    
                    //Place it
                    HexSpatial h = new HexSpatial(coord);
                    h.placeCorrectly();
                    
                    grid.put(coord, h);
                }
                y++;
            }
            
            
            x++;
        }
        
    }
}
