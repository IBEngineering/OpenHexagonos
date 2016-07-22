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

/**
 *
 * @author mihaita
 */
public class HexGrid {
    
    protected int size; //In amount of hexagons.
    
    //For spatials
    protected Node node;
    
    protected HashMap<HexCoordinate, HexagonContainer> grid;
    
    public HexGrid(int size, Node rootNode) {
        this.size = size;
        
        this.grid = new HashMap<HexCoordinate, HexagonContainer>();
        this.node = new Node("hexgrid node");
        rootNode.attachChild(node);
        
        generateField();
        
        //Put everything on a location
        for (Map.Entry<HexCoordinate, HexagonContainer> entry : grid.entrySet()) {
            
            HexCoordinate hexCoordinate = entry.getKey();
            HexagonContainer hexagon = entry.getValue();
            
            
        }
    }
    
    private Vector3f getHexPosition(HexCoordinate c)
    {
        int size = 2;
        
        float height = size;
        float width = (float) (Math.sqrt(3.0)/2.0 * height);
        
        float vertiz = height * 0.75f;
        float horiz = width;
        
        float x = c.z * vertiz;
        float z = c.x * horiz + (0.5f * c.z * horiz); 
        return new Vector3f(z,-1,x);
        
        
        
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
                    HexagonContainer h = new HexagonContainer(coord, node);
                    h.place(getHexPosition(coord));
                    
                    grid.put(coord, h);
                }
                y++;
            }
            
            
            x++;
        }
        
    }
}
