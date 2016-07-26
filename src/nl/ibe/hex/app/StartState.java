/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.app;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.HashMap;
import nl.ibe.hex.supply.ModelSupplier;
import nl.ibe.hex.view.update.UpdateListener;
import nl.ibe.hex.view.update.UpdateProvider;

/**
 * 
 * 
 * @author MisterCavespider
 */
public class StartState extends AbstractAppState implements UpdateListener {
    
    private SimpleApplication app;
    private Nifty nifty;
    private NiftyJmeDisplay fakeNifty;
    
    private Spatial thing = ModelSupplier.getRandomHexagon(60);
    
    private HashMap<String,ScreenController> controllers;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        
        //Init some values
        controllers = new HashMap<>();
        
        //Create the nifty
        fakeNifty = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = fakeNifty.getNifty();
        
        //create ScreenControllers
        createScreenControllers();
        
        String start = "Interface/StartNifty.xml";
        String game = "Interface/GameNifty.xml";
        String sets = "Interface/SettingsNifty.xml";
        
        //Register some of the controllers 
        nifty.registerScreenController(controllers.get(start));
        
        //Add the XMLs
        nifty.addXml(start);
        nifty.addXml(game);
        
        //'Start' the nifty
        nifty.gotoScreen("start");
        
        app.getGuiViewPort().addProcessor(fakeNifty);
        
//        this.app.getGuiNode().attachChild(thing);
        UpdateProvider.listeners.add(this);
//        thing.setLocalTranslation(300, 300, -1000);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        
        app.getGuiViewPort().removeProcessor(fakeNifty);
        
        app.getGuiNode().detachChild(thing);
    }

    @Override
    public void provUpdate(float tpf) {
        
        thing.rotate(2*tpf, 3*tpf, 1.5f*tpf);
        
        
    }

    private void createScreenControllers() {
        StartNiftyController snc = new StartNiftyController(app, nifty);
        controllers.put("Interface/StartNifty.xml", snc);
        
        app.getInputManager().addRawInputListener(snc);
        
    }
    
}
