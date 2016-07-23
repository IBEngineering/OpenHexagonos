/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import nl.ibe.hex.game.HexPlayer;

/**
 * Supplies models, both as Spatials and as Geometries.
 * 
 * @see Geometry
 * @see Spatial
 * @author MisterCavespider
 */
public class ModelSupplier {

    private static Geometry s;
    
    /**
     * SimplyCalles getPlayerShape().
     * 
     * Might be removed in the future.
     * 
     * @param p The player.
     * @return  The spatial representing the player.
     */
    public static Spatial getPlayerModel(HexPlayer p) {
        return getPlayerShape(p.getType());
    }
    
    /**
     * Creates a Geometry based on the player type.
     * 
     * The geometry and size are specified in the settings.
     * NOT YET.
     * 
     * @param type  The player type.
     * @return      The geometry, representing the player.
     */
    public static Geometry getPlayerShape(HexPlayer.Type type) {
        Mesh mesh = new Sphere(6, 20, 1.48f);  //default thing
        
        switch(type) {
            case BACTERIA: {
                mesh = new Sphere(6, 6, 0.35f);
                break;
            }
            case CELL: {
                mesh = new Box(0.35f, 0.35f, 0.35f);
                break;
            }
        }
        
        Geometry spatial = new Geometry("A " + type + " generated in ModelSupplier", mesh);
        spatial.setMaterial(MaterialSupplier.getPlayerMaterial(type));
        
        return spatial;
    }
    
    public static void announcement(Node node, HexPlayer.Type type) {
        s = getPlayerShape(type);
        s.setLocalTranslation(0, -10, 0);
        s.scale(5);
        node.attachChild(s);
    }
    
    public static void changePlayer(Node node, HexPlayer.Type type) {
        node.detachChild(s);
        s = getPlayerShape(type);
        s.setLocalTranslation(0, -10, 0);
        s.scale(5);
        node.attachChild(s);
    }
    
}
