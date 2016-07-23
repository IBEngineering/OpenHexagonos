/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author mihaita
 */
public class ModelSupplier {
    
    //Utilities
    private static AssetManager assetManager;
    private static SimpleApplication app;
    
    //Settings
    private static ModelSupplierSettings settings;

    public static void start(SimpleApplication app, ModelSupplierSettings sets) {
        ModelSupplier.app = app;
        ModelSupplier.settings = sets;
        ModelSupplier.assetManager = app.getAssetManager();
    }
    
    public static Spatial getCell() {
        Box cell = new Box(settings.radius, settings.radius, settings.radius);
        Spatial cellSpatial = new Geometry("A cell generated in ModelSupplier", cell);
        
        cellSpatial.setMaterial(getCellMaterial());
        
        return cellSpatial;
    }
    
    public static Spatial getBacterium() {
        Sphere bac = new Sphere(8, 8, settings.radius);
        Spatial bacSpatial = new Geometry("A bacterium generated in ModelSupplier", bac);
        
        bacSpatial.setMaterial(getBacMaterial());
        
        return bacSpatial;
    }
    
    public static Material getRandomMaterial() {
        if(!checkManager()) {return null;}
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        
        return mat;
    }
    
    public static Material getBacMaterial() {
        if(!checkManager()) {return null;}
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        mat.setColor("Color", getBacColor());
        
        return mat;
    }
    
    public static Material getCellMaterial() {
        if(!checkManager()) {return null;}
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        
        mat.setColor("Color", getCellColor());
        
        return mat;
    }
    
    public static Material getBoardMaterial() {
        if(!checkManager()) {return null;}
        
        ColorRGBA color = new ColorRGBA(0.2f, 0.1f, 0.3f, 0.4f);
        return getColoredMaterial(color);
    }
    
    public static Material getColoredMaterial(ColorRGBA color) {
        if(!checkManager()) {return null;}
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        
        return mat;
    }
    
    public static ColorRGBA getCellColor() {
        float blue = 0.5f + (float)(Math.random()) * 0.5f;
        ColorRGBA color = new ColorRGBA(0.2f, 0.2f, blue, 1f);
        
        return color;
    }
    
    public static ColorRGBA getBacColor() {
        float green = 0.35f + (float)(Math.random()) * 0.65f;
        ColorRGBA color = new ColorRGBA(0.2f, green, 0.2f, 1f);
        
        return color;
    }
    
    public static ParticleEmitter getFire(ColorRGBA start, ColorRGBA end) {
        if(!checkManager()) {return null;}
        
        ParticleEmitter fire = 
                new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        Material mat_red = new Material(assetManager, 
                "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("Effects/Explosion/flame.png"));
        fire.setMaterial(mat_red);
        fire.setImagesX(2); 
        fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(start);
        fire.setStartColor(end);
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fire.setStartSize(0.5f);
        fire.setEndSize(0.06f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(1f);
        fire.setHighLife(3f);
        fire.getParticleInfluencer().setVelocityVariation(0.5f);
        fire.setNumParticles(50);
        return fire;
    }
    
    public static boolean checkManager() {
        if(assetManager == null) {
            System.err.println("There is no assetManager!!");
            return false;
        } else {
            //Nothing, it's in place
            return true;
        }
    }
}
