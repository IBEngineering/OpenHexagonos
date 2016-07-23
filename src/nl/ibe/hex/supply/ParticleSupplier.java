/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.supply;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import static nl.ibe.hex.supply.SupplyRouter.*;

/**
 * Supplies ParticleEmitters.
 * 
 * @author MisterCavespider
 */
public class ParticleSupplier {
    
    /**
     * See the jMonkeyTutorial.
     * 
     * @see ParticleEmitter
     * @param start The start colour.
     * @param end   The end colour.
     * @return  The ParticleEmitter
     */
    public static ParticleEmitter getFire(ColorRGBA start, ColorRGBA end) {
        if(checkManager()) {return null;}
        
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
    
}
