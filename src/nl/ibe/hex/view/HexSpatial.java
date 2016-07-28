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
import com.jme3.scene.Spatial;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.supply.*;

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
    
    //Node
    private Node node;
    
    //Owner etc.
    private Node ownerNode;
    private Team ownerTeam;
    private Spatial spatial;
    
    /**
     * Standard constructor.
     * 
     * It manages the nodes and places the mesh
     * on the correct place.
     * 
     * The node is attachd to the superNode.
     * The has the ownerNode and the hexSpatial.
     * 
     * @param hc
     * @param superNode
     */
    public HexSpatial(HexCoordinate hc, Node superNode) {
        super("A hexagonSpatial", new HexagonMesh(1));
        this.coord = hc;
        
        super.setLocalTranslation(0, 0, 0);
        
        node = new Node("An node at: " + hc);
        
        ownerNode = new Node("An ownerNode at :" + hc);
        ownerNode.setLocalTranslation(0, 0.5f, 0);
        node.attachChild(ownerNode);
        
        superNode.attachChild(node);
        
        attachMe();
    }
    
    /**
     * Only made so NetBeans will shut up.
     */
    private void attachMe() {
        node.attachChild(this);
    }
    
    /**
     * Makes something that hovers above the spatial.
     * It represents the owner - the player that
     * has this tile in possesion.
     * 
     * @see ModelSupplier
     * @param team  The (type of) player.
     */
    public void setOwner(Team team) {
        //Set all the values
        ownerTeam = team;
        
        //Empty the node
        ownerNode.detachAllChildren();
        
        //Attach the stuff
        if(team != null) {
            Spatial s = ModelSupplier.getPlayerShape(team);
            this.spatial = s;
            ownerNode.attachChild(s);
            //ownerNode.attachChild(ParticleSupplier.getFire(ColorSupplier.getPlayerColor(type), ColorRGBA.White));
        }
    }
    
    /**
     * Places the hexagon on the correct place.
     * This is based on the hexCoordinate.
     * 
     * @see CoordinateConverter
     */
    public void placeCorrectly() {
        Vector3f vec = CoordinateConverter.toVector(coord);
        node.setLocalTranslation(vec);
    }

    public HexCoordinate getCoord() {
        return coord;
    }

    public Team getOwnerTeam() {
        return ownerTeam;
    }
    
    public Node getOwnerAttach() {
        return ownerNode;
    }

    public Spatial getSpatial() {
        return spatial;
    }
    
    
}