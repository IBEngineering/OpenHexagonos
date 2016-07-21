/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex;

import nl.ibe.hexgame.HexCoordinate;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.HashMap;
import mygame.Main;

/**
 * This is a tile.
 * 
 * @author mihaita
 */
public class HexagonContainer {
    
    private Geometry hexGeom;
    private HexCoordinate coord;
    
    private HashMap<String, Object> attributes;
    
    public HexagonContainer(HexCoordinate hc, Node node) {
        this.coord = hc;
        
        //Initialize the geometry
        HexagonMesh mesh = new HexagonMesh(1);
        hexGeom = new Geometry("A hexagon in a hexGrid " + hc.toString(), mesh);
        hexGeom.setMaterial(Main.getRandomMat());
        node.attachChild(hexGeom);
    }
    
    public void place(float x, float y, float z) {
        hexGeom.setLocalTranslation(x, y, z);
    }
    
    public void place(Vector3f vec) {
        hexGeom.setLocalTranslation(vec);
    }
}