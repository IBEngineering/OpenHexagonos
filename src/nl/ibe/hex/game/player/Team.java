/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.game.player;

import com.jme3.scene.Spatial;
import java.util.ArrayList;
import nl.ibe.hex.supply.ID;

/**
 *
 * @author MisterCavespider
 */
public class Team {
    
    protected String name;
    protected ID ID;
    protected long numericalID;
    
    protected ID modelID;
    protected ID colourID;
    
    protected boolean visible;
    
    public Team(String name, long id) {
        this.name = name;
        
        numericalID = id;
        ID = new ID(name, id);
        
        modelID = new ID(name, id, "model");
        colourID = new ID(name, id, "colour");
        
        visible = true;
        
        registerMe();
    }

    private void registerMe() {
        TeamManager.add(this);
    }
    
    
    
    public String getName() {
        return name;
    }

    public ID getID() {
        return ID;
    }

    public ID getModelID() {
        return modelID;
    }

    public ID getColourID() {
        return colourID;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setName(String name) {
        this.name = name;
        
        ID = new ID(name, numericalID);
        
        modelID = new ID(name, numericalID, "model");
        colourID = new ID(name, numericalID, "colour");
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getNumericalID() {
        return numericalID;
    }
    
    
}
