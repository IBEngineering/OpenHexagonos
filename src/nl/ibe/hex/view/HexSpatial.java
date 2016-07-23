/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import nl.ibe.hex.game.HexCoordinate;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import nl.ibe.hex.game.HexPlayer;
import static nl.ibe.hex.game.HexPlayer.Type.*;

/**
 * This is a tile.
 * 
 * It has a stored coordinate, thus inverted
 * convertion is not needed.
 * 
 * It also contians node, and enum of who
 * owns this spatial, based on the last click.
 * 
 * @author MisterCavespider
 */
public class HexSpatial extends Geometry {
    
    //Coordinate
    private HexCoordinate coord;

    private Node node;
    
    //Owner etc.
    private Node ownerNode;
    private HexPlayer.Type ownerType;
    
    public HexSpatial(HexCoordinate hc, Node attachTo) {
        super("A hexagonSpatial", new HexagonMesh(1));
        this.coord = hc;
        
        setLocalTranslation(0, 0, 0);
        
        node = new Node("An node at: " + hc);
        
        ownerNode = new Node("An ownerNode at :" + hc);
        ownerNode.setLocalTranslation(0, 0.5f, 0);
        node.attachChild(ownerNode);
        
        attachTo.attachChild(node);
        
        attachMe();
    }
    
    private void attachMe() {
        node.attachChild(this);
    }
    
    public void setOwner(HexPlayer.Type type) {
        //Set all the values
        ownerType = type;
        
        //Empty the node
        ownerNode.detachAllChildren();
        
        if(type == CELL) {
            ownerNode.attachChild(ModelSupplier.getCell());
            ownerNode.attachChild(ModelSupplier.getFire(ModelSupplier.getCellColor(), ColorRGBA.White));
        } else if(type == BACTERIA) {
            ownerNode.attachChild(ModelSupplier.getBacterium());
            ownerNode.attachChild(ModelSupplier.getFire(ModelSupplier.getBacColor(), ColorRGBA.White));
        } else if(type == null) {
            //Nothing! It's gone!
        }
    }
    
    public void placeCorrectly() {
        Vector3f vec = CoordinateConverter.toVector(coord);
        node.setLocalTranslation(vec);
    }

    public HexCoordinate getCoord() {
        return coord;
    }
    
    public Node getOwnerAttach() {
        return ownerNode;
    }
}