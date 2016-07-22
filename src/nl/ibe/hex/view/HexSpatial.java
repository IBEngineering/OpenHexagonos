/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.Vector3f;
import nl.ibe.hex.game.HexCoordinate;
import com.jme3.scene.Geometry;
import java.util.HashMap;

/**
 * This is a tile.
 * 
 * It has a stored coordinate, thus inverted
 * convertion is not needed.
 * 
 * @author MisterCavespider
 */
public class HexSpatial extends Geometry {
    
    private HexCoordinate coord;
    
    private HashMap<String, Object> attributes;
    
    public HexSpatial(HexCoordinate hc) {
        super("A hexagonSpatial", new HexagonMesh(1));
        this.coord = hc;
        
        setLocalTranslation(0, 0, 0);
    }

    public void placeCorrectly() {
        Vector3f vec = CoordinateConverter.toVector(coord);
        setLocalTranslation(vec);
    }

    public HexCoordinate getCoord() {
        return coord;
    }
}