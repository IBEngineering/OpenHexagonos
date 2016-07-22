/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ibe.hex.view.HexSpatial;
import nl.ibe.hex.view.View;

/**
 *
 * @author mihaita
 */
public class Clicker implements ActionListener {
    
    public static String mapping = "Pick for the Clicker";
    
    private final SimpleApplication app;
    private final Node node;
    private View view;

    public Clicker(SimpleApplication app, Node node, View view) {
        this.app = app;
        this.node = node;
        this.view = view;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        
        System.out.println("Click!");
        
        if(name.equals(mapping) && isPressed) {
            //Shoot the ray!
            
            // Reset results list.
            CollisionResults results = new CollisionResults();
            
            // Convert screen click to 3d position
            Vector2f click2d = app.getInputManager().getCursorPosition();
            Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
            
            // Aim the ray from the clicked spot forwards.
            Ray ray = new Ray(click3d, dir);
            
            // Collect intersections between ray and all nodes in results list.
            node.collideWith(ray, results);
            
            // Print the results so we see what is going on
            for (int i = 0; i < results.size(); i++) {
                
                //Say what is found
                System.out.println(results.getCollision(i).toString());
                
            }

            if (results.size() > 0) {
                //There is a thing, so do something
                
                System.out.println("you clicked on something");
                
                Geometry geom = results.getClosestCollision().getGeometry();
                
                if(geom instanceof HexSpatial) {
                    HexSpatial hex = (HexSpatial) geom;
                    view.onClick(hex);
                } else {
                    Logger.getLogger("Clicker").log(Level.WARNING, "No hex has been found in node {0}", node.toString());
                }
                 
            }
        }
    }
}
