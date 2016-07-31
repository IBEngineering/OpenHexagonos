/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Torus;
import nl.ibe.hex.game.HexPlayer;
import nl.ibe.hex.game.player.Team;
import nl.ibe.hex.util.Random;
import nl.ibe.hex.view.HexagonMesh;
import nl.ibe.hex.supply.SupplyRouter.*;

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
        return getPlayerShape(p.getTeam());
    }
    
    public static Geometry getRandomHexagon(float min_radius) {
        
        float radius = Random.rdm(min_radius, min_radius+30);
        Mesh mesh = new HexagonMesh(radius);
        
        Geometry g = new Geometry("a random hexagon", mesh);
        g.setMaterial(new Material(SupplyRouter.assetManager, "Common/MatDefs/Misc/Unshaded.j3md"));
        
        return g;
    }
    
    /**
     * Creates a Geometry based on the player type.
     * 
     * The geometry and size are specified in the settings.
     * NOT YET.
     * 
     * @param team  The player type.
     * @return      The geometry, representing the player.
     */
    public static Geometry getPlayerShape(Team team) {
        Mesh mesh = new Sphere(6, 20, 1.48f);  //default thing
        
        if(team.getModelID().equals(0, "model")) {
            //Cell
            mesh = new Box(0.35f, 0.35f, 0.35f);
        } else if(team.getModelID().equals(1, "model")) {
            //Bacteria
            mesh = new Sphere(6, 6, 0.35f);
        } else if(team.getModelID().equals(2, "model")) {
            mesh = new Torus(8, 8, 0.05f, 0.35f);
        }
        
        Geometry spatial = new Geometry("A " + team.getName() + " generated in ModelSupplier", mesh);
        spatial.setMaterial(MaterialSupplier.getPlayerMaterial(team));
        float r = (float) (0.5*Math.PI);
        spatial.rotate(r, 0, 0);
        
        return spatial;
    }
    
    public static void announcement(Node node, Team team) {
        s = getPlayerShape(team);
        s.setLocalTranslation(0, -10, 0);
        s.scale(5);
        node.attachChild(s);
    }
    
    public static void changePlayer(Node node, Team team) {
        node.detachChild(s);
        s = getPlayerShape(team);
        s.setLocalTranslation(0, -10, 0);
        s.scale(5);
        node.attachChild(s);
    }
    
}
